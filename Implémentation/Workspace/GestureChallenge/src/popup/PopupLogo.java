package popup;

import java.io.File;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.util.math.Vector3D;

import scene.GestureChallengeScene;

public class PopupLogo<O> extends Popup<O> {

	private MTRectangle myLogo;

	//Popup(Class<?> sprite, String name,String content, GestureChallengeScene s, PopUpCreator PC, Vector3D centerPosition, float radius)
	public PopupLogo(Class<?> sprite,String name, GestureChallengeScene s,
			PopUpCreator PC, Vector3D centerPosition, float radius) {
		super(sprite , name, "", s, PC, centerPosition, radius);
		myLogo = new MTRectangle(s.getMTApplication(),s.getMTApplication().loadImage("."+((String)File.separator)+"src"+((String)File.separator)+"popup"+((String)File.separator)+"data"+((String)File.separator)+"logo.png"));
		myLogo.setNoStroke(true);
		this.getMyShape().addChild(myLogo);
		myLogo.setPositionRelativeToOther(this.getMyShape(), this.getMyShape().getCenterPointLocal());
		myLogo.setPickable(false);
		myLogo.removeAllGestureEventListeners();
		//xStartPopUpItem=this.getCenterPointLocal().x;
		//yStartPopUpItem=this.getCenterPointLocal().y+75;
	}

	public void addPopupItem(final String text,O obj){
		super.addPopupItem(text, obj);
		PopupItem change = ((PopupItem) this.getMyShape().getChildByName(text+obj.toString()));
		change.translate(new Vector3D(0, myLogo.getHeightXY(TransformSpace.GLOBAL)));
	}
}