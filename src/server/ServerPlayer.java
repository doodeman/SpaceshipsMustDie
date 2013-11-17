package server;


import client.ClientUpdate;

import com.badlogic.gdx.graphics.g3d.ModelInstance;

import shared.CollidableObject;
import shared.Vector3D;

public class ServerPlayer extends ServerCollidableObject
{
	private float forwardThrust, backwardThrust, spinThrust; 
	int playerId; 
	protected ServerPlayer(int playerId, int id, Vector3D location, Vector3D direction, Vector3D velocity, Vector3D up, int radius, ServerSun sun) 
	{
		super(id, 2, location, direction, velocity, up, radius, sun);
		forwardThrust = (float) 0.5; 
		backwardThrust = (float) -0.25; 
		spinThrust = (float) 0.25;
		// TODO Auto-generated constructor stub
	}
	
	public void update(ClientUpdate update)
	{
		if (update.forward)
		{
			applyForce(direction, forwardThrust);
		}
		if (update.backward)
		{
			applyForce(direction, backwardThrust);
		}
		if (update.left)
		{
			//TODO: make the player spin left
		}
		if (update.right)
		{
			//TODO: make the player spin right
		}
		if (update.down)
		{
			//TODO: make the player spin down
		}
		if (update.up)
		{
			//TODO: make the player spin up
		}
		if (update.fire)
		{
			//TODO: make the player fire a projectile
		}
	}

	@Override
	public void orbit() {
		// TODO Auto-generated method stub
		
	}
}
