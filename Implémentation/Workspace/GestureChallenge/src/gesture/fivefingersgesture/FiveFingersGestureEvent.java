package gesture.fivefingersgesture;

import org.mt4j.components.interfaces.IMTComponent3D;
import org.mt4j.input.inputProcessors.IInputProcessor;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.util.math.Vector3D;


public class FiveFingersGestureEvent extends MTGestureEvent{
	
	private float rotationAngle;
	public FiveFingersGestureEvent(IInputProcessor source, int id,
			IMTComponent3D targetComponent, float rotAngle) {
		super(source, id, targetComponent);
		this.rotationAngle = rotAngle;
	}

	public float getRotationAngle() {
		return rotationAngle;
	}
	

}
