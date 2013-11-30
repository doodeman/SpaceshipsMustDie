package client;

import shared.CollidableObject;
import shared.Vector3D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

public class ClientExplosion extends CollidableObject
{
	public int lifetime;
	public boolean loading = true; 
	private ParticleEffect effect;
	private SpriteBatch batch;
	public Camera cam;
	public boolean started = false;
	
	protected ClientExplosion(int id, Vector3D location,
			Vector3D direction, Vector3D velocity, Vector3D up, int radius, AssetManager assets) {
		super(id, 5, location, direction, velocity, up, radius);
		// TODO Auto-generated constructor stub
	
		effect = new ParticleEffect();
		batch = new SpriteBatch();
		effect.load(Gdx.files.internal("lib/explosion.p"), Gdx.files.internal("lib"));
		//effect.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		
		
		
	}
		
	public void update()
	{
		lifetime++;
	}
	
	public ModelInstance draw(){
		if(cam != null){
			cam.update();
			batch.setProjectionMatrix(cam.projection);
			
			batch.setTransformMatrix(cam.view);
			batch.getTransformMatrix().translate(location.toVector3());
			float scaling = (cam.position.dst(location.toVector3())-1000) * 0.0001f;
			System.out.println(scaling/1000);
		    batch.getTransformMatrix().scale(scaling,scaling,scaling);
			float delta = Gdx.graphics.getDeltaTime();
			if(started == false){
				effect.start();
				started = true;
			}
			batch.begin();
			effect.draw(batch, delta);
			batch.end();
		}
		batch.getTransformMatrix().idt();
		return null;
	}
	
}
