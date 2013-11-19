package client;

import shared.CollidableObject;
import shared.Vector3D;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

import com.badlogic.gdx.graphics.g3d.environment.PointLight;
public class ClientProjectile extends CollidableObject
{
	private AssetManager assets; 
	private ModelInstance instance; 
	private Model model; 
	private boolean loading = true;
	public PointLight light; 
	
	protected ClientProjectile(int id, Vector3D location,
			Vector3D direction, Vector3D velocity, Vector3D up, int radius,  AssetManager assets) {
		super(id, 4, location, direction, velocity, up, radius);
		this.assets = assets; 
	}

	private void doneLoading(){
		model = assets.get("lib/shot.g3db", Model.class);
		instance = new ModelInstance(model);
		instance.transform.setToScaling(0.02f, 0.02f, 0.02f);
		instance.transform.setToWorld(this.location.toVector3(), this.direction.toVector3(), this.up.toVector3());
        
		light = new PointLight();
		light.set(1, 1, 1, location.toVector3(), 100000);
		
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
		
		light.position.set(location.toVector3().add(direction.toVector3().scl(-1,-1,-1)));
	    instance.transform.setToTranslationAndScaling(location.x, location.y, location.z, 0.25f,0.25f, 0.25f);
	    
	    return instance;
	}
}
