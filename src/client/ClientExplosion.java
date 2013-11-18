package client;

import shared.CollidableObject;
import shared.Vector3D;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

public class ClientExplosion extends CollidableObject
{
	public int lifetime;
	AssetManager assets;  
	public boolean loading = true; 
	private Model model;
	private ModelInstance instance;
	private float degrees = 5;

	protected ClientExplosion(int id, Vector3D location,
			Vector3D direction, Vector3D velocity, Vector3D up, int radius, AssetManager assets) {
		super(id, 5, location, direction, velocity, up, radius);
		// TODO Auto-generated constructor stub
		this.assets = assets; 
	}
	
	private void doneLoading(){
		model = assets.get("lib/ast1.obj", Model.class);
		instance = new ModelInstance(model);
		instance.transform.setToTranslationAndScaling(location.x, location.y, location.z, radius*0.1f, radius*0.1f, radius*0.1f);
		loading = false;
	}
	
	public void update()
	{
		lifetime++;
	}
	
	public ModelInstance draw(){
		degrees = degrees + 20 % 360;
		boolean updateBool = assets.update();
		if(loading && updateBool){
			//System.out.println("Here");
			doneLoading();
		}
		else if(loading){
			return null;
		}
	    instance.transform.setToTranslationAndScaling(location.x, location.y, location.z, radius*1.25f, radius*1.25f, radius*1.25f);
		return instance;
	}
	
}
