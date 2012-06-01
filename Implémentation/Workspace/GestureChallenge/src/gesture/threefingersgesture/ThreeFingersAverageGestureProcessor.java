package gesture.threefingersgesture;


import org.mt4j.input.inputData.AbstractCursorInputEvt;
import org.mt4j.input.inputData.InputCursor;
import org.mt4j.input.inputProcessors.IInputProcessor;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.AbstractCursorProcessor;
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;
import util.math.Geometry;

public class ThreeFingersAverageGestureProcessor extends AbstractCursorProcessor {


	/** The applet. */
	private PApplet applet;

	/**
	 * Fixed segment in function of which we calculate the gesture's move.
	 */
	private Vector3D referenceSegment;
	private Vector3D referenceSegment2;
	private Vector3D referenceSegment3;

	private long mainCursorID;
	private long secondCursorID;
	private long thirdCursorID;

	private boolean justStarted;
	/**
	 * Instantiates a new pan processor two fingers.
	 * 
	 * @param app the app
	 */
	public ThreeFingersAverageGestureProcessor(PApplet app) {
		this.applet = applet;
		//TODO A tester dans le jeu pour déterminer quelle propriété mettre
		this.setLockPriority(2);
		this.justStarted = false;
	}



	public void cursorLocked(InputCursor cursor, IInputProcessor lockingprocessor) {
	}


	public void cursorUnlocked(InputCursor cursor) {
		InputCursor[] locked = getLockedCursorsArray();
		if(locked.length<3 && canLock(cursor)){
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
		if (locked.length >= 3){
			System.out.println(this.getName() + " has already enough cursors for this gesture - adding to unused ID:" + newCursor.getId());
		}
		else if(locked.length == 2){
			if(canLock(newCursor)){
				if(getLock(newCursor)){
					this.justStarted = true;
					System.out.println("Gesture's starting");
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
		Vector3D barycenter = getBarycenter(locked);
		if(locked.length==3){
			if(this.justStarted == true){
				this.justStarted = false;
				InputCursor mainCursor = locked[0];
				InputCursor secondCursor = locked[1];
				InputCursor thirdCursor = locked[2];
				mainCursorID = mainCursor.getId();
				secondCursorID = secondCursor.getId();
				thirdCursorID = thirdCursor.getId();
				referenceSegment = new Vector3D(mainCursor.getCurrentEvtPosX()-barycenter.x, mainCursor.getCurrentEvtPosY()-barycenter.y);
				referenceSegment2 = new Vector3D(secondCursor.getCurrentEvtPosX()-barycenter.x, secondCursor.getCurrentEvtPosY()-barycenter.y);
				referenceSegment3 = new Vector3D(thirdCursor.getCurrentEvtPosX()-barycenter.x, thirdCursor.getCurrentEvtPosY()-barycenter.y);

				this.fireGestureEvent(new ThreeFingersAverageGestureEvent(this, MTGestureEvent.GESTURE_STARTED, currentEvent.getCurrentTarget(), getAverageRotationAngle(locked)));
			}
			else{
				this.fireGestureEvent(new ThreeFingersAverageGestureEvent(this, MTGestureEvent.GESTURE_UPDATED, currentEvent.getCurrentTarget(), getAverageRotationAngle(locked)));
				InputCursor mainCursor = getCursorByID(locked,mainCursorID);
				InputCursor secondCursor = getCursorByID(locked,secondCursorID);
				InputCursor thirdCursor = getCursorByID(locked,thirdCursorID);
				referenceSegment = new Vector3D(mainCursor.getCurrentEvtPosX()-barycenter.x, mainCursor.getCurrentEvtPosY()-barycenter.y);
				referenceSegment2 = new Vector3D(secondCursor.getCurrentEvtPosX()-barycenter.x, secondCursor.getCurrentEvtPosY()-barycenter.y);
				referenceSegment3 = new Vector3D(thirdCursor.getCurrentEvtPosX()-barycenter.x, thirdCursor.getCurrentEvtPosY()-barycenter.y);

			}
		}	
	}


	public void cursorEnded(InputCursor inputCursor, AbstractCursorInputEvt currentEvent) {
		InputCursor[] locked = getLockedCursorsArray();
		if(locked.length==2){
			this.fireGestureEvent(new ThreeFingersAverageGestureEvent(this, MTGestureEvent.GESTURE_ENDED, currentEvent.getCurrentTarget(), (float) 0.0));
		}
	}


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

	private float getAverageRotationAngle(InputCursor[] cursors){
		InputCursor mainCursor = getCursorByID(cursors,mainCursorID);
		InputCursor secondCursor = getCursorByID(cursors,secondCursorID);
		InputCursor thirdCursor = getCursorByID(cursors,thirdCursorID);
		Vector3D barycenter = getBarycenter(cursors);
		Vector3D relativeSegment = new Vector3D(mainCursor.getCurrentEvtPosX()-barycenter.x,mainCursor.getCurrentEvtPosY()-barycenter.y);
		Vector3D relativeSegment2 = new Vector3D(secondCursor.getCurrentEvtPosX()-barycenter.x,secondCursor.getCurrentEvtPosY()-barycenter.y);
		Vector3D relativeSegment3 = new Vector3D(thirdCursor.getCurrentEvtPosX()-barycenter.x,thirdCursor.getCurrentEvtPosY()-barycenter.y);
		float averageAngle1 = Geometry.orientedRadianAngleBetween(referenceSegment, relativeSegment);
		float averageAngle2= Geometry.orientedRadianAngleBetween(referenceSegment2, relativeSegment2);
		float averageAngle3=Geometry.orientedRadianAngleBetween(referenceSegment3, relativeSegment3);
		//System.out.println("angles "+averageAngle1+" "+averageAngle2+" "+averageAngle3);
		
		//RETURN AVERAGE
		/*float averageAngle = averageAngle1+averageAngle2+averageAngle3;
		averageAngle/=3f;*/
		
		//RETURN MAX ABSOLUTE
		if(Math.abs(averageAngle1)>Math.abs(averageAngle2)){
			if(Math.abs(averageAngle1)>Math.abs(averageAngle3)){
				return averageAngle1;
			}else{
				return averageAngle3;
			}
		}else{
			if(Math.abs(averageAngle2)>Math.abs(averageAngle3)){
				return averageAngle2;
			}else{
				return averageAngle3;
			}
		}
	}
	
	private float getRotationAngle(InputCursor[] cursors){
		InputCursor mainCursor = getCursorByID(cursors,mainCursorID);
		Vector3D barycenter = getBarycenter(cursors);
		Vector3D relativeSegment = new Vector3D(mainCursor.getCurrentEvtPosX()-barycenter.x,mainCursor.getCurrentEvtPosY()-barycenter.y);
		return Geometry.orientedRadianAngleBetween(referenceSegment, relativeSegment);
	}




	private InputCursor getCursorByID(InputCursor[] cursors,long cursorID){
		for(int i=0;i<cursors.length;i++){
			if(cursors[i].getId()==cursorID){
				return cursors[i];
			}
		}
		return null;
	}
}