package gesture.fivefingersgesture.examples;

import gesture.fivefingersgesture.FiveFingersGestureEvent;
import gesture.fivefingersgesture.FiveFingersGestureProcessor;

import org.mt4j.AbstractMTApplication;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.AbstractShape;
import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.globalProcessors.CursorTracer;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;
import org.mt4j.util.logging.ILogger;
import org.mt4j.util.logging.MTLoggerFactory;
import org.mt4j.util.math.Vector3D;
import org.mt4j.util.opengl.GLFBO;


public class My_Scene extends AbstractScene{

	
	/** The app. */
	private AbstractMTApplication app;
	
	/** The has fbo. */
	private boolean hasFBO;
	
	/** The switch directly to scene. */
	private boolean switchDirectlyToScene = false;
	
	private MTRectangle myPlot;
	private MTEllipse myEllipse;
	
	public My_Scene(final AbstractMTApplication mtApplication, String name) {
		super(mtApplication, name);
		this.app = mtApplication;
		this.hasFBO = GLFBO.isSupported(app);
		this.switchDirectlyToScene = !this.hasFBO || switchDirectlyToScene;
		this.registerGlobalInputProcessor(new CursorTracer(app, this));
		AbstractShape.createDefaultGestures = false;
		
		myPlot = new MTRectangle(700, 700, mtApplication);
		myPlot.setPositionGlobal(new Vector3D(app.width/2f/*-myPlot.getWidthXY(TransformSpace.GLOBAL)/2f*/,app.height/2f/*-myPlot.getHeightXY(TransformSpace.GLOBAL)/2f*/));
		myPlot.setFillColor(MTColor.LIME);
		myPlot.setNoStroke(false);
		myPlot.setStrokeWeight(4);
		myPlot.setStrokeColor(MTColor.GREEN);
		this.getCanvas().addChild(myPlot);
		
		myEllipse = new MTEllipse(mtApplication, new Vector3D(0, 0), 1, 1);
		myEllipse.setStrokeColor(MTColor.RED);
		this.getCanvas().addChild(myEllipse);
		
		
		
		//myPlot.translate(new Vector3D(200, 300));
		myPlot.registerInputProcessor(new FiveFingersGestureProcessor(mtApplication));
		myPlot.addGestureListener(FiveFingersGestureProcessor.class, new IGestureEventListener() {
			public boolean processGestureEvent(MTGestureEvent ge) {
				FiveFingersGestureEvent evt = (FiveFingersGestureEvent) ge;
				//Vector3D barycenter = evt.getBarycenter();
				//myEllipse.setPositionRelativeToParent(barycenter);
				float angle = evt.getRotationAngle();
				System.out.println(Math.toDegrees(angle));
				//System.out.println(angle*360/(2*Math.PI));
				myPlot.rotateZ(myPlot.getCenterPointGlobal(), (float)(angle*360/(2*Math.PI)));
				return false;
			}
		});
	}

}
