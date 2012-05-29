package popup.touch;

import java.io.File;

import org.jbox2d.dynamics.Body;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.components.visibleComponents.widgets.buttons.MTImageButton;
import org.mt4j.input.IMTInputEventListener;
import org.mt4j.input.gestureAction.InertiaDragAction;
import org.mt4j.input.inputData.MTInputEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragProcessor;
import org.mt4j.util.MTColor;
import org.mt4j.util.font.FontManager;
import org.mt4j.util.math.Vector3D;

import physic.shape.IPhysicsComponent;
import processing.core.PApplet;
import scene.GestureChallengeScene;



public class Popup extends MTRoundRectangle implements IPhysicsComponent{

	protected float width;
	protected float height;
	private Vector3D centerPosition;
	private String textContent;
	protected MTTextArea textArea;
	private MTImageButton OK;
	protected GestureChallengeScene scene;	//acces to the gesturecChallenge scene needed to set ths player number, etc.
	protected Popup p=this;					//acces to the popup needed in the button MTEventListener
	
	
	@SuppressWarnings("deprecation")
	public Popup(String content, GestureChallengeScene s, Vector3D centerPosition, float width, float height, PApplet applet) {
		super(0, 0, 0,width, height, width/10, height/10, applet) ;
		this.scene=s;
		this.width=width;
		this.height=height;
		this.centerPosition=centerPosition;
		this.textContent=content;
		
		
		
		int policeSize = (int) (this.width+this.height)/15;
		System.out.println("police size = "+policeSize);
		textArea = new MTTextArea(applet, 100, 100, this.width-this.width/10, this.height-this.height/10, FontManager.getInstance().createFont(applet,"arial.ttf",(int) policeSize,MTColor.BLACK, MTColor.BLACK));
		textArea.setText(this.textContent);
		textArea.setNoFill(true);
		textArea.setGestureAllowance(DragProcessor.class, false);
		textArea.setPositionRelativeToParent(this.getCenterPointLocal());
		this.addChild(this.textArea);
		
		OK = new MTImageButton (applet, applet.loadImage("."+((String)File.separator)+"src"+((String)File.separator)+"popup"+((String)File.separator)+"data"+((String)File.separator)+"OK.png"));
		OK.setWidthXYGlobal(this.width/5);
		OK.setHeightXYGlobal(this.width/5);
		OK.setPositionRelativeToParent(new Vector3D(this.width/2,this.height - this.height/7 ));
		OK.setEnabled(true);
		OK.setNoStroke(true);
		OK.addInputListener(new IMTInputEventListener() {
            @Override
			public boolean processInputEvent(MTInputEvent inEvt) {
            	p.removeFromParent();
				return false;
			}
		});
		this.addChild(OK);
		
		
		
		this.setPositionGlobal(this.centerPosition);
		this.addGestureListener(DragProcessor.class, new InertiaDragAction());
		scene.getCanvas().addChild(this);
	}



	@Override
	public void setCenterRotation(float angle) {
		
	}



	@Override
	public Body getBody() {
		return null;
	}
}