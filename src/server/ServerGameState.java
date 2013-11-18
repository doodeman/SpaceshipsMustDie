package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	int idcounter = 0; 
	List<CollidableObject> addQueue; 
	public ServerGameState() throws IOException
	{
		super();
		sequenceNumber = 0; 
		Vector3D location = new Vector3D(0,0,0);
		Vector3D direction = new Vector3D(0,0,0); 
		Vector3D velocity = new Vector3D(0,0,0); 
		Vector3D up = new Vector3D(0,0,0);
		sun = new ServerSun(idcounter, location, direction, velocity, up, 20);
		idcounter++;
		objects.add(sun); 
		addQueue = new ArrayList<CollidableObject>();
	}
	
	public void addPlayer(Client client)
	{
		Vector3D location = new Vector3D(-100,0,100);
		Vector3D direction = new Vector3D(1,0,0); 
		//Vector3D velocity = new Vector3D((float)Math.random(),(float)Math.random(),(float)Math.random()); 
		Vector3D velocity = new Vector3D(0,0,0); 
		Vector3D up = new Vector3D(0,1,0);
		CollidableObject player = new ServerPlayer(playerCount, playerCount, location, direction, velocity, up, 1, this.sun);
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

		this.objects.add(new ServerAsteroid(idcounter, location, direction, velocity, up, 12, sun));
		idcounter++;
	}
	
	public void addProjectile(ServerPlayer player)
	{
		Vector3D pLocation = Vector3D.sum(player.location, Vector3D.setLength(player.direction, 6));
		//pLocation = Vector3D.mult(-1f, pLocation);
		Vector3D pVelocity = Vector3D.unitVector(player.direction);
		//Vector3D pVelocity = new Vector3D(0f,0f,0f);
	
		ServerProjectile projectile = new ServerProjectile(idcounter, 4, pLocation, player.direction, pVelocity, player.up, 4, this.sun);
		idcounter++;
		objects.add(projectile);
		System.out.println("added " + projectile);
		projectile.location.print(); 
		player.location.print();
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
		removeDestroyed();
		addSplits(); 
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
	
	public void updatePlayers(List<ClientUpdate> updates)
	{
		for (ClientUpdate update : updates)
		{
			updatePlayer(update); 
		}
	}
	
	public void updatePlayer(ClientUpdate update)
	{
		ServerPlayer player = (ServerPlayer) getObject(update.clientId);
		player.update(update); 
		if (player.firing)
		{
			addProjectile(player);
			player.firing = false; 
		}
	}
	
	private void removeDestroyed()
	{
		List<CollidableObject> toRemove = new ArrayList<CollidableObject>(); 
		for (CollidableObject o : objects)
		{
			if (o.destroyed == true)
			{
				//if o is a asteroid
				if (o.type == 3)
				{
					if (splitAsteroid(o));
					{
						toRemove.add(o);
					}
				}
				else if(o.type == 1)
				{
					//do nothing, can't kill the sun
				}
				else
				{
					toRemove.add(o); 
				}
			}
		}
		for(CollidableObject o : toRemove)
		{
			System.out.println("removing " +  o);
			objects.remove(o);
		}
	}
	
	private boolean splitAsteroid(CollidableObject asteroid)
	{
		if (asteroid.radius <= 3)
		{
			return true; 
		}
		else
		{
			Vector3D a1Location = new Vector3D(asteroid.location.x + asteroid.radius, asteroid.location.y + asteroid.radius, asteroid.location.z + asteroid.radius); 
			Vector3D a1Velocity = Vector3D.mult(-1, asteroid.velocity);
			ServerAsteroid a1 = new ServerAsteroid(idcounter, a1Location, asteroid.direction, a1Velocity, asteroid.up, asteroid.radius/2, this.sun); 
			idcounter++; 
			ServerAsteroid a2 = new ServerAsteroid(idcounter, asteroid.location, asteroid.direction, asteroid.velocity, asteroid.up, asteroid.radius/2, this.sun); 
			idcounter++; 
			addQueue.add(a1); 
			addQueue.add(a2);
		}
		return false; 
	}
	
	private void addSplits()
	{
		for (CollidableObject o : addQueue)
		{
			objects.add(o); 
		}
		addQueue.clear();
	}
}
