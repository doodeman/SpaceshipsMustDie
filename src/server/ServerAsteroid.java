package server;

import com.badlogic.gdx.math.Vector3;


public class ServerAsteroid extends ServerCollidableObject
{
	protected ServerAsteroid(int id, Vector3 location, Vector3 direction, Vector3 velocity, int radius, ServerSun sun) 
	{
		super(id, 3, location, direction, velocity, radius, sun);
	}
}
