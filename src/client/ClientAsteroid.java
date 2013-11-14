package client;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.utils.BufferUtils;

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
	private StillModel model;
	private Texture texture;
	
	ClientAsteroid(int id, Vector3D location, Vector3D direction, Vector3D velocity, int radius){
		super(id, 3, location, direction, velocity, radius); 
		//Drawing stuff
		ObjLoader loader = new ObjLoader();
		
	    model = loader.loadObj(Gdx.files.internal("lib/asteroid.obj"));
	    texture = new Texture(Gdx.files.internal("lib/RockSmoothErosion0042_3_M.png"));
		
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
		Gdx.gl11.glPushMatrix();
    	texture.bind();
		Gdx.gl11.glTranslatef(location.x, location.y, location.z);
		Gdx.gl11.glScalef(0.1f, 0.1f, 0.1f);
		Gdx.gl11.glScalef((float)this.radius, (float)this.radius, (float)this.radius);
		model.render();
    	Gdx.gl11.glPopMatrix();
	}

}
