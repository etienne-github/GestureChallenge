package popup;

import model.Constants;

import org.jbox2d.dynamics.World;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import physic.shape.PhysicsShield;
import processing.core.PApplet;

public class FakePlayerRotableShield extends PhysicsShield {

	IntermediateHelpSequence myHS;
	
	public IntermediateHelpSequence getMyHS(){
		return myHS;
	}
	
	public FakePlayerRotableShield(Vector3D position, IntermediateHelpSequence hS, MTColor color
			) {
		super(Constants.shieldBigRadius, Constants.shieldSmallRadius, Constants.shieldSmallDef, Constants.shieldBigDef, Constants.shieldCoveredAngle, position,hS.getMyHHP().getMyScene().getMTApplication(),
				hS.getMyHHP().getMyScene().getWorld(), 0f, 0f, 0f, hS.getMyHHP().getMyScene().getScale(), color);
		this.setName("FakePlayerShield");
		myHS=hS;
	}


		public void caughtBullet() {
			this.myHS.caughtBullet();	
		}


}
