package popup;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;



import model.GameModel;

import org.jbox2d.dynamics.Body;
import org.mt4j.components.MTComponent;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.interfaces.IMTController;
import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.components.visibleComponents.shapes.MTRectangle.PositionAnchor;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.components.visibleComponents.widgets.MTTextField;
import org.mt4j.components.visibleComponents.widgets.buttons.MTImageButton;
import org.mt4j.input.IMTInputEventListener;
import org.mt4j.input.gestureAction.InertiaDragAction;
import org.mt4j.input.inputData.MTInputEvent;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.MTColor;
import org.mt4j.util.font.FontManager;
import org.mt4j.util.font.IFont;
import org.mt4j.util.math.Vector3D;

import physic.shape.IPhysicsComponent;
import processing.core.PApplet;
import scene.GestureChallengeScene;


public class Popup<O> extends MTEllipse {

	private float width;
	private float height;
	
	private String textContent;
	private MTTextArea textArea;
	protected PopUpCreator PC;
	protected GestureChallengeScene scene;	//acces to the gesturecChallenge scene needed to set ths player number, etc.
	private HashMap<String,O> hMap;
	protected String name;
	private ArrayList<PopupItem> popupItemList=new ArrayList<PopupItem>();
	protected float xStartPopUpItem;
	protected float yStartPopUpItem;
	
	
	@SuppressWarnings("deprecation")
	public Popup(String name,String content, GestureChallengeScene s, PopUpCreator PC, Vector3D centerPosition, float radius) {
		super(s.getMTApplication(),centerPosition,radius,radius);
		this.PC=PC;
		this.scene=s;
		this.width=width;
		this.height=height;
		this.textContent=content;
		this.name=name;
		this.setStrokeWeight(4);
		this.setStrokeColor(MTColor.PURPLE);
		this.hMap = new HashMap<String,O>();
		int policeSize = (int) (radius*2f)/15;
		System.out.println("police size = "+policeSize);
		float l = (float) Math.sqrt(2*Math.pow(radius, 2));
		textArea = new MTTextArea(s.getMTApplication(), 100, 100, l, l, FontManager.getInstance().createFont(s.getMTApplication(),"arial",(int) policeSize,MTColor.BLACK, MTColor.BLACK));
		textArea.setText(this.textContent);
		textArea.setNoFill(true);
		textArea.removeAllGestureEventListeners();
		textArea.setPositionRelativeToParent(this.getCenterPointLocal());
		textArea.setNoStroke(true);
		this.addChild(this.textArea);

		
		
		
		
		this.setPositionGlobal(centerPosition);
		this.removeAllGestureEventListeners();
		scene.getCanvas().addChild(this);
		
		this.registerInputProcessor(new DragProcessor(scene.getMTApplication()));
		this.addGestureListener(DragProcessor.class, new CircularPopUpDragListener(this));
		xStartPopUpItem=this.getCenterPointLocal().x;
		yStartPopUpItem=this.getCenterPointLocal().y-100;
	}
	
	
	
	class PopupItem extends MTRoundRectangle {
		
		MTTextField tF;
		
		public PopupItem(final String text, float height,
				 IFont f) {
			super(0, 0,0f,300f,height,10f,10f,scene.getMTApplication());

			popupItemList.add(this);
			tF = new MTTextField(scene.getMTApplication(), 0, 0, 200, f.getFontAbsoluteHeight()+2, f);
			

			tF.setInnerPadding(0);
			tF.setText(text);
			tF.setStrokeColor(MTColor.BLACK);
			tF.setNoStroke(true);
			tF.setNoFill(true);
			tF.removeAllGestureEventListeners();
			this.removeAllGestureEventListeners();
			tF.registerInputProcessor(new TapProcessor(scene.getMTApplication()));
			tF.addGestureListener(TapProcessor.class, new IGestureEventListener() {
				
				@Override
				public boolean processGestureEvent(MTGestureEvent ge) {
					TapEvent te = (TapEvent)ge;
					if (te.isTapped()){
						System.out.println("tapped !");
						PC.reactToPopUpResponse(name,hMap.get(text));
						Popup.this.removeFromParent();
					}
					return false;
				}
			});
			
			tF.setAnchor(PositionAnchor.CENTER);
			tF.setPositionRelativeToOther(this, this.getCenterPointLocal());
			this.addChild(tF);
			this.setPositionRelativeToOther(Popup.this, new Vector3D(Popup.this.xStartPopUpItem,Popup.this.yStartPopUpItem+popupItemList.size()*(this.getHeightXY(TransformSpace.GLOBAL)+5)));

		}
		
	}
	
	
	
	public void addPopupItem(final String text,O obj){
		hMap.put(text, obj);
		
		IFont f = FontManager.getInstance().createFont(scene.getMTApplication(), "arial", 30);

		
		PopupItem pI = new PopupItem(text, f.getFontAbsoluteHeight()+2, f);
		pI.setNoFill(true);
		pI.setStrokeColor(MTColor.PURPLE);
		this.addChild(pI);
	}
	
	
	private class CircularPopUpDragListener implements IGestureEventListener{
		private MTEllipse theListCellContainer;
		private Popup p;
		private Vector3D center;
		private double angle = 1;
		
		private boolean canDrag;
		
		/**
		 * Constructor
		 * @param c
		 * @param s
		 */
		public CircularPopUpDragListener(Popup p){

			this.p=p;
			this.center =p.getCenterPointGlobal();
			
			//this.canDrag = false;
		}
		

		public boolean processGestureEvent(MTGestureEvent ge) {
			DragEvent de = (DragEvent)ge;
			
			Vector3D dir = de.getTranslationVect();
			dir.transformDirectionVector(p.getGlobalInverseMatrix());
			Vector3D to = new Vector3D(de.getTo().x -center.x, de.getTo().y - center.y, 0);
			Vector3D from = new Vector3D(de.getFrom().x - center.x, de.getFrom().y - center.y, 0);
			
			if(from.length() > 100)
			{
				switch (de.getId()) {
				case MTGestureEvent.GESTURE_DETECTED:
				case MTGestureEvent.GESTURE_UPDATED:
						angle = Math.atan2(to.y,to.x) - Math.atan2(from.y,from.x);
						//System.err.println("angle listener : "+angle);
						Popup.this.rotateZGlobal(p.getCenterPointGlobal(), (float) Math.toDegrees(angle));
					break;
				case MTGestureEvent.GESTURE_ENDED:
					Vector3D vel = de.getDragCursor().getVelocityVector(140);
					vel.scaleLocal(0.8f);
					vel = vel.getLimited(15);
					IMTController oldController = p.getController();
					//theListCellContainer.setController(new InertiaListController(theListCellContainer, vel, oldController, (float) (Math.abs(angle)/angle)));
					p.setController(new InertiaCircularPopUpController(p, vel, oldController, (float) (Math.abs(angle)/angle)));

					break;
				default:
					break;
				}
			}
			return false;
		}
		
		
		
		
		
		/**
		 * The Class InertiaListController.
		 * Controller to add an inertia scrolling after scrolling/dragging the list content.
		 * 
		 * @author Christopher Ruff
		 */
		private class InertiaCircularPopUpController implements IMTController{
			private MTComponent target;
			private Vector3D startVelocityVec;
			private float dampingValue = 0.95f;
			private float rotationDir;
//			private float dampingValue = 0.80f;
			
			private IMTController oldController;
			
			public InertiaCircularPopUpController(MTComponent target, Vector3D startVelocityVec, IMTController oldController, float dir) {
				super();
				this.target = target;
				this.startVelocityVec = startVelocityVec;
				this.oldController = oldController;
				this.rotationDir = dir;
				
			//	System.err.println("vel :"+startVelocityVec);
//				System.out.println(startVelocityVec);
				//Animation inertiaAnim = new Animation("Inertia anim for " + target, new MultiPurposeInterpolator(startVelocityVec.length(), 0, 100, 0.0f, 0.5f, 1), target);
			}
			
			public void update(long timeDelta) {
				if (false){//
					//theListCellContainer.isDragging();
					startVelocityVec.setValues(Vector3D.ZERO_VECTOR);
					target.setController(oldController);
					return;
				}
				
				if (Math.abs(startVelocityVec.x) < 0.05f && Math.abs(startVelocityVec.y) < 0.05f){
					startVelocityVec.setValues(Vector3D.ZERO_VECTOR);
					target.setController(oldController);
					return;
				}
				startVelocityVec.scaleLocal(dampingValue);
				
				Vector3D transVect = new Vector3D(startVelocityVec);
				transVect.transformDirectionVector(p.getGlobalInverseMatrix());
				//System.out.println(rotationDir);
				//theListCellContainer.translate(new Vector3D(0, transVect.y), TransformSpace.LOCAL);
				float angle=((rotationDir * transVect.length())/2);
				//System.err.println("angle cont "+angle);
				if(!Float.isNaN(angle)){
					Popup.this.rotateZGlobal(Popup.this.getCenterPointGlobal(), angle);
				}
				
				if (oldController != null){
					oldController.update(timeDelta);
				}
			}
		}
		
	}
}
	
	
