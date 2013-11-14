package client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.math.Vector3;


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
	private Texture texture;
	private ModelInstance instance;
	private ModelBatch modelBatch;
	private Environment environment;
	private Camera cam;
	
	ClientAsteroid(int id, Vector3D location, Vector3D direction, Vector3D velocity, int radius, Environment env, Camera cam){
		super(id, 3, location, direction, velocity, radius); 
		//Drawing stuff
		environment = env;
		this.cam = cam;
		
		modelBatch = new ModelBatch();
		ModelLoader loader = new ObjLoader();
        model = loader.loadModel(Gdx.files.internal("lib/ast1.obj"));
        instance = new ModelInstance(model);
        //texture = new Texture(Gdx.files.internal("lib/RockSmoothErosion0042_3_M.png"));

		//if(id % 2 == 0)	model = loader.load(Gdx.files.internal("lib/ast2.obj"));
		//else if(id%2 == 1) model = loader.loadObj(Gdx.files.internal("lib/ast3.obj"));
		//else model = loader.loadObj(Gdx.files.internal("lib/asteroid.obj"));
	    //texture = new Texture(Gdx.files.internal("lib/RockSmoothErosion0042_3_M.png"));
		
	}
	
	/**
	 * Changes the position, velocity and acceleration of the object.
	 */
	public void changePosition(Vector3D location, Vector3D direction, Vector3D velocity){
		this.location = location;
		this.direction = direction;
	}
	
	/**
	 * Draws the object
	 */
	@Override
	public void draw(){ 
		modelBatch.begin(cam);
		instance.transform.setToScaling(0.1f, 0.1f, 0.1f);
		instance.transform.setToScaling((float)this.radius, (float)this.radius, (float)this.radius);
		instance.transform.setToTranslation(location.x, location.y, location.z);
		modelBatch.render(instance, environment);
		
	    modelBatch.end();
	}

}
