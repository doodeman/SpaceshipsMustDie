package server;

import com.badlogic.gdx.math.Vector3;

import shared.CollidableObject;

public class ServerCollidableObject extends CollidableObject
{
	ServerSun sun; 
	protected ServerCollidableObject(int id, int type, Vector3 location, Vector3 direction, Vector3 velocity, int radius, ServerSun sun) 
	{
		super(id, type, location, direction, velocity, radius);
		this.sun = sun; 
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void update()
	{
		this.location.add(velocity);
		orbit(); 
	}
	
	public void orbit()
	{
		if (sun != null)
		{
			Vector3 gravity = this.vectorTo(sun, 1); 
			this.location.add(gravity); 
		}
	}
}
