package client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
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
class ClientAsteroid extends CollidableObject
{
	private Model model;
	private ModelInstance instance;
	private ModelBatch modelBatch;
	private Environment environment;
	private Camera cam;
	
	private Model model2;
	private ModelInstance box;
	
	ClientAsteroid(int id, Vector3D location, Vector3D direction, Vector3D velocity, int radius, Environment env, Camera cam){
		super(id, 3, location, direction, velocity, radius); 
		//Drawing stuff
		environment = env;
		this.cam = cam;
		
		modelBatch = new ModelBatch();
		ModelLoader loader = new ObjLoader();
		if(id%4 == 0) model = loader.loadModel(Gdx.files.internal("lib/ast1.obj"));
		else if(id%4 == 1) model = loader.loadModel(Gdx.files.internal("lib/ast3.obj"));
		else if(id%4 == 2) model = loader.loadModel(Gdx.files.internal("lib/ast4.obj"));
		else model = loader.loadModel(Gdx.files.internal("lib/ast5.obj"));
        instance = new ModelInstance(model);	
        
        ModelBuilder modelBuilder = new ModelBuilder();
        model2 = modelBuilder.createSphere(radius*2, radius*2, radius*2, 100, 100,  new Material(ColorAttribute.createDiffuse(Color.GREEN)), Usage.Position | Usage.Normal);
        // model = modelBuilder.createSphere(radius/2, radius/2, radius/2, 100, 100, new Material(ColorAttribute.createDiffuse(Color.GREEN)), Usage.Position | Usage.Normal, 10, 10, 10, 10);
//        model2 = modelBuilder.createBox(radius, radius, radius,
//                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
//                Usage.Position | Usage.Normal);Usage.Position | Usage.Normal
       box = new ModelInstance(model2);
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
//		modelBatch.begin(cam);
//
//	    instance.transform.setToTranslationAndScaling(location.x, location.y, location.z, radius*0.1f, radius*0.1f, radius*0.1f);
//		modelBatch.render(instance, environment);
//		
//	    modelBatch.end();
	    
	    modelBatch.begin(cam);
	    box.transform.setToTranslation(location.x, location.y, location.z);
        modelBatch.render(box);
        modelBatch.end();
	}

}
