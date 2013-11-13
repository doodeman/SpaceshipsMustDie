package client;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.loaders.wavefront.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;
import com.badlogic.gdx.math.Vector3;
import shared.CollidableObject;

/**
 * @author matti
 *	Simple class to keep track of each asteroid.
 *	It stores the size, direction and position of each asteroid (and color if I wanna go crazy).
 *	It also stores the buffer that keeps track of the asteroid shape.
 */
class ClientSun extends CollidableObject
{
	private StillModel model;
		
	ClientSun(int id, int radius){
		super(id, 1, new Vector3(0f,0f,0f), new Vector3(0f,0f,0f), new Vector3(0f,0f,0f), radius); 
		
		ObjLoader loader = new ObjLoader();
		 
		model = loader.loadObj(Gdx.files.internal("lib/asteroid.obj"));
		
	}

	
	/**
	 * Draws the object
	 */
	@Override
	public void draw(){ 
    	Gdx.gl11.glPushMatrix();
		Gdx.gl11.glTranslatef(location.x, location.y, location.z);
		Gdx.gl11.glScalef(0.1f, 0.1f, 0.1f);
		Gdx.gl11.glScalef((float)this.radius, (float)this.radius, (float)this.radius);
		model.render();
    	Gdx.gl11.glPopMatrix();
   	}
    	
}
