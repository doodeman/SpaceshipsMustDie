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
class ClientAsteroid extends CollidableObject
{
	private Model model;
	private ModelInstance instance;

	private AssetManager assets;

	
	ClientAsteroid(int id, Vector3D location, Vector3D direction, Vector3D velocity, Vector3D up, int radius, AssetManager assets){
		super(id, 3, location, direction, velocity, up, radius); 
		this.assets = assets;
	}
	
	/**
	 * Changes the position, velocity and acceleration of the object.
	 */
	public void changePosition(Vector3D location, Vector3D direction, Vector3D velocity){
		this.location = location;
		this.direction = direction;
	}
	private boolean loading = true;
	
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
