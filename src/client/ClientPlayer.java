package client;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;


import shared.CollidableObject;
import shared.Vector3D;

/**
 * @author matti
 *	Simple class to keep track of each player.
 *	It stores the size, direction and position of each asteroid (and color if I wanna go crazy).
 *	It also stores the buffer that keeps track of the asteroid shape.
 */
class ClientPlayer extends CollidableObject
{
	private Model model;
	private ModelInstance instance;
	private AssetManager assets;
	
	ClientPlayer(int id, Vector3D location, Vector3D direction, Vector3D velocity, Vector3D up, int radius, AssetManager assets){
		super(id, 2, location, direction, velocity, up, radius); 

		this.assets = assets;
	}
	

	private boolean loading = true;
	
	private void doneLoading(){
		model = assets.get("lib/spaceship.g3db", Model.class);
		instance = new ModelInstance(model);
		instance.transform.setToWorld(this.location.toVector3(), this.direction.toVector3(), this.up.toVector3());
		loading = false;
		
	}
	
	private float degrees = 1;
	/**
	 * Draws the object
	 * @return 
	 */
	@Override
	public ModelInstance draw(){ 
		boolean updateBool = assets.update();
		if(updateBool && loading){
			doneLoading();
		}
		else if(loading){
			return null;
		}
		/*
		
		Vector3 dir = currentPlayer.direction.toVector3();
		camera.direction.set(dir);
		camera.up.set(up);
		camera.update();
		 */
		//instance.transform.setToTranslation(location.toVector3());
       // view.setToLookAt(position, location.toVector3().add(direction), up);  
		instance.transform.setToWorld(this.location.toVector3(), this.direction.toVector3(), up.toVector3());		
		//instance.calculateTransforms();values
		//System.out.println(Vector3.dot(side.x, side.y, side.z, direction.x, direction.y, direction.z));
		return instance;
	}

}
