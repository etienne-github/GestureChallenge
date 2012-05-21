package util.math;

import org.mt4j.util.math.Vector3D;

public class Geometry {
	public static float dotProduct(Vector3D refSeg, Vector3D newSeg){
		return (refSeg.x*newSeg.x)+(refSeg.y*newSeg.y);
	}

	public static float crossProduct(Vector3D refSeg, Vector3D newSeg){
		return (refSeg.x*newSeg.y)-(refSeg.y*newSeg.x);
	}
	
	public static float orientedRadianAngleBetween(Vector3D firstVector, Vector3D secondVector){
		return (float) Math.atan2(Geometry.crossProduct(firstVector,secondVector), 
								  Geometry.dotProduct(firstVector,secondVector));
	}
	
	public static float orientedDegreeAngleBetween(Vector3D firstVector, Vector3D secondVector){
		return (float) ((360*Math.atan2(Geometry.crossProduct(firstVector,secondVector), 
								  Geometry.dotProduct(firstVector,secondVector)))/(2*Math.PI));
	}
}
