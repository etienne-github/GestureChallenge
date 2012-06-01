package popup;

import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.util.MTColor;
import org.mt4j.util.font.FontManager;
import org.mt4j.util.font.IFont;
import org.mt4j.util.math.Vector3D;

import scene.GestureChallengeScene;

public class RankingPopup<O> extends Popup<O>{

	public RankingPopup(String name, String content, GestureChallengeScene s,
			PopUpCreator PC, Vector3D centerPosition, float radius, float angle, MTColor myColor) {
		super(MTRoundRectangle.class, name, content, s, PC, centerPosition, radius);
		this.getMyShape().setStrokeColor(myColor);
		//this.rotateZ(getCenterPointGlobal(), (float) Math.toDegrees(angle)-90);
	}

	public void addPopupItem(final String text,O obj, MTColor color){
		hMap.put(text, obj);
		
		IFont f = FontManager.getInstance().createFont(scene.getMTApplication(), "number.ttf", 30);

		
		PopupItem pI = new PopupItem(text, this.getMyShape().getCenterPointLocal().x-100, this.getMyShape().getCenterPointLocal().y-100, f.getFontAbsoluteHeight()+2, f);
		pI.setNoFill(true);
		pI.setStrokeColor(color);
		this.getMyShape().addChild(pI);
	}
	
}
