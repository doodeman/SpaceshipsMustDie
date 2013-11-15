package client;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
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
	private ModelBatch modelBatch;
	private Environment environment;
	private Camera cam;	
	private AssetManager assets;

	
	ClientAsteroid(int id, Vector3D location, Vector3D direction, Vector3D velocity, int radius, Environment env, Camera cam, AssetManager assets){
		super(id, 3, location, direction, velocity, radius); 
		this.assets = assets;
		//Drawing stuff
		this.environment = env;
		this.cam = cam;
		
		modelBatch = new ModelBatch();

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
		System.out.println("Here2");
		instance = new ModelInstance(model);
		instance.transform.setToTranslationAndScaling(location.x, location.y, location.z, radius*0.1f, radius*0.1f, radius*0.1f);
		loading = false;
	}
	
	/**
	 * Draws the object
	 */
	@Override
	public void draw(){ 
		boolean updateBool = assets.update();
		System.out.println(updateBool);
		if(loading && updateBool){
			//System.out.println("Here");
			doneLoading();
		}
		else if(loading){
			return;
		}
		
		modelBatch.begin(cam);

	    instance.transform.setToTranslationAndScaling(location.x, location.y, location.z, radius*1.25f, radius*1.25f, radius*1.25f);
		modelBatch.render(instance, environment);
//		
	    modelBatch.end();
	}

}
