package scene;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import model.Constants;

import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.mt4j.AbstractMTApplication;
import org.mt4j.components.MTComponent;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.input.inputProcessors.globalProcessors.CursorTracer;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.ToolsMath;
import org.mt4j.util.math.Vector3D;
import org.mt4j.util.math.Vertex;
import physic.shape.PhysicsCircle;
import physic.shape.PhysicsPolygon;
import physic.shape.PhysicsRectangle;
import physic.shape.PhysicsShield;
import physic.shape.util.PhysicsHelper;
import physic.shape.util.UpdatePhysicsAction;
import playerinterface.PlayerGoal;







public class GestureChallengeScene extends AbstractScene {
	private float timeStep = 1.0f / 60.0f;
	private int constraintIterations = 10;
	
	/** THE CANVAS SCALE **/
	private float scale = 20;
	private AbstractMTApplication app;
	private World world;
	PhysicsCircle c;
	private MTComponent physicsContainer;
	
	public GestureChallengeScene(AbstractMTApplication mtApplication, String name) {
		super(mtApplication, name);
		this.app = mtApplication;
		
		float worldOffset = 10; //Make Physics world slightly bigger than screen borders
		//Physics world dimensions
		AABB worldAABB = new AABB(new Vec2(-worldOffset, -worldOffset), new Vec2((app.width)/scale + worldOffset, (app.height)/scale + worldOffset));
		//Vec2 gravity = new Vec2(0, 10);
		Vec2 gravity = new Vec2(0, 0);
		boolean sleep = true;
		//Create the pyhsics world
		this.world = new World(worldAABB, gravity, sleep);
		
		this.registerGlobalInputProcessor(new CursorTracer(app, this));
		
		//Update the positions of the components according the the physics simulation each frame
		this.registerPreDrawAction(new UpdatePhysicsAction(world, timeStep, constraintIterations, scale));
		
		MTRectangle bck = new MTRectangle(app,app.loadImage("."+((String)File.separator)+"src"+((String)File.separator)+"scene"+((String)File.separator)+"data"+((String)File.separator)+"bkgrnd.jpg"));
		this.getCanvas().addChild(bck);
		bck.removeAllGestureEventListeners();
		bck.setPositionGlobal(new Vector3D(app.width/2f,app.height/2f));
		
		physicsContainer = new MTComponent(app);
		//Scale the physics container. Physics calculations work best when the dimensions are small (about 0.1 - 10 units)
		//So we make the display of the container bigger and add in turn make our physics object smaller
		physicsContainer.scale(scale, scale, 1, Vector3D.ZERO_VECTOR);
		this.getCanvas().addChild(physicsContainer);

		//physicsContainer.
		//Create borders around the screen
		//this.createScreenBorders(physicsContainer);
		
		
		
		
		
		//Create empty circle
		int bigRadius= 200;
		int smallRadius = 170;
		int smallDef = 25;
		int bigDef = 25;
		double coveredAngle = Math.toRadians(50); //50 degrees
		
		
		//SHIELD CREATION
		//PhysicsShield pS = new PhysicsShield(bigRadius, smallRadius, smallDef,bigDef,(float) coveredAngle, new Vector3D(app.width/2f,app.height/2f), app, world, 0f, 0f, 0f, scale);
		//physicsContainer.addChild(pS);

		//pS.registerInputProcessor(new TapProcessor(app));
		
		
		 //MARQUAGE CENTRE
		 /*c= new PhysicsCircle(app,pS.getCenterPointGlobal(), 20, world, 0, 0, 0, scale);
		 System.out.println("center : "+physicsContainer.globalToLocal(pS.getCenterPointGlobal()).x+" "+physicsContainer.globalToLocal(pS.getCenterPointGlobal()).y);
		 physicsContainer.globalToLocal(pS.getCenterPointGlobal());
		 physicsContainer.addChild(c);*/
		
		
		//TEST ROTATION
		/*pS.addGestureListener(TapProcessor.class, new IGestureEventListener(){

			
			
			
			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
				
				TapEvent tE = (TapEvent)ge;
				if(tE!=null){
					c.setPositionGlobal(((PhysicsShield)ge.getTarget()).getCenterPointGlobal());
					System.out.println("-> listener"+((PhysicsShield)ge.getTarget()).getCenterPointGlobal());
					
					//System.out.println("angle "+((PhysicsPolygon)ge.getTarget()).getAngle());
					//c.setPositionRelativeToOther(((PhysicsPolygon)ge.getTarget()), new Vertex(((PhysicsPolygon)ge.getTarget()).getCenterPointLocal().x-600,((PhysicsPolygon)ge.getTarget()).getCenterPointLocal().y-(500)));
					//c.setPositionGlobal(new Vertex(((PhysicsPolygon)ge.getTarget()).getCenterPointLocal().x-600,((PhysicsPolygon)ge.getTarget()).getCenterPointLocal().y-(500)));
					//System.out.println("hey");
					//System.out.println(new Vertex(((PhysicsPolygon)ge.getTarget()).getCenterPointLocal().x-600,((PhysicsPolygon)ge.getTarget()).getCenterPointLocal().y-(500)));
					//((PhysicsPolygon)ge.getTarget()).rotateZGlobal(((PhysicsPolygon)ge.getTarget()).getCenterPointGlobal(), 5);
					//((PhysicsPolygon)ge.getTarget()).rotateZ(((PhysicsPolygon)ge.getTarget()).getCenterPointGlobal(), 5, TransformSpace.GLOBAL);	
					//((PhysicsPolygon)ge.getTarget()).rotateZGlobal(new Vertex(((PhysicsPolygon)ge.getTarget()).getCenterPointGlobal().x-600,((PhysicsPolygon)ge.getTarget()).getCenterPointGlobal().y-(500)), 5);
					//((PhysicsPolygon)ge.getTarget()).setCenterRotation(5);
					if(tE.isTapped()){
						
						System.err.println("??");
						((PhysicsShield)ge.getTarget()).setCenterRotation(5);
					}
				}
				

				
				return false;
			}
			
		});*/
	//shieldPoly.rotateZ(new Vertex(shieldPoly.getCenterPointLocal().x,shieldPoly.getCenterPointLocal().y-(bigRadius-smallRadius)/2f), 90, TransformSpace.LOCAL);	
	createScreenBorders(physicsContainer);
	
	//PlayerGoal pG = new PlayerGoal(app,new Vertex(app.width/2f,app.height/2F),world,scale,MTColor.PURPLE);
	//physicsContainer.addChild(pG);
	
	//add bouncing circle
	/*PhysicsCircle c = new PhysicsCircle(app, new Vertex(app.width/2f,app.height/2F), 10, world, 1.0f, 0, 1, scale);
	MTColor col1 = new MTColor(ToolsMath.getRandom(60, 255),ToolsMath.getRandom(60, 255),ToolsMath.getRandom(60, 255));
	c.setFillColor(col1);
	c.setStrokeColor(col1);
	PhysicsHelper.addDragJoint(world, c, c.getBody().isDynamic(), scale);
	c.setDepthBufferDisabled(true);
	//c.getBody().getShapeList().m_filter.categoryBits=0x0002;
	c.getBody().getShapeList().m_filter.maskBits=15;
	c.getBody().getShapeList().m_filter.categoryBits=15;
	c.getBody().getShapeList().m_filter.groupIndex=0;*/
	
	//boolean collideWall = (c.getBody().getShapeList().m_filter.maskBits & 1) != 0 && (c.getBody().getShapeList().m_filter.categoryBits & 1) != 0;


	//boolean collideArc = (c.getBody().getShapeList().m_filter.maskBits & 2) != 0 && (c.getBody().getShapeList().m_filter.categoryBits & 2) != 0;
	
	//System.out.println("bullet collides with walls ? "+collideWall);
	//System.out.println("bullet collides with arcs ? "+collideArc);
	
	//c.getBody().getShapeList().m_filter.groupIndex=0x0004;
	//physicsContainer.addChild(c);
	//System.out.println("c : "+c.getBody().getShapeList().m_filter.categoryBits+" / "+ c.getBody().getShapeList().m_filter.groupIndex);

	
	
		
	}
	
	
	
	private void createScreenBorders(MTComponent parent){
		//Create empty circle
			int mask = 0;
			
			for(int ind=0;ind<16;ind++){
				mask+=Math.pow(2, ind);
			}
				int radius = Constants.areaRadius;
				int def = 96;
				float twoPi = (float) (Math.PI*2);
				/*
				var radius:Number = 100;
				var numSegments:Number = 24;
				var twoPi:Number = Math.PI * 2;*/
				
				Vertex[] emptyCircleVertices=new Vertex[def];
				PhysicsCircle test;
				for(int i=0;i<def;i++){
					System.out.println(Math.cos((i/(float)def)*twoPi)*radius+" "+Math.sin((i/(float)def)*twoPi)*radius);
					emptyCircleVertices[i]=new Vertex((float) (app.width/2f+Math.cos((i/(float)def)*twoPi)*radius) , (float)(app.height/2f+Math.sin((i/(float)def)*twoPi)*radius));
					test = new PhysicsCircle(app,emptyCircleVertices[i], 1, world, 0, 0, 0, scale);
					test.setFillColor(MTColor.WHITE);
					test.setStrokeColor(MTColor.RED);
					//physicsContainer.addChild(test);
					//System.out.println("added en "+emptyCircleVertices[i].x+" "+emptyCircleVertices[i].y);
				}
				Vertex AB;
				Vertex halfAB;
				PhysicsRectangle pR;
				for(int i=0;i<def-1; i++){
					AB = new Vertex();
					AB.x = emptyCircleVertices[i+1].x-emptyCircleVertices[i].x;
					AB.y = emptyCircleVertices[i+1].y-emptyCircleVertices[i].y;
					float l = (float) Math.sqrt(Math.pow(AB.x, 2)+Math.pow(AB.y, 2));
					halfAB = new Vertex(AB.x/2f,AB.y/2f);
					pR = new PhysicsRectangle(new Vertex(emptyCircleVertices[i].x+halfAB.x,emptyCircleVertices[i].y+halfAB.y), l, 8, app, world, 0, 0, 0, scale);

					
					
					if((i>def/4)&&(i<3*(def/4))){
						
						pR.getBody().setXForm(
								pR.getBody().getPosition(), (float) (AB.angleBetween(Vector3D.X_AXIS)*-1)
								/*((float) Math.toDegrees(angle))*/
						);
						
						//pR.rotateZ(pR.getCenterPointLocal(), (float) Math.toDegrees(AB.angleBetween(Vector3D.X_AXIS))*-1, TransformSpace.LOCAL);
					}else{
						pR.getBody().setXForm(
								pR.getBody().getPosition(), (float) (AB.angleBetween(Vector3D.X_AXIS))
								/*((float) Math.toDegrees(angle))*/
						);
						//pR.rotateZ(pR.getCenterPointLocal(), (float) Math.toDegrees(AB.angleBetween(Vector3D.X_AXIS)), TransformSpace.LOCAL);
					}
					pR.getBody().getShapeList().getFilterData().maskBits=mask;
					pR.getBody().getShapeList().getFilterData().categoryBits=1;
					pR.getBody().getShapeList().getFilterData().groupIndex=0;
					pR.setDepthBufferDisabled(true);
					//pR.setNoFill(true);
					pR.setNoStroke(true);
					parent.addChild(pR);
				}
				//Add last segment
				AB = new Vertex();
				AB.x = emptyCircleVertices[0].x-emptyCircleVertices[def-1].x;
				AB.y = emptyCircleVertices[0].y-emptyCircleVertices[def-1].y;
				float l = (float) Math.sqrt(Math.pow(AB.x, 2)+Math.pow(AB.y, 2));
				halfAB = new Vertex(AB.x/2f,AB.y/2f);
				pR = new PhysicsRectangle(new Vertex(emptyCircleVertices[def-1].x+halfAB.x,emptyCircleVertices[def-1].y+halfAB.y), l, 8, app, world, 0, 0, 0, scale);
				pR.getBody().setXForm(
						pR.getBody().getPosition(), (float) (AB.angleBetween(Vector3D.X_AXIS))
						/*((float) Math.toDegrees(angle))*/
				);
				pR.getBody().getShapeList().getFilterData().maskBits=mask;
				pR.getBody().getShapeList().getFilterData().categoryBits=1;
				pR.getBody().getShapeList().getFilterData().groupIndex=0;
				//pR.rotateZ(pR.getCenterPointLocal(), (float) Math.toDegrees(AB.angleBetween(Vector3D.X_AXIS)), TransformSpace.LOCAL);
				pR.setDepthBufferDisabled(true);
				//pR.setNoFill(true);
				pR.setNoStroke(true);
				parent.addChild(pR);	
			
	}

	public void onEnter() {
	}
	
	public void onLeave() {	
	}



	public World getWorld() {
		return world;
	}



	public void setWorld(World world) {
		this.world = world;
	}



	public MTComponent getPhysicsContainer() {
		return physicsContainer;
	}



	public void setPhysicsContainer(MTComponent physicsContainer) {
		this.physicsContainer = physicsContainer;
	}



	public float getScale() {
		return scale;
	}



	public void setScale(float scale) {
		this.scale = scale;
	}

}
