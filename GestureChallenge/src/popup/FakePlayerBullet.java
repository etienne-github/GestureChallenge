package popup;

import model.Constants;

import org.jbox2d.dynamics.World;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import physic.shape.PhysicsCircle;
import processing.core.PApplet;

public class FakePlayerBullet extends PhysicsCircle {

	BeginnerHelpSequence mySender;
	int reboundleft;
	int myScore;
	
	public FakePlayerBullet(PApplet applet, Vector3D centerPoint, float radius,
			World world, float density, float friction, float restitution,
			float worldScale, MTColor color, BeginnerHelpSequence beginnerHelpSequence) {
		super(applet, centerPoint, radius, world, density, friction, restitution,
				worldScale);

		mySender = beginnerHelpSequence;
		reboundleft=1;
		this.setFillColor(color);
		MTColor darker = new MTColor(color.getR()/2f,color.getG()/2f,color.getB()/2f);
		this.setStrokeColor(darker);
		this.setStrokeWeight(4);

		
		this.setName("FakePlayerBullet");
		this.myScore=Constants.bulletScore;
		
	}
	
	
	
	
	public void bounce(){
		reboundleft--;
		MTColor myFillColor = this.getFillColor();
		MTColor myStrokeColor = this.getStrokeColor();
		float alpha = myFillColor.getAlpha();
		alpha*=reboundleft/(float)Constants.bulletMaxRebound;
		//alpha+=20;
		MTColor newFillCol = new MTColor(myFillColor.getR(),myFillColor.getG(),myFillColor.getB());
		newFillCol.setAlpha(alpha);
		MTColor newStrokeCol=new MTColor(myStrokeColor.getR(),myStrokeColor.getG(),myStrokeColor.getB());
		newStrokeCol.setAlpha(alpha);
		this.setStrokeColor(newStrokeCol);
		this.setFillColor(newFillCol);
	}

	public int getReboundleft() {
		return reboundleft;
	}

	public void missedGoal() {
		if(mySender!=null){
		mySender.missedGoal();
		}
		
	}
	
	public void reachedGoal(){
		if(mySender!=null){
			mySender.reachedGoal();
		}

	}
	

	
	

}
