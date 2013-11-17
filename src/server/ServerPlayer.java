package server;


import client.ClientUpdate;

import com.badlogic.gdx.graphics.g3d.ModelInstance;

import shared.CollidableObject;
import shared.Vector3D;

public class ServerPlayer extends CollidableObject
{
	int playerId; 
	protected ServerPlayer(int playerId, int id, Vector3D location, Vector3D direction, Vector3D velocity, int radius) 
	{
		super(id, 2, location, direction, velocity, radius);
		// TODO Auto-generated constructor stub
	}
	
	public void update(ClientUpdate update)
	{
		//Dear Matti: pls to be implement the functionality 
		System.out.println(update);
	}
}
