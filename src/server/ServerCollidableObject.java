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
}
