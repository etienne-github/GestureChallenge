package playerinterface;

import gesture.threefingersgesture.ThreeFingersAverageGestureEvent;
import gesture.threefingersgesture.ThreeFingersAverageGestureProcessor;
import gesture.threefingersgesture.ThreeFingersGestureEvent;
import gesture.threefingersgesture.ThreeFingersGestureProcessor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import model.Constants;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.panProcessor.PanProcessorTwoFingers;
import org.mt4j.input.inputProcessors.componentProcessors.panProcessor.PanTwoFingerEvent;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import physic.shape.util.PhysicsHelper;
import scene.GestureChallengeScene;


public class PlayerInterface implements PropertyChangeListener {

	MTColor myColor;
	int myNumber;
	int myScore;
	GestureChallengeScene myGCS;
	PlayerGoal myPG;
	PlayerRotableShield myPS;
	PlayerBulletLoader myBL;
	float myAngle;
	int playerNumber;
	int myCollisionID;
	int myBulletMask;
	int myBulletCat;
	PlayerMovableShieldArea myMSA;
	PlayerRotableShieldArea myRSA;
	PlayerDisplay myPD;
	PlayerPanShield myPanShield;
	
	
	
	
	public GestureChallengeScene getMyGCS() {
		return myGCS;
	}



	public PlayerGoal getMyPG() {
		return myPG;
	}


	public int getMyBulletCat(){
		return myBulletCat;
	}

	public int getMyBulletMask() {
		return myBulletMask;
	}



	public MTColor getMyColor() {
		return myColor;
	}



	public int getMyCollisionID() {
		return myCollisionID;
	}



	public PlayerInterface(MTColor color, int playerID,float angle,GestureChallengeScene gCS,int playerNumber, int level){
		
		//init
		myColor=color;
		myNumber=playerID;
		myGCS = gCS;
		myAngle=angle;
		this.playerNumber = playerNumber;
		myScore=0;
		
		

		
		
		//creation Goal
		float x = myGCS.getMTApplication().width/2f +(float) (Math.cos(angle)*Constants.radiusCenterGoals);
		float y = myGCS.getMTApplication().height/2f +(float) (Math.sin(angle)*Constants.radiusCenterGoals);
		
		//Creation mobileShield area
		if(level>2){
			myMSA = new PlayerMovableShieldArea(this, new Vector3D(x,y));
			myMSA.removeAllGestureEventListeners();
			this.myGCS.getPhysicsContainer().addChild(myMSA);
		}
		if(level>1){
			myRSA = new PlayerRotableShieldArea(this,new Vector3D(x,y));
			myGCS.getPhysicsContainer().addChild(myRSA);
		}
		
		myPG=new PlayerGoal(myGCS.getMTApplication(), new Vector3D(x,y), myGCS.getWorld(), myGCS.getScale(), myColor,this.myNumber+1,angle,myGCS);
		myGCS.getPhysicsContainer().addChild(myPG);

		//creation shields
		
		x = x -(float) (Math.cos(angle)*Constants.shieldDistance);
		y = y -(float) (Math.sin(angle)*Constants.shieldDistance);
		float coveredAngle = (float) Math.toRadians(180/(float)playerNumber);
		//myPS=new PhysicsShield(Constants.shieldBigRadius,Constants.shieldSmallRadius,Constants.shieldSmallDef,Constants.shieldBigDef,coveredAngle,new Vector3D(x,y),myGCS.getMTApplication(),myGCS.getWorld(),0f,0f,0f,myGCS.getScale(),color);
		
		if(level>1){
			myPS = new PlayerRotableShield(new Vector3D(x,y),this);
			myGCS.getPhysicsContainer().addChild(myPS);		
			myPS.getBody().setXForm(
					myPS.getBody().getPosition(), (float) (angle+Math.PI/2f)
			);
		}
		
		
		
		//TEST WITH ROTABLE SHIELD AREA
		myPG.removeAllGestureEventListeners();
		myPG.setPickable(false);
		
		if(level>1){
			myRSA.registerInputProcessor(new ThreeFingersAverageGestureProcessor(myGCS.getMTApplication()));
			myRSA.addGestureListener(ThreeFingersAverageGestureProcessor.class, new IGestureEventListener() {
				public boolean processGestureEvent(MTGestureEvent ge) {
					ThreeFingersAverageGestureEvent evt = (ThreeFingersAverageGestureEvent) ge;
					float angle = evt.getRotationAngleRadian();
					float beta = myPS.getBody().getAngle();
					Vector3D center = myPG.getCenterPointGlobal();
					Vec2 newPos = new Vec2(PhysicsHelper.scaleDown((float)(center.x-Constants.shieldDistance*Math.sin(angle+beta)), myGCS.getScale()),PhysicsHelper.scaleDown((float)(center.y+Constants.shieldDistance*Math.cos(angle+beta)), myGCS.getScale()));
					myPS.getBody().setXForm(newPos, angle+myPS.getBody().getAngle());
					return false;
				}
			});
		}
		//WITHOUT ROTABLE SHIELD AREA
		/*myPG.registerInputProcessor(new ThreeFingersGestureProcessor(myGCS.getMTApplication()));
		myPG.addGestureListener(ThreeFingersGestureProcessor.class, new IGestureEventListener() {
			public boolean processGestureEvent(MTGestureEvent ge) {
				ThreeFingersGestureEvent evt = (ThreeFingersGestureEvent) ge;
				float angle = evt.getRotationAngleRadian();
				float beta = myPS.getBody().getAngle();
				Vector3D center = myPG.getCenterPointGlobal();
				Vec2 newPos = new Vec2(PhysicsHelper.scaleDown((float)(center.x-Constants.shieldDistance*Math.sin(angle+beta)), myGCS.getScale()),PhysicsHelper.scaleDown((float)(center.y+Constants.shieldDistance*Math.cos(angle+beta)), myGCS.getScale()));
				myPS.getBody().setXForm(newPos, angle+myPS.getBody().getAngle());
				return false;
			}
		});*/
		
		
		//PhysicsHelper.addDragJoint(this.getMyGCS().getWorld(), myPS, myPS.getBody().isDynamic(), this.getMyGCS().getScale());


		
		//Avoid collision between playerInterface and its own bullets
		

		myCollisionID = (int) Math.pow(2, this.myNumber+1);

		
		myBulletMask =0;
		for(int i =0;i<=playerNumber;i++){
			myBulletMask+=(int) Math.pow(2, i+1);
		}
		
		myBulletMask+=Math.pow(2, 10); //wall
		
		
		myBulletMask-=myCollisionID;
		myBulletCat=(int) Math.pow(2,this.playerNumber+this.myNumber+1+1);
		
		for(int i =0;i<=playerNumber;i++){
			if(i!=myNumber){
				myCollisionID+=((int) Math.pow(2, playerNumber+i+1+1));
			}
		}
		
				
		//System.out.println("P"+myNumber+" mask:"+myBulletMask);
		
		if(level>1){
			myPS.getBody().getShapeList().m_filter.categoryBits=myCollisionID;
			myPS.getBody().getShapeList().m_filter.maskBits=myCollisionID;
			myPS.getBody().getShapeList().m_filter.groupIndex=0;
			//System.out.println("PS"+myNumber+" cat("+myPS.getBody().getShapeList().m_filter.categoryBits+")/mask("+myPS.getBody().getShapeList().m_filter.maskBits+")/group("+myPS.getBody().getShapeList().m_filter.groupIndex+")");
			//System.out.println("PS"+myNumber+" cat("+myPS.getBody().m_shapeList.m_filter.categoryBits+")/mask("+myPS.getBody().m_shapeList.m_filter.maskBits+")/group("+myPS.getBody().m_shapeList.m_filter.groupIndex+")");
			Shape shape;
			shape=myPS.getBody().getShapeList();
			for (Shape s = myPS.getBody().getShapeList();
				     s != null;
				     s = s.getNext()){
				s.m_filter.categoryBits=myCollisionID;
				s.m_filter.maskBits=myCollisionID;
				s.m_filter.groupIndex=0;
				//System.out.println(i);
			}
			
			System.out.println("P"+myNumber+" rotableShield("+myPS.getBody().getShapeList().m_filter.categoryBits+")("+myPS.getBody().getShapeList().m_filter.maskBits+")");
			
		}
		
		
		myPG.getBody().getShapeList().m_filter.categoryBits=myCollisionID;
		myPG.getBody().getShapeList().m_filter.maskBits=myCollisionID;
		myPG.getBody().getShapeList().m_filter.groupIndex=0;
		//System.out.println("PG"+myNumber+" cat("+myPG.getBody().getShapeList().m_filter.categoryBits+")/mask("+myPG.getBody().getShapeList().m_filter.maskBits+")");
	

		
		//Creation bullets
		myBL = new PlayerBulletLoader(this);
		myPD = new PlayerDisplay(this);
		
		// Add pan listener in order to create a temporary shield between the two cursors
		if(level>2){
			final PlayerInterface PI = this;
			myMSA.registerInputProcessor(new PanProcessorTwoFingers(myGCS.getMTApplication()));
			myMSA.addGestureListener(PanProcessorTwoFingers.class, new IGestureEventListener() {
				public boolean processGestureEvent(MTGestureEvent ge) {
					PanTwoFingerEvent evt = (PanTwoFingerEvent)ge;
					switch(evt.getId()){
					case MTGestureEvent.GESTURE_STARTED:
						myPanShield = new PlayerPanShield(PI, evt.getFirstCursor(), evt.getSecondCursor(), myCollisionID);
						myGCS.getPhysicsContainer().addChild(myPanShield);


						//System.out.println("P"+myNumber+" movableShield("+myPanShield.getBody().getShapeList().m_filter.categoryBits+")("+myPanShield.getBody().getShapeList().m_filter.maskBits+")");

						break;
					case MTGestureEvent.GESTURE_ENDED:

						myGCS.getWorld().destroyBody(myPanShield.getBody());
						myGCS.getPhysicsContainer().removeChild(myPanShield);
						myPanShield.destroy();
						break;
					case MTGestureEvent.GESTURE_UPDATED:

						if(myMSA.containsPointGlobal(evt.getFirstCursor().getPosition()) && myMSA.containsPointGlobal(evt.getSecondCursor().getPosition())){
							myGCS.getWorld().destroyBody(myPanShield.getBody());
							myGCS.getPhysicsContainer().removeChild(myPanShield);
							myPanShield.destroy();
							myPanShield = new PlayerPanShield(PI, evt.getFirstCursor(), evt.getSecondCursor(), myCollisionID);
							myGCS.getPhysicsContainer().addChild(myPanShield);
							//Avoid collision with own bullets
						}	

						//myPanShield.move(mySA, evt.getFirstCursor(), evt.getSecondCursor());
						break;
					}
					return false;
				}
			});
		}
		

	
	}
	
	public void score(int score){
		myScore+=score;
		myPD.score.setText(myScore+" pts");
		myPD.addNotification("+"+score+" pts !",MTColor.YELLOW);

	}



	public int getMyScore() {
		return myScore;
	}



	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().compareTo("ranking"+myNumber)==0){
			switch(Integer.parseInt((String) evt.getNewValue())){
				case 1:
					this.myPD.rank.setText("1st");
					this.myPD.rank.setFontColor(MTColor.GREEN);
					break;
				case 2:
					this.myPD.rank.setText("2st");
					this.myPD.rank.setFontColor(MTColor.BLACK);
					break;
				case 3:
					this.myPD.rank.setText("3rd");
					this.myPD.rank.setFontColor(MTColor.BLACK);
					break;
				default:
					this.myPD.rank.setText(((String) evt.getNewValue())+"th");
					this.myPD.rank.setFontColor(MTColor.BLACK);
					break;
			}
		}else if(evt.getPropertyName().compareTo("time")==0){
			final String time = (String) evt.getNewValue();
			
			this.getMyGCS().getMTApplication().invokeLater(new Runnable(){

				@Override
				public void run() {
					myPD.time.setText(time);
					
				}
				
			});
			
			if(time.compareTo("0'10''")==0){
				myPD.time.setFontColor(MTColor.RED);
			}
		}
		
	}



	public void cancelTimers() {
		myPD.cancelTimers();
		
	}
	
	
}
