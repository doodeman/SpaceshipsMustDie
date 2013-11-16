package client;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;



import shared.CollidableObject;
import shared.Vector3D;

/**
 * @author matti
 *	Simple class to keep track of each asteroid.
 *	It stores the size, direction and position of each asteroid (and color if I wanna go crazy).
 *	It also stores the buffer that keeps track of the asteroid shape.
 */
class ClientSun extends CollidableObject
{
		private Model model;
		private ModelInstance instance;
		private AssetManager assets;
		
	ClientSun(int id, int radius, AssetManager assets){
		super(id, 1, new Vector3D(0f,0f,0f), new Vector3D(0f,0f,0f), new Vector3D(0f,0f,0f), radius); 
		this.assets = assets;	
	}
	private boolean loading = true;
	
	private void doneLoading(){
		model = assets.get("lib/sun.obj", Model.class);
		System.out.println("HereSun" + model);
		instance = new ModelInstance(model);
		instance.transform.setToTranslationAndScaling(location.x, location.y, location.z, radius*0.3f, radius*0.3f, radius*0.3f);
		loading = false;
	}
	/**
	 * Draws the object
	 * @return 
	 */
	@Override
	public ModelInstance draw(){ 
		
		boolean updateBool = assets.update();
		//System.out.println(updateBool);
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
