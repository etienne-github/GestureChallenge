package popup;

import java.beans.PropertyChangeSupport;

import model.GameModel;

import org.mt4j.components.visibleComponents.shapes.AbstractShape;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

/**
 * SuperClass of each help Popup
 * @author remy
 *
 */
public class HelpPopup extends MTRoundRectangle{



	/**
	 * Creating popup, rotate it on order to place it in front of the player
	 * Add DoubleTapListener to close the popup
	 * @param gm
	 * @param scene
	 * @param name
	 * @param position
	 * @param angle
	 */
	@SuppressWarnings("deprecation")
	public HelpPopup(final GameModel gm, final AbstractScene scene, String name, Vector3D position, float angle) {
		super(position.x, position.y,0f,200f,150f,10f,10f,scene.getMTApplication());
		this.setStrokeColor(MTColor.BLACK);
		//this.setFillColor(MTColor.GREY);
		this.setNoFill(true);
		this.setPickable(false);
		this.setPositionGlobal(position);
		this.removeAllGestureEventListeners();

		

		this.rotateZ(position, (float) Math.toDegrees(angle)-90);

		
	}


}