package main;

import org.mt4j.MTApplication;
import org.mt4j.input.inputSources.MacTrackpadSource;

import GestureChallengeScene.GestureChallengeScene;




public class main extends MTApplication {
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		initialize();
	}
	
	@Override
	public void startUp() {
		getInputManager().registerInputSource(new MacTrackpadSource(this));
		addScene(new GestureChallengeScene(this, "Physics Example Scene"));
	}

}

