package physicsShapes;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.dynamics.World;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.ToolsMath;
import org.mt4j.util.math.Vector3D;
import org.mt4j.util.math.Vertex;

import processing.core.PApplet;

public class PhysicsShield2 extends PhysicsPolygon {

	public PhysicsShield2(int bigRadius,int smallRadius, int smallDef,int bigDef, float coveredAngle, Vector3D position, PApplet applet,
			World world, float density, float friction, float restitution, float worldScale
	){
		super(new Vertex[]{}, position, applet, world, density, friction, restitution,
				worldScale);
		
Vertex[] vertices = new Vertex[smallDef+bigDef];
		
		int i;
		int j;
		for(i=0; i<smallDef;i++){
			vertices[i]=new Vertex((float)(Math.cos((Math.PI-coveredAngle)/2f+(i/(float)smallDef)*coveredAngle)*smallRadius),(float)(Math.sin((Math.PI-coveredAngle)/2f+(i/(float)smallDef)*coveredAngle)*smallRadius));
			// c = new PhysicsCircle(app, shieldVertices[i], 2, world, 0, 0, 0, scale);
			//physicsContainer.addChild(c);

		}
		for(j=0; j<bigDef;j++){
			vertices[i+j]=new Vertex((float)(Math.cos((Math.PI-coveredAngle)/2f+((bigDef-(j+1))/(float)bigDef)*coveredAngle)*bigRadius),(float)(Math.sin((Math.PI-coveredAngle)/2f+((bigDef-(j+1))/(float)bigDef)*coveredAngle)*bigRadius));
			//c = new PhysicsCircle(app, shieldVertices[i+j], 2, world, 0, 0, 0, scale);
			//physicsContainer.addChild(c);
		}
		
		//shieldVertices[smallDef+bigDef]=shieldVertices[0];
		
		for(int k=0;k<bigDef+smallDef;k++){
			System.out.println("V["+k+"]=("+vertices[k].x+" "+vertices[k].y+")");
		}
		
		super.setVertices(vertices);
		
		MTColor polyCol = new MTColor(ToolsMath.getRandom(60, 255),ToolsMath.getRandom(60, 255),ToolsMath.getRandom(60, 255));
		this.setFillColor(polyCol);
		this.setStrokeColor(polyCol);
		//PhysicsHelper.addDragJoint(world, shieldPoly, shieldPoly.getBody().isDynamic(), scale);
		//For an anti-aliased outline
		List<Vertex[]> contours = new ArrayList<Vertex[]>();
		contours.add(vertices);
		this.setOutlineContours(contours);
		this.setNoStroke(false);	
	}
	
	
	public PhysicsShield2(Vertex[] vertices, Vector3D position, PApplet applet,
			World world, float density, float friction, float restitution,
			float worldScale) {
		super(new Vertex[]{}, position, applet, world, density, friction, restitution,
				worldScale);
		// TODO Auto-generated constructor stub
	}

}
