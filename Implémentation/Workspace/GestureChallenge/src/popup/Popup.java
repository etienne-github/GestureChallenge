package popup;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;



import model.GameModel;

import org.jbox2d.dynamics.Body;
import org.mt4j.components.TransformSpace;
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
	private PopUpCreator PC;
	private MTImageButton OK;
	private GestureChallengeScene scene;	//acces to the gesturecChallenge scene needed to set ths player number, etc.
	private HashMap<String,O> hMap;
	private String name;
	private ArrayList<PopupItem> popupItemList=new ArrayList<PopupItem>();
	
	
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
	}
	
	
	
	class PopupItem extends MTRoundRectangle {
		
		MTTextField tF;
		
		public PopupItem(final String text, float x, float y, float height,
				 IFont f) {
			super(x, y,0f,300f,height,10f,10f,scene.getMTApplication());

			popupItemList.add(this);
			tF = new MTTextField(scene.getMTApplication(), 0, 0, 200, f.getFontAbsoluteHeight()+2, f);
			

			tF.setInnerPadding(0);
			tF.setText(text);
			tF.setStrokeColor(MTColor.BLACK);
			tF.setNoStroke(true);
			tF.setNoFill(true);
			tF.removeAllGestureEventListeners();
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
			this.setPositionRelativeToOther(Popup.this, new Vector3D(Popup.this.getCenterPointLocal().x,Popup.this.getCenterPointLocal().y-100+popupItemList.size()*(this.getHeightXY(TransformSpace.GLOBAL)+5)));
			
		}
		
	}
	
	
	
	public void addPopupItem(final String text,O obj){
		hMap.put(text, obj);
		
		IFont f = FontManager.getInstance().createFont(scene.getMTApplication(), "arial", 30);

		
		PopupItem pI = new PopupItem(text, this.getCenterPointLocal().x-100, this.getCenterPointLocal().y-100, f.getFontAbsoluteHeight()+2, f);
		pI.setNoFill(true);
		pI.setStrokeColor(MTColor.PURPLE);
		this.addChild(pI);
	}
	
	

	
	
}