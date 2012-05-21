package playerinterface;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.mt4j.input.inputData.InputCursor;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import physic.shape.PhysicsRectangle;
import physic.shape.util.PhysicsHelper;
import processing.core.PGraphics;
import scene.GestureChallengeScene;
import util.math.Geometry;


public class PlayerPanShield extends PhysicsRectangle{
	
	//private MTColor myColor;
	private Vector3D referenceSegment;
	private Vector3D relativeSegment;
	//private float myScale;
	
	
	/**
	 * Creation of a temporary shield between the two fingers
	 * of the player who executed a pan gesture on his
	 * PlayerMovableShieldArea
	 * 
	 * @param PI			The player's interface
	 * @param startPoint	The first Cursor in the pan gesture
	 * @param endPoint		The second Cursor in the pan gesture
	 */
	public PlayerPanShield(PlayerInterface PI, InputCursor startPoint, InputCursor endPoint, int avoidCollision) {
		super(new Vector3D(Math.abs(startPoint.getCurrentEvtPosX()+endPoint.getCurrentEvtPosX())/2,Math.abs(startPoint.getCurrentEvtPosY()+endPoint.getCurrentEvtPosY())/2), 
				(float) Math.sqrt(Math.pow(startPoint.getCurrentEvtPosX()-endPoint.getCurrentEvtPosX(), 2)+Math.pow(startPoint.getCurrentEvtPosY()-endPoint.getCurrentEvtPosY(), 2)),
				2.0f,
				PI.myGCS.getMTApplication(),
				PI.myGCS.getWorld(),
				0f,
				0f,
				0f, 
				PI.myGCS.getScale()
				);
		//Avoid collision with our bullets
		//FIXME Avoid some collisions but not every of them
		/*this.getBody().getShapeList().m_filter.categoryBits=avoidCollision;
		this.getBody().getShapeList().m_filter.maskBits=avoidCollision;
		this.getBody().getShapeList().m_filter.groupIndex=0;*/

		//System.out.println("P"+PI.myNumber+" movableShieldSuper("+super.getBody().getShapeList().m_filter.categoryBits+")("+super.getBody().getShapeList().m_filter.maskBits+")");

Shape shape=super.getBody().getShapeList();
		
		for (Shape s = super.getBody().getShapeList();
			     s != null;
			     s = s.getNext()){
			s.m_filter.categoryBits=avoidCollision;
			s.m_filter.maskBits=avoidCollision;
			s.m_filter.groupIndex=0;
			//System.out.println(i);
		}
		

		shape=this.getBody().getShapeList();
		for (Shape s = this.getBody().getShapeList();
			     s != null;
			     s = s.getNext()){
			s.m_filter.categoryBits=avoidCollision;
			s.m_filter.maskBits=avoidCollision;
			s.m_filter.groupIndex=0;
			//System.out.println(i);
		}
		
		
		//The shield is now horizontal, we have to rotate it to replace it
		//between the two fingers
		referenceSegment = new Vector3D(1,0);
		relativeSegment = new Vector3D(startPoint.getCurrentEvtPosX()-endPoint.getCurrentEvtPosX(), startPoint.getCurrentEvtPosY()-endPoint.getCurrentEvtPosY());
		
		//this.getBody().setXForm(new Vec2(Math.abs(startPoint.getCurrentEvtPosX()+endPoint.getCurrentEvtPosX())/2,Math.abs(startPoint.getCurrentEvtPosY()+endPoint.getCurrentEvtPosY())/2), Geometry.orientedRadianAngleBetween(referenceSegment, relativeSegment));

		this.getBody().setXForm(this.getBody().getPosition(), Geometry.orientedRadianAngleBetween(referenceSegment, relativeSegment));
		referenceSegment = relativeSegment;
		
		//We use the color of the player to draw the component
		setFillColor(PI.myColor);
		//setStrokeColor(PI.myColor);
		setNoStroke(true);
		

		
		shape=super.getBody().getShapeList();
		
		for (Shape s = super.getBody().getShapeList();
			     s != null;
			     s = s.getNext()){
			s.m_filter.categoryBits=avoidCollision;
			s.m_filter.maskBits=avoidCollision;
			s.m_filter.groupIndex=0;
			//System.out.println(i);
		}
		

		shape=this.getBody().getShapeList();
		for (Shape s = this.getBody().getShapeList();
			     s != null;
			     s = s.getNext()){
			s.m_filter.categoryBits=avoidCollision;
			s.m_filter.maskBits=avoidCollision;
			s.m_filter.groupIndex=0;
			//System.out.println(i);
		}
		

		
	}
	
/*
	public void move(PlayerMovableShieldArea myArea, InputCursor startPoint, InputCursor endPoint){
		if(myArea.containsPointGlobal(startPoint.getPosition()) && myArea.containsPointGlobal(endPoint.getPosition())){
		// WORK BUT DOES NOT SCALE	THE BODY
			//Updating the body
			relativeSegment = new Vector3D(startPoint.getCurrentEvtPosX()-endPoint.getCurrentEvtPosX(), startPoint.getCurrentEvtPosY()-endPoint.getCurrentEvtPosY());
			System.out.println("Angle : "+Geometry.orientedDegreeAngleBetween(referenceSegment, relativeSegment));
			this.getBody().setXForm(new Vec2(PhysicsHelper.scaleDown((startPoint.getCurrentEvtPosX()+endPoint.getCurrentEvtPosX())/2,myScale),PhysicsHelper.scaleDown((startPoint.getCurrentEvtPosY()+endPoint.getCurrentEvtPosY())/2,myScale)), this.getBody().getAngle()+Geometry.orientedRadianAngleBetween(referenceSegment, relativeSegment));
			referenceSegment = relativeSegment;
			this.setWidthXYGlobal((float) Math.sqrt(Math.pow(startPoint.getCurrentEvtPosX()-endPoint.getCurrentEvtPosX(), 2)+Math.pow(startPoint.getCurrentEvtPosY()-endPoint.getCurrentEvtPosY(), 2)));
			 
		}
		
	}
	*/
}



