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
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;


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
	private Texture texture;
	private ModelInstance instance;
	private ModelBatch modelBatch;
	private Environment environment;
	private Camera cam;
		
	private float rotation = 0.9f;
	ClientSun(int id, int radius, Environment env, Camera cam){
		super(id, 1, new Vector3D(0f,0f,0f), new Vector3D(0f,0f,0f), new Vector3D(0f,0f,0f), radius); 
		
		environment = env;
		this.cam = cam;
		modelBatch = new ModelBatch();
		ModelLoader loader = new ObjLoader();
        model = loader.loadModel(Gdx.files.internal("lib/sun.obj"));
        instance = new ModelInstance(model);
		
	}

	
	/**
	 * Draws the object
	 */
	@Override
	public void draw(){ 
		modelBatch.begin(cam);
		
		//instance.transform.scale((float)this.radius, (float)this.radius, (float)this.radius);
		
		instance.transform.setToScaling(radius, radius, radius);
		instance.transform.setToTranslation(location.x, location.y, location.z);
		
		instance.calculateTransforms();
		modelBatch.render(instance, environment);
	    modelBatch.end();
    	
	}

}
