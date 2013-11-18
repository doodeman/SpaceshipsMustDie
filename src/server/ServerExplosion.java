package server;

import shared.CollidableObject;
import shared.Vector3D;

public class ServerExplosion extends CollidableObject
{
	int lifetime = 0; 
	protected ServerExplosion(int id, Vector3D location,
			Vector3D direction, Vector3D velocity, Vector3D up, int radius) {
		super(id, 5, location, direction, velocity, up, radius);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void update()
	{
		lifetime++; 
	}
}
