package playerinterface;

import java.util.Timer;
import java.util.TimerTask;

import model.Constants;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import physic.shape.PhysicsCircle;
import physic.shape.util.PhysicsHelper;



public class PlayerBulletLoader {
	Vec2 goalCenter;
	float angle;
	PlayerInterface myPI;
	int bulletsNumber;
	int bulletsAlive;
	int reloadDelay;
	int maxAnimationFrame=Constants.loaderAnimationFrames;
	int frame;
	Timer timer=new Timer();
	PlayerBullet[] myBullets;
	PhysicsCircle[] myBulletSlots;
	
	public PlayerBulletLoader(PlayerInterface pI){
		myPI = pI;
		bulletsNumber = Constants.loaderBulletsNumber;
		reloadDelay = Constants.loaderReloadDelay;
		myBullets = new PlayerBullet[bulletsNumber];
		myBulletSlots = new PhysicsCircle[bulletsNumber];
		load();
		
	}
	
	public void notifyDestruction(){
		
		bulletsAlive--;
		//System.out.println("BL notified, left "+bulletsAlive);
		if(bulletsAlive<=0){
			reload();
		}
	}
	
	public void load(){
		float interangle = (float) Math.toRadians(90/(float)bulletsNumber);
		//System.out.println("interangle: "+interangle);
		for(int i=0;i<bulletsNumber;i++){
			Vector3D pos = new Vector3D();
			//System.out.println("angle bulletL: "+myPI.myAngle);
			pos.x=(float) (
					(myPI.getMyPG().getBody().getPosition().x)*myPI.getMyGCS().getScale()+
					Math.cos(myPI.myAngle-Math.PI-(((bulletsNumber/2)-i-0.5)*interangle))*Constants.loaderBulletDistance
					);
			pos.y=(float) (
					(myPI.getMyPG().getBody().getPosition().y)*myPI.getMyGCS().getScale()+
					Math.sin(myPI.myAngle-Math.PI-(((bulletsNumber/2)-i-0.5)*interangle))*Constants.loaderBulletDistance
					);
			
			PhysicsCircle c = new PhysicsCircle(myPI.myGCS.getMTApplication(), pos, 13,
					myPI.getMyGCS().getWorld(), 1.0f, 0, 1,
					myPI.getMyGCS().getScale());
			c.removeAllGestureEventListeners();
			c.getBody().getShapeList().m_filter.categoryBits=0;
			c.getBody().getShapeList().m_filter.maskBits=0;
			c.getBody().getShapeList().m_filter.groupIndex=0;
			c.setNoStroke(true);
			c.setNoFill(true);
			c.setPickable(false);
			
			myBulletSlots[i]=c;
			
			PlayerBullet b = new PlayerBullet(myPI.myGCS.getMTApplication(), pos, 10,
					myPI.getMyGCS().getWorld(), 1.0f, 0, 1,
					myPI.getMyGCS().getScale(), myPI,this);
			myPI.getMyGCS().getPhysicsContainer().addChild(b);
			myPI.getMyGCS().getPhysicsContainer().addChild(c);		
			PhysicsHelper.addDragJoint(myPI.getMyGCS().getWorld(), b, b.getBody().isDynamic(), myPI.getMyGCS().getScale());
			bulletsAlive++;
			//System.out.println("bullet added in "+pos.toString());
		}
	}
	
	public void reload(){
		
		
		TimerTask myTask;
		
		for(PhysicsCircle c:myBulletSlots){
			c.setNoStroke(true);
		}
		
		float interangle = (float) Math.toRadians(90/(float)bulletsNumber);
		//System.out.println("interangle: "+interangle);
		for(int i=0;i<bulletsNumber;i++){
			Vector3D pos = new Vector3D();
			//System.out.println("angle bulletL: "+myPI.myAngle);
			pos.x=(float) (
					(myPI.getMyPG().getBody().getPosition().x)*myPI.getMyGCS().getScale()+
					Math.cos(myPI.myAngle-Math.PI-(((bulletsNumber/2)-i-0.5)*interangle))*Constants.loaderBulletDistance
					);
			pos.y=(float) (
					(myPI.getMyPG().getBody().getPosition().y)*myPI.getMyGCS().getScale()+
					Math.sin(myPI.myAngle-Math.PI-(((bulletsNumber/2)-i-0.5)*interangle))*Constants.loaderBulletDistance
					);
			

			
			PlayerBullet b = new PlayerBullet(myPI.myGCS.getMTApplication(), pos, 10,
					myPI.getMyGCS().getWorld(), 1.0f, 0, 1,
					myPI.getMyGCS().getScale(), myPI,this);
			

			
			myBullets[i]=b;
			
			
			b.removeAllGestureEventListeners();
			myPI.getMyGCS().getPhysicsContainer().addChild(b);
			//PhysicsHelper.addDragJoint(myPI.getMyGCS().getWorld(), b, b.getBody().isDynamic(), myPI.getMyGCS().getScale());
			b.removeAllGestureEventListeners();
			bulletsAlive++;
		}
			myTask = new TimerTask(){

				@Override
				public void run() {
					
					frame--;
					float alpha=255*((maxAnimationFrame-frame)/(float)maxAnimationFrame);
					//System.err.println("Animation loader ("+alpha+")/("+((maxAnimationFrame-frame)/(float)maxAnimationFrame)+")");
					MTColor newfillcol = new MTColor(myBullets[0].getFillColor().getR(),myBullets[0].getFillColor().getG(),myBullets[0].getFillColor().getB());
					MTColor newstrokecol = new MTColor(myBullets[0].getStrokeColor().getR(),myBullets[0].getStrokeColor().getG(),myBullets[0].getStrokeColor().getB());
					newfillcol.setAlpha(alpha);
					newstrokecol.setAlpha(alpha);
					for(PlayerBullet b:myBullets){
						b.setFillColor(newfillcol);
						b.setStrokeColor(newstrokecol);
					}
					if(frame<=0){
						this.cancel();
						for(int i=0;i<bulletsNumber;i++){
							PhysicsHelper.addDragJoint(myPI.getMyGCS().getWorld(), myBullets[i], myBullets[i].getBody().isDynamic(), myPI.getMyGCS().getScale());
							myBulletSlots[i].setNoStroke(false);
							myBulletSlots[i].setStrokeColor(MTColor.LIME);
						}

					}
					
					
				}
				
			};
			frame=maxAnimationFrame;
			timer.schedule(myTask,0,reloadDelay*1000/maxAnimationFrame);
		
	}
}
