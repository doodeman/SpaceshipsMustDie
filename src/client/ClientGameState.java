package client;

import com.badlogic.gdx.assets.AssetManager;

import network.ClientUDPClient;
import shared.CollidableObject;
import shared.GameState;

public class ClientGameState extends GameState 
{
	ClientUDPClient client;
	private AssetManager assets;
	
	public ClientGameState(ClientUDPClient client, AssetManager assets)
	{
		super(); 
		this.assets = assets;
		this.client = client; 
	}
	
	public void update()
	{
		//Add new objects
		for (CollidableObject o:  client.getState().objects)
		{
			if (!this.contains(o))
			{
				if (o.type == 1)
				{
					objects.add(new ClientSun(o.id,  o.radius, assets));
				}
				if (o.type == 3)
				{
					objects.add(new ClientAsteroid(o.id, o.location, o.direction, o.velocity, o.radius,assets));
				}
			}
		}
		
		//update old objects
		for (CollidableObject o: client.getState().objects)
		{
			CollidableObject oldObject = this.getById(o.id);
			oldObject.copy(o); 
		}
	}
}
