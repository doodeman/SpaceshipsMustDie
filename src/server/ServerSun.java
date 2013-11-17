package server;


import shared.Vector3D;

public class ServerSun extends ServerCollidableObject
{
	protected ServerSun(int id, Vector3D location, Vector3D direction, Vector3D velocity, Vector3D up, int radius) 
	{
		super(id, 1, location, direction, velocity, up, radius, null);
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
