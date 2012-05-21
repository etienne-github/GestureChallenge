package playerinterface;

import model.Constants;

import org.jbox2d.dynamics.World;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import physic.shape.PhysicsShield;
import processing.core.PApplet;

public class PlayerRotableShield extends PhysicsShield {

	PlayerInterface myPI;
	
	public PlayerInterface getMyPI(){
		return myPI;
	}
	
	public PlayerRotableShield(Vector3D position, PlayerInterface PI
			) {
		super(Constants.shieldBigRadius, Constants.shieldSmallRadius, Constants.shieldSmallDef, Constants.shieldBigDef, Constants.shieldCoveredAngle, position, PI.getMyGCS().getMTApplication(),
				PI.getMyGCS().getWorld(), 0f, 0f, 0f, PI.getMyGCS().getScale(), PI.getMyColor());
		myPI=PI;
		this.setName("PlayerShield");
	}

}
