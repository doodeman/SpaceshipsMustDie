package server;

import shared.CollidableObject;
import shared.Vector3D;

public class ServerProjectile extends ServerCollidableObject
{
	public int owner; 
	protected ServerProjectile(int id, int type, Vector3D location,
			Vector3D direction, Vector3D velocity, Vector3D up, int radius,
			ServerSun sun, int owner) 
	{
		super(id, type, location, direction, velocity, up, radius, sun);
		this.owner = owner; 
	}

	@Override
	public void orbit() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void update()
	{
		this.location = Vector3D.sum(this.location, velocity);
	}
}
