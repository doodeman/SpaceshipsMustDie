package server;

import com.badlogic.gdx.math.Vector3;

import network.Client;
import shared.CollidableObject;
import shared.GameState;

public class ServerGameState extends GameState
{
	ServerSun sun; 
	
	public ServerGameState()
	{
		super();
		Vector3 location = new Vector3(0,0,0);
		Vector3 direction = new Vector3(0,0,0); 
		Vector3 velocity = new Vector3(0,0,0); 
		sun = new ServerSun(location, direction, velocity, 20);
	}
	
	public void addPlayer(Client client)
	{
		
	}

	public void addAsteroid() {
		Vector3 location = new Vector3(1,1,1);
		Vector3 direction = new Vector3(1,1,1); 
		Vector3 velocity = new Vector3(1,1,1); 
		this.objects.add(new ServerAsteroid(location, direction, velocity, 10, sun));
	}
	
	@Override
	public void update()
	{
		for (CollidableObject o : objects)
		{
			o.update(); 
		}
	}
}
