package client;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

public class ClientExplosion 
{
	public int lifetime;
	AssetManager assets; 
	
	public ClientExplosion(AssetManager assets)
	{
		lifetime = 0; 
		this.assets = assets; 
	}
	
	public void update()
	{
		lifetime++;
	}
	
	public ModelInstance draw()
	{
		return null;
	}
	
}
