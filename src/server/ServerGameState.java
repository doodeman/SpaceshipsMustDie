package server;

import java.io.IOException;

import network.Client;
import shared.CollidableObject;
import shared.GameState;
import shared.Logger;
import shared.Vector3D;

public class ServerGameState extends GameState
{
	ServerSun sun; 
	
	public ServerGameState() throws IOException
	{
		super();
		Vector3D location = new Vector3D(0,0,0);
		Vector3D direction = new Vector3D(0,0,0); 
		Vector3D velocity = new Vector3D(0,0,0); 
		sun = new ServerSun(0, location, direction, velocity, 20);
		objects.add(sun); 
	}
	
	public void addPlayer(Client client)
	{
		
	}

	public void addAsteroid() {
		Vector3D location = new Vector3D(1,1,1);
		Vector3D direction = new Vector3D(1,1,1); 
		Vector3D velocity = new Vector3D(1,1,1); 
		int id = objects.size(); 
		this.objects.add(new ServerAsteroid(id, location, direction, velocity, 10, sun));
	}
	
	@Override
	public void update()
	{
		for (CollidableObject o : objects)
		{
			Logger.log("Server.log", "GAMESTATe: Updating: " + o);
			Logger.log("Server.log", "GAMESTATE: Old values: " + o.location.x + " " + o.location.y + " " + o.location.z);
			o.update(); 			
			Logger.log("Server.log", "GAMESTATE: New values: " + o.location.x + " " + o.location.y + " " + o.location.z);
		}
	}
}
