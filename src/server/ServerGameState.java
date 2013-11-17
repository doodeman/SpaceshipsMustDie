package server;

import java.io.IOException;

import client.ClientUpdate;
import network.Client;
import shared.CollidableObject;
import shared.GameState;
import shared.Logger;
import shared.Vector3D;

public class ServerGameState extends GameState
{
	ServerSun sun; 
	int playerCount = -1; 
	public ServerGameState() throws IOException
	{
		super();
		sequenceNumber = 0; 
		Vector3D location = new Vector3D(0,0,0);
		Vector3D direction = new Vector3D(0,0,0); 
		Vector3D velocity = new Vector3D(0,0,0); 
		Vector3D up = new Vector3D(0,0,0);
		sun = new ServerSun(0, location, direction, velocity, up, 20, this);
		objects.add(sun); 
	}
	
	public void addPlayer(Client client)
	{
		Vector3D location = new Vector3D(-100,0,100);
		Vector3D direction = new Vector3D((float)0.01,0,(float)0.01); 
		//Vector3D velocity = new Vector3D((float)Math.random(),(float)Math.random(),(float)Math.random()); 
		Vector3D velocity = new Vector3D(0,0,0); 
		Vector3D up = new Vector3D(0,0,0);
		CollidableObject player = new ServerPlayer(playerCount, playerCount, location, direction, velocity, up, 1, this.sun, this);
		playerCount--; 
		//player.direction = (player.vectorTo(sun, 1));
		objects.add(player);
	}

	public void addAsteroid(float x, float y, float z) {
		System.out.println("Adding asteroid");
		Vector3D location = new Vector3D(x, y, z);
		Vector3D direction = new Vector3D((float)Math.random(),(float)Math.random(),(float)Math.random()); 
		//Vector3D velocity = new Vector3D((float)Math.random(),(float)Math.random(),(float)Math.random()); 
		Vector3D velocity = new Vector3D((float)0.5,(float)0,0); 
		Vector3D up = new Vector3D(0,0,0);

		int id = objects.size(); 
		this.objects.add(new ServerAsteroid(id, location, direction, velocity, up, 10, sun, this));
	}
	
	public void addProjectile(ServerPlayer player)
	{
		Vector3D pLocation = Vector3D.numSum(10f, player.location); 
		Vector3D pVelocity = Vector3D.numSum(0.5f, player.velocity); 
		ServerProjectile projectile = new ServerProjectile(objects.size(), 4, pLocation, player.direction, pVelocity, player.up, 5, this.sun, this);
		objects.add(projectile);
	}
	
	@Override
	public void update()
	{
		sequenceNumber++; 
		for (CollidableObject o : objects)
		{
			//Logger.log("Server.log", "GAMESTATe: Updating: " + o);
			//Logger.log("Server.log", "GAMESTATE: Old values: " + o.location.x + " " + o.location.y + " " + o.location.z);
			o.update(); 			
			//Logger.log("Server.log", "GAMESTATE: New values: " + o.location.x + " " + o.location.y + " " + o.location.z);
		}
		checkForCollisionsAndThenFixThem();
	}
	
	//The greatest function name in the world 
	private void checkForCollisionsAndThenFixThem()
	{
		for (CollidableObject o : objects)
		{
			o.hasCollided = false; 
		}
		for (CollidableObject o : objects)
		{
			for (CollidableObject o1 : objects)
			{
				if (o != o1)
				{
					o.hasCollided(o1);
				}
			}
		}
	}
	
	public CollidableObject getObject(int objectId)
	{
		for (CollidableObject o : objects)
		{
			if (o.id == objectId)
			{
				return o; 
			}
		}
		return null; 
	}
	
	public void updatePlayer(ClientUpdate update)
	{
		ServerPlayer player = (ServerPlayer) getObject(update.clientId);
		player.update(update); 
	}
}
