package server;

import com.badlogic.gdx.math.Vector3;

import shared.CollidableObject;

public class ServerSun extends ServerCollidableObject
{

	protected ServerSun(int id, Vector3 location, Vector3 direction, Vector3 velocity, int radius) 
	{
		super(id, 1, location, direction, velocity, radius, null);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void orbit()
	{
		
	}
	
	@Override
	public void update()
	{
		
	}
}
