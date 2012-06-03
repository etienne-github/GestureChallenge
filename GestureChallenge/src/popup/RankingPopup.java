package popup;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.util.MTColor;
import org.mt4j.util.font.FontManager;
import org.mt4j.util.math.Vector3D;

import scene.GestureChallengeScene;

public class RankingPopup<O> extends Popup<O>{

	private float width, height;
	public RankingPopup(String name, String content, GestureChallengeScene s,
			PopUpCreator PC, Vector3D centerPosition, float radius, float angle, MTColor myColor) {
		super(MTRoundRectangle.class, name, content, s, PC, centerPosition, radius);
		this.getMyShape().setStrokeColor(myColor);
		height = radius;
		width = (float) (radius*1.5);
		getMyShape().rotateZ(getMyShape().getCenterPointGlobal(), (float) Math.toDegrees(angle)-90);
	}

	public void addPopupItem(final String text,O obj, MTColor color){

		super.addPopupItem(text, obj);
		PopupItem change = ((PopupItem) this.getMyShape().getChildByName(text+obj.toString()));
		change.setStrokeColor(color);
		change.tF.setFont(FontManager.getInstance().createFont(scene.getMTApplication(), "number.ttf", 30));
		change.setWidthXYGlobal((float) (this.width - (this.width*0.1)));
		change.setPositionRelativeToOther(getMyShape(), new Vector3D(getMyShape().getCenterPointLocal().x,getMyShape().getCenterPointLocal().y-getMyShape().getHeightXY(TransformSpace.GLOBAL)/3+getPopupItemList().size()*(getMyShape().getHeightXY(TransformSpace.GLOBAL)/5   /*+5)+getMyShape().getHeightXY(TransformSpace.GLOBAL)/2*/)));
	}

}