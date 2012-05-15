package PlayerInterface;

import org.jbox2d.dynamics.World;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import GameModel.Constants;

import physicsShapes.PhysicsCircle;
import processing.core.PApplet;

public class PlayerMovableShieldArea extends PhysicsCircle {

	public PlayerMovableShieldArea(PlayerInterface PI, Vector3D pos) {
		super(
				PI.getMyGCS().getMTApplication(),
				new Vector3D(
						(float) (pos.x+Math.cos(PI.myAngle)*(Constants.areaRadius-Constants.radiusCenterGoals)),
						(float) (pos.y+Math.sin(PI.myAngle)*(Constants.areaRadius-Constants.radiusCenterGoals))
				),
				Constants.movableShieldAreaRadius,
				PI.getMyGCS().getWorld(),
				0f,
				0f,
				0f,
				PI.getMyGCS().getScale()
			);
		this.getBody().getShapeList().m_filter.categoryBits=0;
		this.getBody().getShapeList().m_filter.groupIndex=0;
		this.getBody().getShapeList().m_filter.maskBits=0;
		this.setNoFill(true);
		this.setStrokeWeight(3);
		this.setStrokeColor(PI.getMyColor());
		MTColor c = this.getStrokeColor();
		c.setAlpha(75f);
		setStrokeColor(c);
		// TODO Auto-generated constructor stub
	}

}
