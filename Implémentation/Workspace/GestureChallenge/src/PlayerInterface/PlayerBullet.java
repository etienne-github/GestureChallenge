package playerinterface;

import org.jbox2d.dynamics.World;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import physic.shape.PhysicsCircle;
import processing.core.PApplet;

public class PlayerBullet extends PhysicsCircle {

	PlayerInterface mySender;
	
	public PlayerBullet(PApplet applet, Vector3D centerPoint, float radius,
			World world, float density, float friction, float restitution,
			float worldScale, PlayerInterface sender) {
		super(applet, centerPoint, radius, world, density, friction, restitution,
				worldScale);
		mySender=sender;
		this.setFillColor(mySender.getMyColor());
		MTColor darker = new MTColor(mySender.getMyColor().getR()/2f,mySender.getMyColor().getG()/2f,mySender.getMyColor().getB()/2f);
		this.setStrokeColor(darker);
		this.setStrokeWeight(4);
		mySender = sender;
		this.getBody().getShapeList().m_filter.categoryBits=(int) Math.pow(2,mySender.playerNumber+mySender.myNumber+1);
		//this.getBody().getShapeList().m_filter.categoryBits=mySender.getMyBulletMask();
		this.getBody().getShapeList().m_filter.maskBits=mySender.getMyBulletMask();
		this.getBody().getShapeList().m_filter.groupIndex=0;
		// TODO Auto-generated constructor stub
		
		
		boolean collide_wall = (this.getBody().getShapeList().m_filter.maskBits & 1) != 0 && (this.getBody().getShapeList().m_filter.categoryBits & 1) != 0;
		boolean collide_P1 = (this.getBody().getShapeList().m_filter.maskBits & 2) != 0 && (this.getBody().getShapeList().m_filter.categoryBits & 2) != 0;
		boolean collide_P2 = (this.getBody().getShapeList().m_filter.maskBits & 4) != 0 && (this.getBody().getShapeList().m_filter.categoryBits & 4) != 0;
		boolean collide_P3 = (this.getBody().getShapeList().m_filter.maskBits & 8) != 0 && (this.getBody().getShapeList().m_filter.categoryBits & 8) != 0;
		boolean collide_PP1 = (this.getBody().getShapeList().m_filter.maskBits & (1+4+8)) != 0 && (this.getBody().getShapeList().m_filter.categoryBits & (1+4+8)) != 0;
		boolean collide_PP2 = (this.getBody().getShapeList().m_filter.maskBits & (1+2+8)) != 0 && (this.getBody().getShapeList().m_filter.categoryBits & (1+2+8)) != 0;
		boolean collide_PP3 = (this.getBody().getShapeList().m_filter.maskBits & (1+2+4)) != 0 && (this.getBody().getShapeList().m_filter.categoryBits & (1+2+4)) != 0;
		System.out.println("bullet P"+mySender.myNumber+": cat("+this.getBody().getShapeList().m_filter.categoryBits+") / mask("+this.getBody().getShapeList().m_filter.maskBits+")");
		System.out.println("bullet P"+mySender.myNumber+" collide with wall : "+collide_wall);
		System.out.println("bullet P"+mySender.myNumber+" collide with P1 : "+collide_P1);
		System.out.println("bullet P"+mySender.myNumber+" collide with P2 : "+collide_P2);
		System.out.println("bullet P"+mySender.myNumber+" collide with P3 : "+collide_P3);
		System.out.println("bullet P"+mySender.myNumber+" collide with PP1 : "+collide_PP1);
		System.out.println("bullet P"+mySender.myNumber+" collide with PP2 : "+collide_PP2);
		System.out.println("bullet P"+mySender.myNumber+" collide with PP3 : "+collide_PP3);
		
	}
	
	public void changeSender(PlayerInterface newSender){
		this.mySender=newSender;
		this.setFillColor(mySender.getMyColor());
		MTColor darker = new MTColor(mySender.getMyColor().getR()/2f,mySender.getMyColor().getG()/2f,mySender.getMyColor().getB()/2f);
		this.setStrokeColor(darker);
	}

}
