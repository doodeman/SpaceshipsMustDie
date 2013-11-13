package server;

import com.badlogic.gdx.math.Vector3;

import shared.CollidableObject;

public class ServerAsteroid extends CollidableObject
{

	protected ServerAsteroid(Vector3 location, Vector3 direction, Vector3 velocity, int radius) 
	{
		super(location, direction, velocity, radius);
	}
	
}
