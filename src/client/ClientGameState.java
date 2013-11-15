package client;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;

import network.ClientUDPClient;
import shared.CollidableObject;
import shared.GameState;

public class ClientGameState extends GameState 
{
	ClientUDPClient client;
	private Camera camera;
	private Environment environment; 
	private AssetManager assets;
	
	public ClientGameState(ClientUDPClient client, Environment environment, Camera camera, AssetManager assets)
	{
		super(); 
		this.assets = assets;
		this.camera = camera;
		this.environment = environment;
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
					objects.add(new ClientSun(o.id,  o.radius, environment, camera, assets));
				}
				if (o.type == 3)
				{
					objects.add(new ClientAsteroid(o.id, o.location, o.direction, o.velocity, o.radius, environment, camera, assets));
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
