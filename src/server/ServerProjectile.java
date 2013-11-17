package server;

import shared.CollidableObject;
import shared.Vector3D;

public class ServerProjectile extends ServerCollidableObject
{

	protected ServerProjectile(int id, int type, Vector3D location,
			Vector3D direction, Vector3D velocity, Vector3D up, int radius,
			ServerSun sun) 
	{
		super(id, type, location, direction, velocity, up, radius, sun);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void orbit() {
		// TODO Auto-generated method stub
		
	}
}
