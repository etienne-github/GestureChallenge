package popup;

import model.GameModel;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.components.visibleComponents.widgets.MTTextField;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;
import org.mt4j.util.font.FontManager;
import org.mt4j.util.font.IFont;
import org.mt4j.util.math.Vector3D;

import physic.shape.PhysicsCircle;
import physic.shape.util.PhysicsHelper;
import playerinterface.PlayerBullet;
import scene.GestureChallengeScene;

public class BeginnerLevelHelpPopup extends HelpPopup{

	PhysicsCircle myBullet;
	MTTextArea helpMessage;
	GestureChallengeScene scene;
	FakePlayerAimGoal myFPG;

	public BeginnerLevelHelpPopup(GameModel gm, GestureChallengeScene scene,
			String name, Vector3D position, float angle) {
		super(gm, scene, name, position, angle);
		this.scene =scene;
	/*	myBullet = new PhysicsCircle(scene.getMTApplication(), getCenterPointGlobal(), 10, ((GestureChallengeScene) scene).getWorld(), 1.0f, 0, 1, ((GestureChallengeScene) scene).getScale());
		myBullet.setFillColor(MTColor.BLUE);
		myBullet.setStrokeColor(MTColor.SILVER);
		myBullet.setStrokeWeight(4);*/
		
		//FakePlayerBullet p = new FakePlayerBullet(scene.getMTApplication(), this.getCenterPointGlobal(), 10, scene.getWorld(), 1.0f, 0, 1, scene.getScale(),MTColor.SILVER,this);
		//PhysicsHelper.addAssistedDragJoint(((GestureChallengeScene) scene).getWorld(), p, true, ((GestureChallengeScene) scene).getScale(),this);
		myFPG= new FakePlayerAimGoal(scene.getMTApplication(), new Vector3D(scene.getMTApplication().width/2f,scene.getMTApplication().height/2f), scene.getWorld(), scene.getScale(), MTColor.SILVER, 0, scene,null);
		scene.getPhysicsContainer().addChild(myFPG);
		//scene.getPhysicsContainer().addChild(p);
		
		IFont f = FontManager.getInstance().createFont(scene.getMTApplication(), "REZ.ttf", 15);
		helpMessage = new MTTextArea(scene.getMTApplication(),this.getCenterPointGlobal().x,this.getCenterPointGlobal().y,this.getWidthXY(TransformSpace.GLOBAL),this.getHeightXY(TransformSpace.GLOBAL),f);
		this.addChild(helpMessage);
		helpMessage.setFontColor(MTColor.AQUA);
		helpMessage.setPickable(false);
		helpMessage.setNoFill(true);
		helpMessage.setNoStroke(true);
		helpMessage.setText("           Put your finger on the bullet\n                                   and hold it");
	
	}
	
	public void setHelpMessageText(String s,MTColor color){
		helpMessage.setFontColor(MTColor.NAVY);
		helpMessage.setText(s);
	}
	

	public void notifyBulletDestruction() {
		// TODO Add new Bullet
		//change Text
		
	}

	public void reachedGoal() {
		helpMessage.setFontColor(MTColor.LIME);
		helpMessage.setText("Good");
		//FakePlayerBullet p = new FakePlayerBullet(scene.getMTApplication(), this.getCenterPointGlobal(), 10, scene.getWorld(), 1.0f, 0, 1, scene.getScale(),MTColor.SILVER,this);
		//PhysicsHelper.addAssistedDragJoint(((GestureChallengeScene) scene).getWorld(), p, true, ((GestureChallengeScene) scene).getScale(),this);
		//scene.getPhysicsContainer().addChild(p);
		
	}

	public void missedGoal() {
		helpMessage.setFontColor(MTColor.RED);
		helpMessage.setText("Try again");
		//FakePlayerBullet p = new FakePlayerBullet(scene.getMTApplication(), this.getCenterPointGlobal(), 10, scene.getWorld(), 1.0f, 0, 1, scene.getScale(),MTColor.SILVER,this);
		//PhysicsHelper.addAssistedDragJoint(((GestureChallengeScene) scene).getWorld(), p, true, ((GestureChallengeScene) scene).getScale(),this);
		//scene.getPhysicsContainer().addChild(p);
	}

}