package shared;


public class Point3D {
	public float x;
	public float y;
	public float z;
	
	public Point3D(float xx, float yy, float zz) {
		x = xx;
		y = yy;
		z = zz;
	}
	
	public Point3D(double xx, double yy, double zz)
	{
		x = (float) xx; 
		y = (float) yy; 
		z = (float) zz; 
	}
	public void set(float xx, float yy, float zz) {
		x = xx;
		y = yy;
		z = zz;
	}
	
	public Point3D(double xx, double zz)
	{
		x = (float) xx; 
		z = (float) zz; 
		y = 1; 
	}
	
	public void add(Vector3D v) {
		x += v.x;
		y += v.y;
		z += v.z;
	}
	
	public float distance(Point3D that)
	{
		float xdist, ydist; 
		xdist = this.x - that.x; 
		ydist = this.x - that.x; 
		return (float) Math.sqrt(xdist*xdist + ydist*ydist); 
	}
	
	/**
	 * Distance of point X from the line formed by the points a and b. 
	 * Borrowed from http://www.ahristov.com/tutorial/geometry-games/point-line-distance.html and then adapted. 
	 * @param a
	 * @param b
	 * @param x
	 * @return
	 */
	 public static double pointToLineDistance(Point3D A, Point3D B, Point3D P) 
	 {
		 double lineCenterX = (A.x + B.x)/2;
		 double lineCenterY = (A.y + B.y)/2; 
		 return ((P.x - lineCenterX)*(P.x - lineCenterX) + (P.y - lineCenterY)*(P.y - lineCenterY));
		 //double normalLength = Math.sqrt((B.x-A.x)*(B.x-A.x)+(B.z-A.z)*(B.z-A.z));
		 //return Math.abs((P.x-A.x)*(B.z-A.z)-(P.z-A.z)*(B.x-A.x))/normalLength;
	 }
	 
	 
	 
	 
	 /*
	  * Burrowed from http://ideone.com/PnPJgb and adapted. 
	  */
	 // Determines if the lines AB and CD intersect.
	 public static boolean LinesIntersect(Point3D A, Point3D B, Point3D C, Point3D D)
	 {
		 Point3D CmP = new Point3D(C.x - A.x, C.z - A.z);
		 Point3D r = new Point3D(B.x - A.x, B.z - A.z);
		 Point3D s = new Point3D(D.x - C.x, D.z - C.z);
		  
		 float CmPxr = CmP.x * r.z - CmP.z * r.x;
		 float CmPxs = CmP.x * s.z - CmP.z * s.x;
		 float rxs = r.x * s.z - r.z * s.x;
		  
		 if (CmPxr == 0f)
		 {
		 // Lines are collinear, and so intersect if they have any overlap
		  
		 return ((C.x - A.x < 0f) != (C.x - B.x < 0f))
		 || ((C.z - A.z < 0f) != (C.z - B.z < 0f));
		 }
		  
		 if (rxs == 0f)
		 return false; // Lines are parallel.
		  
		 float rxsr = 1f / rxs;
		 float t = CmPxs * rxsr;
		 float u = CmPxr * rxsr;
		  
		 return (t >= 0f) && (t <= 1f) && (u >= 0f) && (u <= 1f);
	 }
}
