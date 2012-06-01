package popup;

import java.io.File;

import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.util.math.Vector3D;

import scene.GestureChallengeScene;

public class PopupLogo<O> extends Popup<O> {

	
	
	//Popup(Class<?> sprite, String name,String content, GestureChallengeScene s, PopUpCreator PC, Vector3D centerPosition, float radius)
	public PopupLogo(Class<?> sprite,String name, GestureChallengeScene s,
			PopUpCreator PC, Vector3D centerPosition, float radius) {
		super(sprite , name, "", s, PC, centerPosition, radius);
		MTRectangle logo = new MTRectangle(s.getMTApplication(),s.getMTApplication().loadImage("."+((String)File.separator)+"src"+((String)File.separator)+"popup"+((String)File.separator)+"data"+((String)File.separator)+"logo.png"));
		this.getMyShape().addChild(logo);
		logo.setPositionRelativeToOther(this.getMyShape(), this.getMyShape().getCenterPointLocal());
		logo.setPickable(false);
		logo.removeAllGestureEventListeners();
		//xStartPopUpItem=this.getCenterPointLocal().x;
		//yStartPopUpItem=this.getCenterPointLocal().y+75;
	}

}
