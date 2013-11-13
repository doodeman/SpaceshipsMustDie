package server;

import com.badlogic.gdx.math.Vector3;

import shared.CollidableObject;

public class ServerPlayer extends CollidableObject
{
	protected ServerPlayer(int id, Vector3 location, Vector3 direction, Vector3 velocity, int radius) 
	{
		super(id, 2, location, direction, velocity, radius);
		// TODO Auto-generated constructor stub
	}
}
