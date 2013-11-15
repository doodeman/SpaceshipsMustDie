package client;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;


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
	private Model model2;
	private ModelInstance instance;
	private ModelInstance box;
	private ModelBatch modelBatch;
	private Environment environment;
	private Camera cam;
		
	ClientSun(int id, int radius, Environment env, Camera cam){
		super(id, 1, new Vector3D(0f,0f,0f), new Vector3D(0f,0f,0f), new Vector3D(0f,0f,0f), radius); 
		
		environment = env;
		this.cam = cam;
		modelBatch = new ModelBatch();
		ModelLoader loader = new ObjLoader();
        model = loader.loadModel(Gdx.files.internal("lib/sun.obj"));
        instance = new ModelInstance(model);
        ModelBuilder modelBuilder = new ModelBuilder();
        model2 = modelBuilder.createSphere(radius*2, radius*2, radius*2, 100, 100,  new Material(ColorAttribute.createDiffuse(Color.RED)), Usage.Position | Usage.Normal);
        // model = modelBuilder.createSphere(radius/2, radius/2, radius/2, 100, 100, new Material(ColorAttribute.createDiffuse(Color.GREEN)), Usage.Position | Usage.Normal, 10, 10, 10, 10);
//        model2 = modelBuilder.createBox(radius, radius, radius,
//                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
//                Usage.Position | Usage.Normal);Usage.Position | Usage.Normal
       box = new ModelInstance(model2);
	}
	
	/**
	 * Draws the object
	 */
	@Override
	public void draw(){ 
		
//		modelBatch.begin(cam);
//	    instance.transform.setToTranslationAndScaling(location.x, location.y, location.z, (float)radius*0.3f, (float)radius*0.3f, (float)radius*0.3f);
//		//System.out.println(instance.transform.cpy());
//		
//        modelBatch.render(instance, environment);
//        modelBatch.end();
	    
        modelBatch.begin(cam);
        modelBatch.render(box);
        modelBatch.end();

	}

}
