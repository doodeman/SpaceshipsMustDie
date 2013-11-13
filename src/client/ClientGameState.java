package client;

import network.ClientUDPClient;
import shared.CollidableObject;
import shared.GameState;

public class ClientGameState extends GameState 
{
	ClientUDPClient client; 
	
	public ClientGameState(ClientUDPClient client)
	{
		super(); 
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
					objects.add(new ClientSun(o.id,  o.radius));
				}
				if (o.type == 3)
				{
					objects.add(new ClientAsteroid(o.id, o.location, o.direction, o.velocity, o.radius));
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
