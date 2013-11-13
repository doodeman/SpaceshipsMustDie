package server;


import shared.CollidableObject;
import shared.Vector3D;

public class ServerCollidableObject extends CollidableObject
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
	
	public void orbit()
	{
		Vector3D gravity = this.vectorTo(sun, 1); 
		this.location = Vector3D.sum(this.location, gravity);
	}
}
