package playerinterface;

import model.Constants;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.widgets.MTTextField;
import org.mt4j.util.font.FontManager;
import org.mt4j.util.math.Vector3D;

public class PlayerDisplay {
	MTTextField rank;
	MTTextField score;
	MTTextField time;
	
	public PlayerDisplay(PlayerInterface PI){
		rank = new MTTextField(PI.getMyGCS().getMTApplication(),PI.myPG.getBody().getPosition().x*PI.getMyGCS().getScale(),PI.myPG.getBody().getPosition().y*PI.getMyGCS().getScale(),35,25,FontManager.getInstance().createFont(PI.getMyGCS().getMTApplication(), Constants.displatFontName, Constants.displayFontSize));
		score = new MTTextField(PI.getMyGCS().getMTApplication(),PI.myPG.getBody().getPosition().x*PI.getMyGCS().getScale(),PI.myPG.getBody().getPosition().y*PI.getMyGCS().getScale(),130,25,FontManager.getInstance().createFont(PI.getMyGCS().getMTApplication(), Constants.displatFontName, Constants.displayFontSize));
		time = new MTTextField(PI.getMyGCS().getMTApplication(),PI.myPG.getBody().getPosition().x*PI.getMyGCS().getScale(),PI.myPG.getBody().getPosition().y*PI.getMyGCS().getScale(),60,25,FontManager.getInstance().createFont(PI.getMyGCS().getMTApplication(), Constants.displatFontName, Constants.displayFontSize));

		
		
		rank.setNoFill(true);
		rank.setNoStroke(true);
		rank.setPickable(false);
		rank.removeAllGestureEventListeners();


		
		score.setNoFill(true);
		score.setNoStroke(true);
		score.setPickable(false);
		score.removeAllGestureEventListeners();
		
		time.setNoFill(true);
		time.setNoStroke(true);
		time.setPickable(false);
		time.removeAllGestureEventListeners();

		
		rank.setText("3th");
		score.setText("0 pts");
		time.setText("0'00''");
		
		rank.rotateZGlobal(rank.getCenterPointGlobal(), (float) (Math.toDegrees(PI.myAngle-Math.PI/2f)));
		score.rotateZGlobal(rank.getCenterPointGlobal(), (float) (Math.toDegrees(PI.myAngle-Math.PI/2f)));
		time.rotateZGlobal(rank.getCenterPointGlobal(), (float) (Math.toDegrees(PI.myAngle-Math.PI/2f)));

		
		rank.setPositionGlobal(new Vector3D(
				(float) (PI.myPG.getBody().getPosition().x*PI.getMyGCS().getScale()/*+rank.getWidthXY(TransformSpace.GLOBAL)/2f*/+Math.cos(PI.myAngle-Math.PI/2f)*Constants.radiusGoalDisplay),
				(float) (PI.myPG.getBody().getPosition().y*PI.getMyGCS().getScale()/*+rank.getHeightXY(TransformSpace.GLOBAL)/2f*/+Math.sin(PI.myAngle-Math.PI/2f)*Constants.radiusGoalDisplay)
				)
		);
		
		score.setPositionGlobal(new Vector3D(
				(float) (PI.myPG.getBody().getPosition().x*PI.getMyGCS().getScale()/*+rank.getWidthXY(TransformSpace.GLOBAL)/2f*/+Math.cos(PI.myAngle-Math.PI/2f)*(Constants.radiusGoalDisplay+rank.getWidthXY(TransformSpace.GLOBAL)/2f+score.getWidthXY(TransformSpace.GLOBAL)/2f)),
				(float) (PI.myPG.getBody().getPosition().y*PI.getMyGCS().getScale()/*+rank.getHeightXY(TransformSpace.GLOBAL)/2f*/+Math.sin(PI.myAngle-Math.PI/2f)*(Constants.radiusGoalDisplay+rank.getWidthXY(TransformSpace.GLOBAL)/2f+score.getWidthXY(TransformSpace.GLOBAL)/2f))
				)
		);
		
		time.setPositionGlobal(new Vector3D(
				(float) (PI.myPG.getBody().getPosition().x*PI.getMyGCS().getScale()/*+rank.getWidthXY(TransformSpace.GLOBAL)/2f*/+Math.cos(PI.myAngle-Math.PI/2f+Math.PI/12f)*(Constants.radiusGoalDisplay+10)),
				(float) (PI.myPG.getBody().getPosition().y*PI.getMyGCS().getScale()/*+rank.getHeightXY(TransformSpace.GLOBAL)/2f*/+Math.sin(PI.myAngle-Math.PI/2f+Math.PI/12f)*(Constants.radiusGoalDisplay+10))
				)
		);
		
		
		PI.getMyGCS().getCanvas().addChild(rank);
		PI.getMyGCS().getCanvas().addChild(score);
		PI.getMyGCS().getCanvas().addChild(time);
	}
}
