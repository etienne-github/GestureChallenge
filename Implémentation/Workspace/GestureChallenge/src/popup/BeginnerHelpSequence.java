package popup;

import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import physic.shape.util.PhysicsHelper;
import processing.core.PApplet;
import scene.GestureChallengeScene;
import codeanticode.gsvideo.GSMovie;

public class BeginnerHelpSequence extends HelpSequence {
	FakePlayerBullet p;
	FakePlayerAimGoal myFPG;

	public BeginnerHelpSequence(String moviePath, GSMovie preloadedMovieClip,
			PApplet app, HybridHelpPopUp h) {
		super(moviePath, preloadedMovieClip, app, h);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void remove() {
		this.getMyHHP().getMyScene().getMTApplication().invokeLater(new Runnable(){

			@Override
			public void run() {
				BeginnerHelpSequence.this.getMyHHP().getMyScene().getWorld().destroyBody(p.getBody());
				BeginnerHelpSequence.this.getMyHHP().getMyScene().getPhysicsContainer().removeChild(p);
				BeginnerHelpSequence.this.getMyHHP().getMyScene().getWorld().destroyBody(myFPG.getBody());
				BeginnerHelpSequence.this.getMyHHP().getMyScene().getPhysicsContainer().removeChild(myFPG);
				
			}
			
		});
		
	}

	@Override
	protected void setUp() {
		p = new FakePlayerBullet(getMyHHP().getMyScene().getMTApplication(), getMyHHP().getCenterPointGlobal(), 10, getMyHHP().getMyScene().getWorld(), 1.0f, 0, 1, getMyHHP().getMyScene().getScale(),MTColor.SILVER,this);
		PhysicsHelper.addAssistedDragJoint(getMyHHP().getMyScene().getWorld(), p, true, getMyHHP().getMyScene().getScale(),this.myHHP);		
		myFPG= new FakePlayerAimGoal(this.getMyHHP().getMyScene().getMTApplication(), new Vector3D(this.getMyHHP().getMyScene().getMTApplication().width/2f,this.getMyHHP().getMyScene().getMTApplication().height/2f), this.getMyHHP().getMyScene().getWorld(), this.getMyHHP().getMyScene().getScale(), MTColor.SILVER, 0, this.getMyHHP().getMyScene(),null);
		this.getMyHHP().getMyScene().getPhysicsContainer().addChild(myFPG);
		this.getMyHHP().getMyScene().getPhysicsContainer().addChild(p);	
		this.getMyHHP().setHelpMessage("Put your finger on the bullet", MTColor.AQUA);
	}
	
	public void missedGoal(){
		this.getMyHHP().setHelpMessage("Try again", MTColor.RED);
		p = new FakePlayerBullet(getMyHHP().getMyScene().getMTApplication(), getMyHHP().getCenterPointGlobal(), 10, getMyHHP().getMyScene().getWorld(), 1.0f, 0, 1, getMyHHP().getMyScene().getScale(),MTColor.SILVER,this);
		PhysicsHelper.addAssistedDragJoint(getMyHHP().getMyScene().getWorld(), p, true, getMyHHP().getMyScene().getScale(),this.myHHP);
		//myFPG= new FakePlayerAimGoal(this.getMyHHP().getMyScene().getMTApplication(), new Vector3D(this.getMyHHP().getMyScene().getMTApplication().width/2f,this.getMyHHP().getMyScene().getMTApplication().height/2f), this.getMyHHP().getMyScene().getWorld(), this.getMyHHP().getMyScene().getScale(), MTColor.SILVER, 0, this.getMyHHP().getMyScene(),null);
		this.getMyHHP().getMyScene().getPhysicsContainer().addChild(myFPG);
		this.getMyHHP().getMyScene().getPhysicsContainer().addChild(p);	
	}
	
	public void	reachedGoal(){
		this.getMyHHP().setHelpMessage("Good job", MTColor.LIME);
		p = new FakePlayerBullet(getMyHHP().getMyScene().getMTApplication(), getMyHHP().getCenterPointGlobal(), 10, getMyHHP().getMyScene().getWorld(), 1.0f, 0, 1, getMyHHP().getMyScene().getScale(),MTColor.SILVER,this);
		PhysicsHelper.addAssistedDragJoint(getMyHHP().getMyScene().getWorld(), p, true, getMyHHP().getMyScene().getScale(),this.myHHP);
		//myFPG= new FakePlayerAimGoal(this.getMyHHP().getMyScene().getMTApplication(), new Vector3D(this.getMyHHP().getMyScene().getMTApplication().width/2f,this.getMyHHP().getMyScene().getMTApplication().height/2f), this.getMyHHP().getMyScene().getWorld(), this.getMyHHP().getMyScene().getScale(), MTColor.SILVER, 0, this.getMyHHP().getMyScene(),null);
		this.getMyHHP().getMyScene().getPhysicsContainer().addChild(myFPG);
		this.getMyHHP().getMyScene().getPhysicsContainer().addChild(p);	
		
	}
	
}
