package popup.touch.gestureAction;




import org.mt4j.AbstractMTApplication;
import org.mt4j.components.MTComponent;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.interfaces.IMTController;
import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.components.visibleComponents.shapes.MTPolygon;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.util.MTColor;
import org.mt4j.util.camera.MTCamera;
import org.mt4j.util.math.Vector3D;
import org.mt4j.util.math.Vertex;

import popup.touch.gestureEvent.TapAndHoldCountEvent;
import processing.core.PApplet;

/**
 * The Class TapAndHoldVisualizer. Animates the drawing of a circle
 * to indicate the status of the tap&hold gesture in progress.
 * 
 * @author Christopher Ruff
 */
public class TapAndHoldCentralVizualizer implements IGestureEventListener {
	
	/** The app. */
	private AbstractMTApplication app;
	
	/** The parent. */
	private MTComponent parent;
	private MTPolygon target;
	
	/** The cam. */
	private MTCamera cam;

	/** The e. */
	private HoldEllipse e;
	
	
	/**
	 * Instantiates a new tap and hold visualizer.
	 * 
	 * @param app the app
	 * @param parent the parent
	 */
	public TapAndHoldCentralVizualizer(AbstractMTApplication app, MTComponent parent, MTPolygon target) {
		super();
		this.app = app;
		this.parent = parent;
		this.target=target;
		
		//cam = new MTCamera(app);
		
		e = new HoldEllipse(app, new Vector3D(this.target.getCenterPointGlobal().x, this.target.getCenterPointGlobal().y), target.getWidthXY(TransformSpace.GLOBAL)/2f, target.getHeightXY(TransformSpace.GLOBAL)/2f, 50);
		e.setPickable(false);
		e.unregisterAllInputProcessors();
		e.setStrokeColor(new MTColor(240,50,50,200));
		e.setStrokeWeight(4);
		e.setNoFill(true);
		e.setDepthBufferDisabled(true);
		//e.attachCamera(cam);
		e.setVisible(false);
		e.setDegrees(0);
		
		e.setController(new IMTController() {
			public void update(long timeDelta) {
				MTComponent parent = e.getParent();
				if (parent != null){
					int childCount = parent.getChildCount();
					if (childCount > 0
						&& !parent.getChildByIndex(childCount-1).equals(e))
					{
						TapAndHoldCentralVizualizer.this.app.invokeLater(new Runnable() {
							public void run(){
								MTComponent parent = e.getParent();
								if (parent != null){
									parent.removeChild(e);
									parent.addChild(e);
									
									e.setPositionRelativeToOther(TapAndHoldCentralVizualizer.this.getTarget(), TapAndHoldCentralVizualizer.this.getTarget().getCenterPointLocal());
								}
							}
						});
					}
				}
			}
		});
	}


	public MTPolygon getTarget(){
		return target;
	}
	
	/* (non-Javadoc)
	 * @see org.mt4j.input.inputProcessors.IGestureEventListener#processGestureEvent(org.mt4j.input.inputProcessors.MTGestureEvent)
	 */
	public boolean processGestureEvent(MTGestureEvent ge) {
		TapAndHoldCountEvent t = (TapAndHoldCountEvent)ge;

		float d = 360f * t.getElapsedTimeNormalized();
		
//		float a = 255 * t.getElapsedTimeNormalized();
		//float a = 255 * t.getElapsedTimeNormalized();
		float a = 255;
		
		switch (t.getId()) {
		case TapAndHoldCountEvent.GESTURE_STARTED:
		case TapAndHoldCountEvent.GESTURE_RESUMED:
			parent.addChild(e);
			e.setDegrees(0);
			e.recreate(false);
			e.setPositionGlobal(new Vector3D(target.getCenterPointGlobal().x,target.getCenterPointGlobal().y));
			break;
		case TapAndHoldCountEvent.GESTURE_UPDATED:
			e.setVisible(true);
			
			if (d >= 350){ //FIXME HACK to display the circle really closed before the end
				d = 360;
				e.setDegrees(d);
				e.recreate(true);
				
				MTColor stroke = e.getStrokeColor();
				e.setStrokeColor(new MTColor(stroke.getR(), stroke.getG(), stroke.getB(), 255));
			}else{
			e.setDegrees(d);
			e.recreate(false);
			
			MTColor stroke = e.getStrokeColor();
			e.setStrokeColor(new MTColor(stroke.getR(), stroke.getG(), stroke.getB(), a));
			}
			break;
		case MTGestureEvent.GESTURE_CANCELED:
		case TapAndHoldCountEvent.GESTURE_ENDED:
			e.setVisible(false);
			parent.removeChild(e);
			break;
		default:
			break;
		}
		return false;
	}

	
	/**
	 * The Class HoldEllipse.
	 * 
	 * @author Christopher Ruff
	 */
	private class HoldEllipse extends MTEllipse{
		
		/** The segments. */
		private int segments;

		/**
		 * Instantiates a new hold ellipse.
		 * 
		 * @param applet the applet
		 * @param centerPoint the center point
		 * @param radiusX the radius x
		 * @param radiusY the radius y
		 * @param segments the segments
		 */
		public HoldEllipse(PApplet applet, Vector3D centerPoint, float radiusX,float radiusY, int segments) {
			super(applet, centerPoint, radiusX, radiusY, segments);
			this.segments = segments;
			
		}
		
		@Override
		protected void setDefaultGestureActions() {
			//no gestures
		}
		
		/**
		 * Recreate.
		 * 
		 * @param close the close
		 */
		public void recreate(boolean close){
			if (close){
				setVertices(getVertices(segments));
			}else{
				Vertex[] verts = getVertices(segments);
				Vertex[] v = new Vertex[verts.length -1];
				System.arraycopy(verts, 0, v, 0, verts.length-1);
				setVertices(v);
			}
		}
		
		
	}
}
