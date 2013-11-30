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
import com.badlogic.gdx.math.Vector3;

public class ClientExplosion extends CollidableObject
{
	public int lifetime;
	private final int DENSITY = 18; 
	public boolean loading = true; 
	private ParticleEffect[] effects = new ParticleEffect[DENSITY];
	private SpriteBatch batch;
	public Camera cam;
	private boolean started = false;
	
	
	protected ClientExplosion(int id, Vector3D location,
			Vector3D direction, Vector3D velocity, Vector3D up, int radius, AssetManager assets) {
		super(id, 5, location, direction, velocity, up, radius);
		// TODO Auto-generated constructor stub
		for(int i = 0; i<DENSITY; i++){
			effects[i] = new ParticleEffect();
			effects[i].load(Gdx.files.internal("lib/explosion.p"), Gdx.files.internal("lib"));
		}
		batch = new SpriteBatch();
		
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
			float scaling = (cam.position.dst(location.toVector3())-1000) * 0.00006f;
			System.out.println(scaling/1000);
		    batch.getTransformMatrix().scale(scaling,scaling,scaling);
			float delta = Gdx.graphics.getDeltaTime();
			if(started == false){
				for(int i= 0; i<18; i++){
					effects[i].start();
				}
				started = true;
			}
			for(int i=0;i<DENSITY;i++){
				batch.getTransformMatrix().rotate(new Vector3(0,1,0), 360/DENSITY);
				batch.begin();
				effects[i].draw(batch, delta);
				batch.end();
			}
		}
		batch.getTransformMatrix().idt();
		return null;
	}
	
}
