package popup;

import org.mt4j.input.inputData.InputCursor;

import playerinterface.PlayerPanShield;
import scene.GestureChallengeScene;

public class FakePlayerPanShield extends PlayerPanShield{

	AdvancedHelpSequence myHS;
	public FakePlayerPanShield(GestureChallengeScene scene,
			InputCursor startPoint, InputCursor endPoint, int avoidCollision, AdvancedHelpSequence hs) {
		super(scene, startPoint, endPoint, avoidCollision);
		this.setName("FakePlayerPanShield");
		this.myHS = hs;
	}
	public void caughtBullet() {
		this.myHS.caughtBullet();	
	}
}