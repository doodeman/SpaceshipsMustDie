package client;

import java.util.ArrayList;
import java.util.List;

import network.ClientUDPClient;
import shared.CollidableObject;
import shared.GameState;

import com.badlogic.gdx.assets.AssetManager;

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
		if (client != null)
		{
			//System.out.println("Gamestate updating through udp client");
			for (CollidableObject o:  client.getState().objects)
			{
				if (!this.contains(o))
				{
					if (o.type == 1)
					{
						this.objects.add(new ClientSun(o.id,  o.radius, assets));
					}
					if (o.type == 2)
					{
						System.out.println("o.id" + o.id);
						this.objects.add(new ClientPlayer(o.id, o.location, o.direction, o.velocity, o.up, 1, assets));
					}
					if (o.type == 3)
					{
						this.objects.add(new ClientAsteroid(o.id, o.location, o.direction, o.velocity, o.up, o.radius,assets));
					}
				}
			}
			
			//update old objects
			for (CollidableObject o: client.getState().objects)
			{
				CollidableObject oldObject = this.getById(o.id);
				try {
					oldObject.copy(o); 
					
						
				}
				catch (NullPointerException e)
				{
					System.out.println("oh no");
				}
			}
		}
	}
}
