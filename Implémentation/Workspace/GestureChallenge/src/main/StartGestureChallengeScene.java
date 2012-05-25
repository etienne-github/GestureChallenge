package main;

import model.GameModel;

import org.mt4j.MTApplication;
import org.mt4j.input.inputSources.MacTrackpadSource;
import org.mt4j.util.math.Vector3D;

import physic.shape.PhysicsRectangle;
import popup.Popup;
import scene.GestureChallengeScene;





public class StartGestureChallengeScene extends MTApplication {
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		initialize();
	}
	
	@Override
	public void startUp() {
		getInputManager().registerInputSource(new MacTrackpadSource(this));
		GestureChallengeScene GCS = new GestureChallengeScene(this, "GestureChallenge");
		addScene(GCS);
		GameModel GM = new GameModel(GCS);
		GCS.setGM(GM);
		
		GM.newGame();
		/*
		GM.setPlayerNumber(3);
		GM.createInterfaces();
		GM.subscribeInterfaces();
		GM.initGame();
		Popup p = new Popup<Integer>("player_number","Number of players ?", GCS, GM, new Vector3D(GCS.getMTApplication().width/2f,GCS.getMTApplication().height/2f), 300);
		
		p.addPopupItem("2 players", 2);
		p.addPopupItem("3 players", 3);
		p.addPopupItem("4 players", 4);*/

	}

}

