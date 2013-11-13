package server;

import com.badlogic.gdx.math.Vector3;


public class ServerAsteroid extends ServerCollidableObject
{
	protected ServerAsteroid(Vector3 location, Vector3 direction, Vector3 velocity, int radius, ServerSun sun) 
	{
		super(location, direction, velocity, radius, sun);
	}
}
