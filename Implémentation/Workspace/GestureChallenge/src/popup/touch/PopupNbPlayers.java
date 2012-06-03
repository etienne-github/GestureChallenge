package popup.touch;

import java.io.File;

import org.mt4j.AbstractMTApplication;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.shapes.MTRectangle.PositionAnchor;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.components.visibleComponents.widgets.MTTextField;
import org.mt4j.components.visibleComponents.widgets.buttons.MTImageButton;
import org.mt4j.input.IMTInputEventListener;
import org.mt4j.input.gestureAction.TapAndHoldVisualizer;
import org.mt4j.input.inputData.MTInputEvent;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;


import org.mt4j.util.MTColor;
import org.mt4j.util.font.FontManager;
import org.mt4j.util.font.IFont;
import org.mt4j.util.math.ToolsMath;
import org.mt4j.util.math.Vector3D;

import physic.shape.PhysicsCircle;
import physic.shape.util.PhysicsHelper;
import popup.PopUpCreator;
import popup.Popup;
import popup.PopupCredits;

import popup.touch.gestureAction.TapAndHoldCentralVizualizer;
import popup.touch.gestureEvent.TapAndHoldCountEvent;
import popup.touch.gestureProcessor.TapAndHoldCountProcessor;
import processing.core.PApplet;
import scene.GestureChallengeScene;



public class PopupNbPlayers extends Popup {
	
	MTEllipse myTouchArea;

	public PopupNbPlayers(String name, String content, GestureChallengeScene s,
			PopUpCreator PC, Vector3D centerPosition, float radius) {
		super(MTEllipse.class, name, content, s, PC, centerPosition, radius);
		
		myTouchArea = new MTEllipse(s.getMTApplication(),this.getMyShape().getCenterPointLocal(),radius*5/10f,radius*5/10f);
		myTouchArea.removeAllGestureEventListeners();
		myTouchArea.setNoFill(true);
		myTouchArea.setStrokeColor(MTColor.PURPLE);
		this.getMyShape().addChild(myTouchArea);
		myTouchArea.registerInputProcessor(new TapAndHoldCountProcessor(s.getMTApplication(), 2000));
		myTouchArea.addGestureListener(TapAndHoldCountProcessor.class, new TapAndHoldCentralVizualizer(s.getMTApplication(), s.getCanvas(), myTouchArea));
		myTouchArea.addGestureListener(TapAndHoldCountProcessor.class, new WaitEndOfHoldTapAndCountCursorsListener());
		
		//Put and hold one finger per player on this area;
		
		
		//int policeSize = (int) (radius)/15;
		int policeSize = (int) (radius)/12;
		//System.out.println("police size = "+policeSize);
		float l = (float) Math.sqrt(2*Math.pow(radius*5/10f, 2));
		MTTextArea textArea = new MTTextArea(s.getMTApplication(), 100, 100, l, l, FontManager.getInstance().createFont(s.getMTApplication(),"REZ.ttf",(int) policeSize,MTColor.BLACK, MTColor.BLACK));
		textArea.setText("\n\n\n            Put and hold \none finger per player\n              in this area");
		textArea.setNoFill(true);
		textArea.removeAllGestureEventListeners();
		textArea.setPositionRelativeToOther(myTouchArea,myTouchArea.getCenterPointLocal());
		textArea.setNoStroke(true);
		textArea.setPickable(false);
		myTouchArea.addChild(textArea);
			
		}	
	

	class  WaitEndOfHoldTapAndCountCursorsListener implements IGestureEventListener{

		@Override
		public boolean processGestureEvent(MTGestureEvent ge) {
		TapAndHoldCountEvent th = (TapAndHoldCountEvent)ge;
		switch (th.getId()) {
		case TapAndHoldCountEvent.GESTURE_STARTED:
			//textArea.setText("Peut être quelqu'un de plus dans la partie..." );
			break;
		case TapAndHoldCountEvent.GESTURE_UPDATED:

			break;
		case TapAndHoldCountEvent.GESTURE_CANCELED:
			//textArea.setText(nbPlayers + " joueur ose relever le challenge !" );
			break;
			
		case TapAndHoldCountEvent.GESTURE_ENDED:
			if (th.isHoldComplete()){
				PopupNbPlayers.this.myTouchArea.unregisterAllInputProcessors();
				PopupNbPlayers.this.myTouchArea.removeAllGestureEventListeners();
				PC.reactToPopUpResponse(name,th.getAvailableCursors());
				
			
				
				PopupNbPlayers.this.scene.getMTApplication().invokeLater(new Runnable(){

					@Override
					public void run() {
						PopupNbPlayers.this.getMyShape().removeFromParent();
						
					}
					
				});
				
				

			}
			break;
		default:
			break;
		}
		return false;
	}
	
	
	}

	
	
	
	/*private int nbPlayers = 0;
	private float scale = 20;
	private PApplet applet;
	
	
	player1.registerInputProcessor(new TapAndHoldProcessor((AbstractMTApplication) applet, 2000));
	player1.addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer((AbstractMTApplication) applet, super.scene.getCanvas()));
	player1.addGestureListener(TapAndHoldProcessor.class, gl);
	
	public PopupNbPlayers(String content, GestureChallengeScene s,
			Vector3D centerPosition, float width, float height, final PApplet applet) {
		super(content, s, centerPosition, width, height, applet);

		final MTColor textAreaColor = new MTColor(50,200,15,255);
		float verticalPad = 25;
		float horizontalPad = 500;

		this.applet = applet;
		
		
		final MTTextArea player1 = new MTTextArea(applet); // j1
		final MTTextArea player2 = new MTTextArea(applet); // j2
		final PopupNbPlayers pop = this;

		
		IGestureEventListener gl = new IGestureEventListener() {
			public boolean processGestureEvent(MTGestureEvent ge) {
				TapAndHoldEvent th = (TapAndHoldEvent)ge;
				switch (th.getId()) {
				case TapAndHoldEvent.GESTURE_STARTED:
					//textArea.setText("Peut être quelqu'un de plus dans la partie..." );
					break;
				case TapAndHoldEvent.GESTURE_UPDATED:

					break;
				case TapAndHoldEvent.GESTURE_CANCELED:
					textArea.setText(nbPlayers + " joueur ose relever le challenge !" );
					break;
				case TapAndHoldEvent.GESTURE_ENDED:
					if (th.isHoldComplete()){
							nbPlayers++;
							if(nbPlayers == 1)
								textArea.setText(nbPlayers + " seul joueur ose relever le challenge !" );
							else
								textArea.setText(nbPlayers + " joueurs osent relever le challenge !" );
							
							
							// Adding circle so that user can have fun waiting the others (j'ai pas encore la13 ça se sent ^^ !)
							PhysicsCircle c = new PhysicsCircle(applet, th.getLocationOnScreen(), 10, scene.getWorld(), 2.0f, 0, 1, scale);
							MTColor col1 = new MTColor(ToolsMath.getRandom(60, 255),ToolsMath.getRandom(60, 255),ToolsMath.getRandom(60, 255));
							c.setFillColor(col1);
							c.setStrokeColor(col1);
							PhysicsHelper.addDragJoint(scene.getWorld(), c, c.getBody().isDynamic(), scale);
							c.setDepthBufferDisabled(true);
							c.setSizeXYGlobal(75, 75);
							scene.getCanvas().addChild(c);
							// hidding 
							th.getCurrentTarget().setVisible(false);
							// adding gamer's picture on popup
							MTImageButton OK = new MTImageButton (applet, applet.loadImage("."+((String)File.separator)+"src"+((String)File.separator)+"popup"+((String)File.separator)+"touch"+((String)File.separator)+"data"+((String)File.separator)+"joueur.png"));
							OK.setWidthXYGlobal(128);
							OK.setHeightXYGlobal(128); // décallage
							OK.setPositionRelativeToParent(new Vector3D(64*nbPlayers,260));
							OK.setEnabled(true);
							OK.setNoStroke(true);
							OK.addInputListener(new IMTInputEventListener() {
					            @Override
								public boolean processInputEvent(MTInputEvent inEvt) {
					            	// when someone cliks on it we can decrease the number of player ?
									return false;
								}
							});
							p.addChild(OK);
							
					}
					break;
				default:
					break;
				}
				return false;
			}
		};
		
		// player 1
		player1.setFillColor(textAreaColor);
		player1.setStrokeColor(textAreaColor);
		player1.setText("Cliquez et maintenez pour jouer ! ");
		player1.registerInputProcessor(new TapAndHoldProcessor((AbstractMTApplication) applet, 2000));
		player1.addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer((AbstractMTApplication) applet, super.scene.getCanvas()));
		player1.addGestureListener(TapAndHoldProcessor.class, gl);
		// player 2
		player2.setFillColor(textAreaColor);
		player2.setStrokeColor(textAreaColor);
		player2.setText("Cliquez et maintenez pour jouer ! ");
		player2.registerInputProcessor(new TapAndHoldProcessor((AbstractMTApplication) applet, 2000));
		player2.addGestureListener(TapAndHoldProcessor.class, new TapAndHoldCentralVizualizer((AbstractMTApplication) applet, super.scene.getCanvas(),player2));
		player2.addGestureListener(TapAndHoldProcessor.class, gl);
		
		scene.getCanvas().addChild(player1);
		scene.getCanvas().addChild(player2);

		// location
		player1.setPositionGlobal(new Vector3D(1*horizontalPad,2*verticalPad,0));
		// I tried a rotatiion...
		player2.setPositionGlobal(new Vector3D(1*horizontalPad,2*verticalPad,0));
		player2.rotateZ(player1.getCenterPointLocal(), 90); // j2


		
	}*/
	

}
