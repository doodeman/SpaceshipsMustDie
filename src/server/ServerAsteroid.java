package server;

import shared.Vector3D;



public class ServerAsteroid extends ServerCollidableObject
{
	protected ServerAsteroid(int id, Vector3D location, Vector3D direction, Vector3D velocity, int radius, ServerSun sun) 
	{
		super(id, 3, location, direction, velocity, radius, sun);
	}
}
