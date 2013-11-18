package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
	List<RespawnPoint> respawnPoints; 
	private Random random; 
	List<CollidableObject> toRemove;
	
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
		respawnPoints = makeRespawnPoints(); 
		random = new Random(); 
		toRemove = new ArrayList<CollidableObject>(); 
	}
	
	public void addPlayer(Client client)
	{
		Vector3D location = new Vector3D(-100,0,100);
		Vector3D direction = new Vector3D(1,0,0); 
		//Vector3D velocity = new Vector3D((float)Math.random(),(float)Math.random(),(float)Math.random()); 
		Vector3D velocity = new Vector3D(0,0,0); 
		Vector3D up = new Vector3D(0,1,0);
		CollidableObject player = new ServerPlayer(playerCount, playerCount, location, direction, velocity, up, 1, this.sun);
		player.side = new Vector3D(0,0,1);
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
		Vector3D pVelocity = Vector3D.setLength(Vector3D.unitVector(player.direction), 4f);
		//Vector3D pVelocity = new Vector3D(0f,0f,0f);
	
		ServerProjectile projectile = new ServerProjectile(idcounter, 4, pLocation, player.direction, pVelocity, player.up, 4, this.sun, player.id);
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
			if (o.type == 5)
			{
				ServerExplosion explosion = (ServerExplosion) o; 
				if (explosion.lifetime > 20)
				{
					toRemove.add(explosion);
				}
			}
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
		for (CollidableObject o : objects)
		{
			if (o.destroyed == true)
			{
				//System.out.println(o.id);

				//if o is a asteroid
				if (o.type == 3)
				{
					if (splitAsteroid(o));
					{
						toRemove.add(o);
					}
				}
				//if o is a player
				if (o.type == 2)
				{
					killPlayer((ServerPlayer) o);
				}
				//if it's a projectile we want it to explode
				else if (o.type == 4)
				{
					addQueue.add(new ServerExplosion(idcounter, new Vector3D(o.location), new Vector3D(o.direction),
							new Vector3D(o.velocity), new Vector3D(o.up), 10));
					idcounter++; 
					toRemove.add(o); 
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
		toRemove.clear();
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
			System.out.println("Adding " + o);
			objects.add(o); 
		}
		addQueue.clear();
	}
	
	private void killPlayer(ServerPlayer player)
	{
		System.out.println("player died");
		CollidableObject collidedWith = getById(player.collidedWith); 
		//If he collided with a projectile
		if (collidedWith.type == 4)
		{
			ServerProjectile projectile = (ServerProjectile) collidedWith; 
			if (projectile.owner == player.id)
			{
				//System.out.println("player died to his own projectile"); 

				player.score--; 
			}
			else
			{
				//System.out.println("player died to another player's projectile"); 
				ServerPlayer killingPlayer = (ServerPlayer) getById(projectile.owner);
				killingPlayer.score++; 
			}
		}
		else if (collidedWith.type == 2)
		{
			//If two players killed each other via collision
			//System.out.println("player died by colliding with another player "); 
			ServerPlayer killingPlayer = (ServerPlayer) collidedWith; 
			killingPlayer.score++; 
			player.score++; 
		}
		else
		{
			System.out.println("player died by colliding scenery (lol)"); 
			//If player is dumb and killed himself via collision with asteroid or the sun
			player.score--; 
		}
		addQueue.add(new ServerExplosion(idcounter, new Vector3D(player.location), new Vector3D(player.direction),
				new Vector3D(player.velocity), new Vector3D(player.up), 10));
		idcounter++;
		respawnPlayer(player); 
	}
	
	private void respawnPlayer(CollidableObject player)
	{
		player.destroyed = false; 
		RespawnPoint point = respawnPoints.get(random.nextInt(respawnPoints.size()));
		player.velocity = new Vector3D(point.velocity);
		player.direction = new Vector3D(point.direction); 
		player.location = new Vector3D(point.location);
	}
	
	private List<RespawnPoint> makeRespawnPoints()
	{
		List<RespawnPoint> points = new ArrayList<RespawnPoint>(); 
		points.add(new RespawnPoint(new Vector3D(-100, 0, 100), new Vector3D(0,0,0), new Vector3D(1,0,0))); 
		points.add(new RespawnPoint(new Vector3D(100, 0, 100), new Vector3D(0,0,0), new Vector3D(1,0,0))); 
		points.add(new RespawnPoint(new Vector3D(50, 0, 50), new Vector3D(0,0,0), new Vector3D(1,0,0))); 
		points.add(new RespawnPoint(new Vector3D(-50, 0, 50), new Vector3D(0,0,0), new Vector3D(1,0,0))); 
		return points; 
	}
	
	private class RespawnPoint
	{
		public Vector3D location, velocity, direction; 
		public RespawnPoint(Vector3D location, Vector3D velocity, Vector3D direction)
		{
			this.location = location; 
			this.velocity = velocity; 
			this.direction = direction; 
		}
	}
}


