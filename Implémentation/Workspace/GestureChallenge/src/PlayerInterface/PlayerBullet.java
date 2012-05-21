package playerinterface;

import model.Constants;

import org.jbox2d.dynamics.World;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import physic.shape.PhysicsCircle;
import processing.core.PApplet;

public class PlayerBullet extends PhysicsCircle {

	PlayerInterface mySender;
	PlayerBulletLoader myBulletLoader;
	int reboundleft;
	int myScore;
	
	public PlayerBullet(PApplet applet, Vector3D centerPoint, float radius,
			World world, float density, float friction, float restitution,
			float worldScale, PlayerInterface sender, PlayerBulletLoader loader) {
		super(applet, centerPoint, radius, world, density, friction, restitution,
				worldScale);
		mySender=sender;
		myBulletLoader=loader;
		reboundleft=Constants.bulletMaxRebound;
		this.setFillColor(mySender.getMyColor());
		MTColor darker = new MTColor(mySender.getMyColor().getR()/2f,mySender.getMyColor().getG()/2f,mySender.getMyColor().getB()/2f);
		this.setStrokeColor(darker);
		this.setStrokeWeight(4);
		mySender = sender;
		this.getBody().getShapeList().m_filter.categoryBits=mySender.getMyBulletCat();
		this.getBody().getShapeList().m_filter.maskBits=mySender.getMyBulletMask();
		this.getBody().getShapeList().m_filter.groupIndex=0;
		System.out.println("PP"+mySender.myNumber+" cat("+mySender.getMyBulletCat()+")/mask("+mySender.getMyBulletMask()+")");
		this.setName("PlayerBullet");
		this.myScore=Constants.bulletScore;
		
	}
	
	public void changeSender(PlayerInterface newSender){
		this.mySender=newSender;
		this.setFillColor(mySender.getMyColor());
		MTColor darker = new MTColor(mySender.getMyColor().getR()/2f,mySender.getMyColor().getG()/2f,mySender.getMyColor().getB()/2f);
		this.setStrokeColor(darker);
		this.getBody().getShapeList().m_filter.categoryBits=mySender.getMyBulletCat();
		this.getBody().getShapeList().m_filter.maskBits=mySender.getMyBulletMask();
		this.getBody().getShapeList().m_filter.groupIndex=0;
	}
	
	public void score(){
		this.mySender.score(myScore);
		
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

	public void die() {
		myBulletLoader.notifyDestruction();
		
	}
	


}
