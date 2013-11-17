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
		forwardThrust = 1f; 
		spinThrust = 1f;
		// TODO Auto-generated constructor stub
	}
	
	public void update(ClientUpdate update)
	{
		//System.out.println(this.location.x + " " + this.location.y + " " + this.location.z);
		//System.out.println(this.direction.x + " " + this.direction.y + " " + this.direction.z);
		if (update.forward)
		{
			//System.out.println("thrusting");
			applyForce(this.direction, forwardThrust);
		}
		if (update.backward)
		{
			applyForce(this.direction, -forwardThrust);
		}
		if (update.left)
		{
			yaw(-spinThrust); 
		}
		if (update.right)
		{
			yaw(spinThrust); 
		}
		if (update.down)
		{
			pitch(-spinThrust); 
		}
		if (update.up)
		{
			pitch(spinThrust); 
		}
		if (update.fire)
		{
			//TODO: make the player fire a projectile
		}
		
		if (this.velocity.length() > 0.5)
		{
			this.velocity = Vector3D.setLength(this.velocity, 0.5f);
		}
		//System.out.println(this.velocity.length());
	}

	
	@Override
	public void orbit() {
		// TODO Auto-generated method stub
		
	}
}
