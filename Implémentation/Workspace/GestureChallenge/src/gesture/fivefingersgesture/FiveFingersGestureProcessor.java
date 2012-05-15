package gesture.fivefingersgesture;


import java.util.ArrayList;

import javax.xml.stream.Location;

import org.mt4j.input.inputData.AbstractCursorInputEvt;
import org.mt4j.input.inputData.InputCursor;
import org.mt4j.input.inputProcessors.IInputProcessor;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.AbstractCursorProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.rotateProcessor.RotateEvent;
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;

public class FiveFingersGestureProcessor extends AbstractCursorProcessor {

	
	/** The applet. */
	private PApplet applet;
	
	/**
	 * Fixed segment in function of which we calculate the gesture's move.
	 */
	private Vector3D referenceSegment;


	/**
	 * Instantiates a new pan processor two fingers.
	 * 
	 * @param app the app
	 */
	public FiveFingersGestureProcessor(PApplet app) {
		this.applet = applet;
		//TODO A tester dans le jeu pour déterminer quelle propriété mettre
		this.setLockPriority(5);
	}
	
	
	
	public void cursorLocked(InputCursor cursor, IInputProcessor lockingprocessor) {
	}

	
	public void cursorUnlocked(InputCursor cursor) {
		InputCursor[] locked = getLockedCursorsArray();
		if(locked.length<5 && canLock(cursor)){
			if(getLock(cursor)){
				System.out.println("A cursor freed by another processor has just been locked");
			}
		}
		
	}

	/**
	 * Lock the five first cursors
	 */
	public void cursorStarted(InputCursor newCursor, AbstractCursorInputEvt positionEvent) {
		InputCursor[] locked = getLockedCursorsArray();
		if (locked.length >= 5){
			System.out.println(this.getName() + " has already enough cursors for this gesture - adding to unused ID:" + newCursor.getId());
		}
		else if(locked.length == 4){
			if(canLock(newCursor)){
				if(getLock(newCursor)){
					Vector3D barycenter = getBarycenter(locked);
					InputCursor firstCursor = locked[0];
					referenceSegment = new Vector3D(firstCursor.getCurrentEvtPosX()-barycenter.x, firstCursor.getCurrentEvtPosY()-barycenter.y);
					this.fireGestureEvent(new FiveFingersGestureEvent(this, MTGestureEvent.GESTURE_STARTED, positionEvent.getCurrentTarget(), getRotationAngle(locked)));
				}
			}
			else{
				System.out.println("Cursor detected, but could not be locked");
			}
		}
		else{
			if(canLock(newCursor)){
				if(getLock(newCursor)){
					System.out.println("One more cursor, but still not enough to begin");
				}
			}
			else{
				System.out.println("Cursor detected, but could not be locked");
			}
		}
		
	}

	
	public void cursorUpdated(InputCursor inputCursor, AbstractCursorInputEvt currentEvent) {		
		InputCursor[] locked = getLockedCursorsArray();
		if(locked.length==5){
			Vector3D barycenter = getBarycenter(locked);
			this.fireGestureEvent(new FiveFingersGestureEvent(this, MTGestureEvent.GESTURE_UPDATED, currentEvent.getCurrentTarget(), getRotationAngle(locked)));
			referenceSegment = new Vector3D(locked[0].getCurrentEvtPosX()-barycenter.x, locked[0].getCurrentEvtPosY()-barycenter.y);
		}		
	}

	
	public void cursorEnded(InputCursor inputCursor, AbstractCursorInputEvt currentEvent) {}

	
	public String getName() {
	
		return null;
	}
	
	private Vector3D getBarycenter(InputCursor[] cursors){
		float x=0, y=0, numberOfCursors = cursors.length;
		for(InputCursor currentCursor:cursors){
			x += currentCursor.getCurrentEvtPosX();
			y += currentCursor.getCurrentEvtPosY();
		}
		x = x/numberOfCursors;
		y = y/numberOfCursors;
		return new Vector3D(x, y);
	}
	
	private float getRotationAngle(InputCursor[] cursors){
		InputCursor firstCursor = getLockedCursorsArray()[0];
		Vector3D barycenter = getBarycenter(cursors);
		Vector3D relativeSegment = new Vector3D(firstCursor.getCurrentEvtPosX()-barycenter.x,firstCursor.getCurrentEvtPosY()-barycenter.y);
		float cosinus = dotProduct(referenceSegment,relativeSegment);
		float sinus = crossProduct(referenceSegment,relativeSegment);
		return (float) Math.atan2(sinus, cosinus);
	}
	
	private float dotProduct(Vector3D refSeg, Vector3D newSeg){
		return (refSeg.x*newSeg.x)+(refSeg.y*newSeg.y);
	}
	private float crossProduct(Vector3D refSeg, Vector3D newSeg){
		return (refSeg.x*newSeg.y)-(refSeg.y*newSeg.x);
	}
	
}
