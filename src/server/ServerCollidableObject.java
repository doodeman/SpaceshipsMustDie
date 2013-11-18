package server;


import shared.CollidableObject;
import shared.Vector3D;

public abstract class ServerCollidableObject extends CollidableObject
{
	ServerSun sun; 
	protected ServerCollidableObject(int id, int type, Vector3D location, Vector3D direction, Vector3D velocity, Vector3D up, int radius, ServerSun sun) 
	{
		super(id, type, location, direction, velocity, up, radius);
		this.sun = sun; 
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void update()
	{
		orbit(); 
		this.location = Vector3D.sum(this.location, velocity);
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
		//Explosions don't collide
		if (this.type == 5 || that.type == 5)
		{
			return false; 
		}
		if (!this.hasCollided && !that.hasCollided)
		{
			float distance = Vector3D.distanceBetween(this.location, that.location); 
			distance = distance - this.radius; 
			distance = distance - that.radius; 

			if (distance <= 0)
			{
				if (this.type == 3 && that.type == 3)
				{
					//If two asteroids are colliding, bounce
					this.hasCollided = true; 
					that.hasCollided = true; 
					collisionResponse(that); 
					return true; 
				}
				else
				{
					this.destroy();
					that.destroy();
					return true; 
				}
				
			}
			return false; 
		}
		return true; 
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
		if (this.type != 1)
		{
			this.velocity = Vector3D.sum(v1y, Vector3D.sum(a, b));
			this.location = Vector3D.sum(this.velocity, this.location);
		}
		
		//v1x*(2*m1)/(m1+m2)
		a = Vector3D.mult((2*m1)/(m1+m2), v1x); 
		//v2x*(m2-m1)/(m1+m2)
		b = Vector3D.mult((m2-m1)/(m1+m2),v2x); 
		//v1x*(2*m1)/(m1+m2) + v2x*(m2-m1)/(m1+m2) + v2y
		if (that.type != 1)
		{
			that.velocity = Vector3D.sum(v2y, Vector3D.sum(a, b));
			that.location = Vector3D.sum(that.velocity, that.location);
		}
	}
	
	
}
