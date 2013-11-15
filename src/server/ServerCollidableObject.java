package server;


import shared.CollidableObject;
import shared.Vector3D;

public abstract class ServerCollidableObject extends CollidableObject
{
	ServerSun sun; 
	protected ServerCollidableObject(int id, int type, Vector3D location, Vector3D direction, Vector3D velocity, int radius, ServerSun sun) 
	{
		super(id, type, location, direction, velocity, radius);
		this.sun = sun; 
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void update()
	{
		this.location = Vector3D.sum(this.location, velocity);
		orbit(); 
	}
	
	public abstract void orbit();
	
	public void applyForce(Vector3D direction, float magnitude)
	{
		//direction = Vector3D.unitVector(direction); 
		this.velocity = Vector3D.sum(this.velocity, Vector3D.mult(magnitude, direction));
	}
	
	@Override
	public boolean hasCollided(CollidableObject that)
	{
		float distance = Vector3D.distanceBetween(this.location, that.location); 
		distance = distance - this.radius; 
		distance = distance - that.radius; 
		if (distance <= 0)
		{
			//System.out.println("collission!");
			this.hasCollided = true; 
			that.hasCollided = true; 
			collisionResponse(that); 
			return true; 
		}
		return false; 
	}
	
	private void collisionResponse(CollidableObject that)
	{
		//Sphere-sphere collision response courtesy of http://studiofreya.com/blog/3d-math-and-physics/simple-sphere-sphere-collision-detection-and-collision-response/
		//First, find the vector which will serve as a basis vector (x-axis), in an arbiary direction. It have to be normalized to get realistic results.
		Vector3D x = Vector3D.unitVector(Vector3D.difference2(this.location, that.location)); 
		//Then we calculate the x-direction velocity vector and the perpendicular y-vector.
		float x1 = Vector3D.dot(x, this.velocity);
		Vector3D v1x = Vector3D.mult(x1, x);
		Vector3D v1y = Vector3D.difference2(this.velocity, v1x);
		float m1 = this.radius;
		
		x = Vector3D.mult(-1, x); 
		Vector3D v2 = that.velocity; 
		float x2 = Vector3D.dot(x, v2);
		Vector3D v2x = Vector3D.mult(x2, x);
		Vector3D v2y = Vector3D.difference2(v2, v2x); 
		float m2 = that.radius; 
		
		//v1x*(m1-m2)/(m1+m2)
		Vector3D a = Vector3D.mult((m1-m2)/(m1+m2), v1x); 
		//v2x*(2*m2)/(m1+m2)
		Vector3D b = Vector3D.mult((2*m2)/(m1+m2), v2x);
		//( v1x*(m1-m2)/(m1+m2) + v2x*(2*m2)/(m1+m2) + v1y )
		this.velocity = Vector3D.sum(v1y, Vector3D.sum(a, b));
		
		//v1x*(2*m1)/(m1+m2)
		a = Vector3D.mult((2*m1)/(m1+m2), v1x); 
		//v2x*(m2-m1)/(m1+m2)
		b = Vector3D.mult((m2-m1)/(m1+m2),v2x); 
		//v1x*(2*m1)/(m1+m2) + v2x*(m2-m1)/(m1+m2) + v2y
		that.velocity = Vector3D.sum(v2y, Vector3D.sum(a, b));
		
	}
}
