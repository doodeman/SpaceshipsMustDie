package client;

import shared.CollidableObject;
import shared.Vector3D;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

public class ClientProjectile extends CollidableObject
{
	private AssetManager assets; 
	private ModelInstance instance; 
	private Model model; 
	private boolean loading = true; 
	
	protected ClientProjectile(int id, Vector3D location,
			Vector3D direction, Vector3D velocity, Vector3D up, int radius,  AssetManager assets) {
		super(id, 4, location, direction, velocity, up, radius);
		this.assets = assets; 
	}

	private void doneLoading(){
		if(id % 4 == 0) model = assets.get("lib/ast1.obj", Model.class);
		else if(id % 4 == 1) model = assets.get("lib/ast3.obj", Model.class);
		else if(id % 4 == 2) model = assets.get("lib/ast4.obj", Model.class);
		else model = assets.get("lib/ast5.obj", Model.class);
		instance = new ModelInstance(model);
		instance.transform.setToTranslationAndScaling(location.x, location.y, location.z, radius*0.1f, radius*0.1f, radius*0.1f);
		loading = false;
	}
	
	/**
	 * Draws the object
	 * @return 
	 */
	@Override
	public ModelInstance draw(){ 
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
