package popup.touch;

import java.io.File;

import org.mt4j.AbstractMTApplication;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.components.visibleComponents.widgets.buttons.MTImageButton;
import org.mt4j.input.IMTInputEventListener;
import org.mt4j.input.gestureAction.TapAndHoldVisualizer;
import org.mt4j.input.inputData.MTInputEvent;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapAndHoldProcessor.TapAndHoldEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapAndHoldProcessor.TapAndHoldProcessor;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.ToolsMath;
import org.mt4j.util.math.Vector3D;

import physic.shape.PhysicsCircle;
import physic.shape.util.PhysicsHelper;
import processing.core.PApplet;
import scene.GestureChallengeScene;



public class PopupNbPlayers extends Popup {

	
	private int nbPlayers = 0;
	private float scale = 20;
	private PApplet applet;
	
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
		player2.addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer((AbstractMTApplication) applet, super.scene.getCanvas()));
		player2.addGestureListener(TapAndHoldProcessor.class, gl);
		
		scene.getCanvas().addChild(player1);
		scene.getCanvas().addChild(player2);

		// location
		player1.setPositionGlobal(new Vector3D(1*horizontalPad,2*verticalPad,0));
		// I tried a rotatiion...
		player2.setPositionGlobal(new Vector3D(1*horizontalPad,2*verticalPad,0));
		player2.rotateZ(player1.getCenterPointLocal(), 90); // j2


		
	}

}
