package PlayerInterface;

import org.jbox2d.dynamics.World;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import physicsShapes.PhysicsCircle;
import processing.core.PApplet;

public class PlayerBullet extends PhysicsCircle {

	PlayerInterface mySender;
	
	public PlayerBullet(PApplet applet, Vector3D centerPoint, float radius,
			World world, float density, float friction, float restitution,
			float worldScale, PlayerInterface sender) {
		super(applet, centerPoint, radius, world, density, friction, restitution,
				worldScale);
		this.setFillColor(mySender.getMyColor());
		MTColor darker = new MTColor(mySender.getMyColor().getR()/2f,mySender.getMyColor().getG()/2f,mySender.getMyColor().getB()/2f);
		this.setStrokeColor(darker);
		this.setStrokeWeight(4);
		mySender = sender;
		this.getBody().getShapeList().getFilterData().maskBits=1+mySender.getMyCollisionID();
		// TODO Auto-generated constructor stub
	}
	
	public void changeSender(PlayerInterface newSender){
		this.mySender=newSender;
		this.setFillColor(mySender.getMyColor());
		MTColor darker = new MTColor(mySender.getMyColor().getR()/2f,mySender.getMyColor().getG()/2f,mySender.getMyColor().getB()/2f);
		this.setStrokeColor(darker);
	}

}
