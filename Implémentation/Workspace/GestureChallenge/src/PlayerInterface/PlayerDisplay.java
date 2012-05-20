package playerinterface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import model.Constants;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.widgets.MTTextField;
import org.mt4j.util.MTColor;
import org.mt4j.util.font.FontManager;
import org.mt4j.util.font.IFont;
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;

public class PlayerDisplay {
	MTTextField rank;
	MTTextField score;
	MTTextField time;
	ArrayList<Notification> notifList;
	PlayerInterface myPI;
	
	public PlayerDisplay(PlayerInterface PI){
		
		myPI=PI;
		notifList=new ArrayList<Notification>();
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
				(float)(rank.getPosition(TransformSpace.GLOBAL).x+Math.cos(PI.myAngle)*18),
				(float)(rank.getPosition(TransformSpace.GLOBAL).y+Math.sin(PI.myAngle)*18)
				)
		);
		
		
		
		PI.getMyGCS().getCanvas().addChild(rank);
		PI.getMyGCS().getCanvas().addChild(score);
		PI.getMyGCS().getCanvas().addChild(time);
	}
	
	public void addNotification(String text,MTColor color){
		Iterator it = notifList.iterator();
		while(it.hasNext()){
			((Notification) it.next()).up();
		}
		Notification n = new Notification(this,myPI,text,color);
		notifList.add(n);
	}
	
	class Notification extends MTTextField{
			Timer myTimer;
			TimerTask myTask;
			int maxAnimationFrames=10;
			int animationFrames=maxAnimationFrames;
			MTColor myColor;
			PlayerInterface myPI;
			PlayerDisplay myPD;
			int ups=0;
		
		public Notification(PlayerDisplay PD,final PlayerInterface PI,String text,MTColor color) {
			super((float)(rank.getPosition(TransformSpace.GLOBAL).x+Math.cos(PI.myAngle)*18), (float)(rank.getPosition(TransformSpace.GLOBAL).y+Math.sin(PI.myAngle)*18), 130,25, FontManager.getInstance().createFont(PI.getMyGCS().getMTApplication(), Constants.displatFontName, Constants.displayFontSize), PI.getMyGCS().getMTApplication());
			this.rotateZGlobal(this.getCenterPointGlobal(), (float) (Math.toDegrees(PI.myAngle-Math.PI/2f)));
			this.setAnchor(PositionAnchor.UPPER_LEFT);
			this.setPositionGlobal(new Vector3D(
					(float)(rank.getPosition(TransformSpace.GLOBAL).x+Math.cos(PI.myAngle-Math.PI)*35),
					(float)(rank.getPosition(TransformSpace.GLOBAL).y+Math.sin(PI.myAngle-Math.PI)*35)
					)
			);
			myColor=color;
			myPI=PI;
			myPD=PD;
			this.setNoStroke(true);
			this.removeAllGestureEventListeners();
			this.setPickable(false);
			this.setNoFill(true);
			
			//myColor=MTColor.YELLOW;
			this.setFontColor(myColor);
			this.setText(text);
			
			myTask = new TimerTask(){

				@Override
				public void run() {
					System.err.println("Animation");
					animationFrames--;
					float alpha = myColor.getAlpha();
					alpha*=(animationFrames/(float)maxAnimationFrames);
					MTColor nC = new MTColor(myColor.getR(),myColor.getG(),myColor.getB());
					nC.setAlpha(alpha);
					setFontColor(nC);
					myColor=nC;
					if(animationFrames<=0){
						this.cancel();
						myTimer.cancel();
						
						PI.getMyGCS().getMTApplication().invokeLater(new Runnable(){

							@Override
							public void run() {
								

								notifList.remove(Notification.this);
								PI.getMyGCS().getCanvas().removeChild(Notification.this);
								
							}

						});
						
					}
					
					
				}
				
			};
			myTimer = new Timer();
			myTimer.schedule(myTask,0,Constants.displayNotificationTime*1000/maxAnimationFrames);
			myPI.getMyGCS().getCanvas().addChild(this);
		}
		
		public void up(){
			ups++;
			this.setPositionGlobal(new Vector3D(
					(float)(rank.getPosition(TransformSpace.GLOBAL).x+Math.cos(myPI.myAngle-Math.PI)*(35+ups*this.getHeightXY(TransformSpace.GLOBAL))),
					(float)(rank.getPosition(TransformSpace.GLOBAL).y+Math.sin(myPI.myAngle-Math.PI)*(35+ups*this.getHeightXY(TransformSpace.GLOBAL)))
					)
			);
		}
		
	}
}
