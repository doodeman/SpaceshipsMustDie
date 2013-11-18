package client;

import java.util.ArrayList;
import java.util.List;

import network.ClientUDPClient;
import shared.CollidableObject;
import shared.GameState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

public class ClientGameState extends GameState 
{
	ClientUDPClient client;
	private AssetManager assets;
	public List<Sound> sounds; 
	public ClientGameState(ClientUDPClient client, AssetManager assets)
	{
		super(); 
		this.assets = assets;
		this.client = client; 
		sounds = new ArrayList<Sound>(); 
		sounds.add(Gdx.audio.newSound(Gdx.files.internal("lib/dspistol.mp3")));
		sounds.add(Gdx.audio.newSound(Gdx.files.internal("lib/dsbarexp.mp3")));
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
					if (o.type == 4)
					{
						this.objects.add(new ClientProjectile(o.id, o.location, o.direction, o.velocity, o.up, o.radius, assets));
						sounds.get(0).play(); 
					}
					if (o.type == 5)
					{
						this.objects.add(new ClientExplosion(o.id, o.location, o.direction, o.velocity, o.up, o.radius, assets));
					}
				}
			}
			
			//Remove destroyed objects
			List<CollidableObject> toRemove = new ArrayList<CollidableObject>(); 
			for (CollidableObject o: objects)
			{
				boolean found = false; 
				for (CollidableObject o2 : client.getState().objects)
				{
					if (o2.id == o.id)
						found = true;
				}
				if (!found)
				{
					toRemove.add(o);
				}
			}
			for (CollidableObject o: toRemove)
			{
				if (o.type == 4)
				{
					sounds.get(1).play();
				}
				objects.remove(o);
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
