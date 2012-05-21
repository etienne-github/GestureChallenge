package gesture.threefingersgesture;

import org.mt4j.components.interfaces.IMTComponent3D;
import org.mt4j.input.inputProcessors.IInputProcessor;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.util.math.Vector3D;


public class ThreeFingersGestureEvent extends MTGestureEvent{

	private float rotationAngle;
	public ThreeFingersGestureEvent(IInputProcessor source, int id,
			IMTComponent3D targetComponent, float rotAngle) {
		super(source, id, targetComponent);
		this.rotationAngle = rotAngle;
	}

	public float getRotationAngleRadian() {
		return rotationAngle;
	}

	public float getRotationAngleDegree() {
		return (float)Math.round((rotationAngle*360)/(2*Math.PI));
	}

}