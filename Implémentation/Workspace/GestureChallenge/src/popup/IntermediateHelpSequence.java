package popup;

import java.util.Timer;

import gesture.threefingersgesture.ThreeFingersAverageGestureEvent;
import gesture.threefingersgesture.ThreeFingersAverageGestureProcessor;
import model.Constants;

import org.jbox2d.common.Vec2;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import physic.shape.util.PhysicsHelper;
import playerinterface.PlayerRotableShield;
import processing.core.PApplet;
import util.math.Geometry;
import codeanticode.gsvideo.GSMovie;

public class IntermediateHelpSequence extends HelpSequence {
	FakePlayerRotableShield myFPS;
	FakePlayerOwnGoal myFPOG;
	float aimX;
	float aimY;
	FakePlayerBullet p;

		
	public IntermediateHelpSequence(String moviePath,
			GSMovie preloadedMovieClip, PApplet app, HybridHelpPopUp h) {
		super(moviePath, preloadedMovieClip, app, h);
		// TODO Auto-generated constructor stub
	}
	
	
	public void sendRandom(){
		
		System.err.println(this.hashCode()+" sends random");
		
		float dAngle = (float) (Math.random()*80);
		float sign = (float) (Math.random()*100);
		if(sign>50){
			dAngle*=-1;
		}
		
		Vector3D v = new Vector3D(0,(float) ((Constants.radiusCenterGoals-Constants.radiusGoals*1.5)*-1));
				
		v.rotateZ((float) (this.getMyHHP().myAngle-Math.PI/2f-Math.PI/15f));
		System.err.println("ang "+this.getMyHHP().myAngle);
		System.err.println("dAng "+dAngle);
		System.err.println("aimed goal" +myFPOG.hashCode()+" at : "+myFPOG.getCenterPointGlobal());
		v.rotateZ((float) Math.toRadians(dAngle));

		p = new FakePlayerBullet(getMyHHP().getMyScene().getMTApplication(), new Vector3D(getMyHHP().getCenterPointGlobal().x+v.x,getMyHHP().getCenterPointGlobal().y+v.y), 10, getMyHHP().getMyScene().getWorld(), 1.0f, 0, 1, getMyHHP().getMyScene().getScale(),MTColor.SILVER,null);
		this.getMyHHP().myScene.getPhysicsContainer().addChild(p);
		
		Vector3D aim = new Vector3D(aimX,aimY);
		
		float a = Geometry.orientedRadianAngleBetween(p.getCenterPointGlobal(), aim);
		Vector3D v2 =  aim.getSubtracted(p.getCenterPointGlobal()); /*new Vector3D(1,0)*/
		//Vector3D v2 =  p.getCenterPointGlobal().getSubtracted(myFPOG.getCenterPointGlobal()); /*new Vector3D(1,0)*/
		v2=v2.getNormalized();
		//v2.rotateZ(a*-1);
		Vec2 v3 = new Vec2(v2.x*8,v2.y*8);
		

		p.getBody().applyImpulse(v3, p.getBody().getPosition());
		
		
		
		
	}
	

	@Override
	protected void remove() {
		
		
		this.getMyHHP().getMyScene().getMTApplication().invokeLater(new Runnable(){

			@Override
			public void run() {
				IntermediateHelpSequence.this.getMyHHP().getMyScene().getWorld().destroyBody(myFPS.getBody());
				IntermediateHelpSequence.this.getMyHHP().getMyScene().getPhysicsContainer().removeChild(myFPS);
				IntermediateHelpSequence.this.getMyHHP().getMyScene().getWorld().destroyBody(myFPOG.getBody());
				IntermediateHelpSequence.this.getMyHHP().getMyScene().getPhysicsContainer().removeChild(myFPOG);
				IntermediateHelpSequence.this.getMyHHP().getMyScene().getWorld().destroyBody(p.getBody());
				IntermediateHelpSequence.this.getMyHHP().getMyScene().getPhysicsContainer().removeChild(p);
				
			}
			
		});
		
		
		this.getMyHHP().setPickable(false);
		this.getMyHHP().removeAllGestureEventListeners();
		
	}

	@Override
	protected void setUp() {
		this.getMyHHP().setHelpMessage("Rotate with three fingers", MTColor.AQUA);
		
		float x = this.getMyHHP().getCenterPointGlobal().x -(float) (Math.cos(this.getMyHHP().myAngle)*Constants.shieldDistance);
		float y = this.getMyHHP().getCenterPointGlobal().y -(float) (Math.sin(this.getMyHHP().myAngle)*Constants.shieldDistance);
		
		myFPS=new FakePlayerRotableShield(new Vector3D(x,y), this, MTColor.SILVER);
		
		myFPS.getBody().setXForm(
				myFPS.getBody().getPosition(), (float) (this.getMyHHP().myAngle+Math.PI/2f)
		);
		this.getMyHHP().myScene.getPhysicsContainer().addChild(myFPS);
		
		myFPOG=new FakePlayerOwnGoal(this.getMyHHP().myScene.getMTApplication(),this.getMyHHP().getCenterPointGlobal(),this.getMyHHP().myScene.getWorld(),this.getMyHHP().myScene.getScale(),MTColor.SILVER,0,this.getMyHHP().myScene,this);
		this.getMyHHP().myScene.getPhysicsContainer().addChild(myFPOG);
		
		ThreeFingersAverageGestureProcessor myThreeFingersProcessor = new ThreeFingersAverageGestureProcessor(this.getMyHHP().myScene.getMTApplication());
		this.getMyHHP().registerInputProcessor(myThreeFingersProcessor);
		this.getMyHHP().addGestureListener(ThreeFingersAverageGestureProcessor.class, new IGestureEventListener() {
			public boolean processGestureEvent(MTGestureEvent ge) {
				ThreeFingersAverageGestureEvent evt = (ThreeFingersAverageGestureEvent) ge;
				float angle = evt.getRotationAngleRadian();
				float beta = myFPS.getBody().getAngle();
				Vector3D center = myFPS.getCenterPointGlobal();
				Vec2 newPos = new Vec2(PhysicsHelper.scaleDown((float)(center.x-Constants.shieldDistance*Math.sin(angle+beta)),IntermediateHelpSequence.this.getMyHHP().myScene.getScale()),PhysicsHelper.scaleDown((float)(center.y+Constants.shieldDistance*Math.cos(angle+beta)), IntermediateHelpSequence.this.getMyHHP().myScene.getScale()));
				myFPS.getBody().setXForm(newPos, angle+myFPS.getBody().getAngle());
				return false;
			}
		});
		this.getMyHHP().setPickable(true);
		aimX= myFPOG.getCenterPointGlobal().x;
		aimY= myFPOG.getCenterPointGlobal().y;
		this.sendRandom();

		
	}
	
	void missedBullet(){
		this.getMyHHP().setHelpMessage("Missed try again", MTColor.RED);
		this.sendRandom();
	}
	
	void caughtBullet(){
		this.getMyHHP().setHelpMessage("Good job", MTColor.LIME);
		this.sendRandom();
	}

}
