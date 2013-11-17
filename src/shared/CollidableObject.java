package shared;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;


/**
 * @author matti
 *	Simple class to keep track of each asteroid.
 *	It stores the size, direction and position of each asteroid (and color if I wanna go crazy).
 *	It also stores the buffer that keeps track of the asteroid shape.
 */
public class CollidableObject{
	public int radius;
	public Vector3D location;
	public Vector3D direction;
	public Vector3D velocity; 
	public Vector3D up; 
	public Vector3D side; 
	//ID is shared on client and server
	public int id; 
	//1 - sun, 2 - player, 3 - asteroid
	public int type; 
	public boolean hasCollided;
	
	protected CollidableObject(int id, int type, Vector3D location, Vector3D direction, Vector3D velocity, Vector3D up, int radius){
		this.id = id; 
		this.location = location;
		this.direction = direction;
		this.radius = radius;
		this.velocity = velocity; 
		this.type = type; 
		hasCollided = false; 
		this.up = up; 
		this.side = Vector3D.cross(up, direction);
	}
	
	public void update()
	{
		
	}
	
	public ModelInstance draw()
	{
		return null;
		
	}
	
	/**
	 * Returns a vector pointing from this object to another collidable object with specified length
	 * @param that
	 * @param length
	 * @return
	 */
	public Vector3D vectorTo(CollidableObject that, float length)
	{
		Vector3D euclid = Vector3D.difference2(that.location, this.location);
		euclid = Vector3D.divide(euclid, euclid.length());
		return Vector3D.mult(length, euclid); 
	}
	
	/**
	 * Sets all state related values of this to that
	 * @param that
	 */
	public void copy(CollidableObject that)
	{
		this.location = that.location; 
		this.velocity = that.velocity; 
		this.direction = that.direction; 
		this.direction.normalize();
	}
	
	public boolean hasCollided(CollidableObject o)
	{
		return false; 
	}
	
	public void yaw(float degrees)
	{
	}
	
	public void roll(float degrees)
	{
		
	}
	
	public void pitch (float degrees)
	{
		Vector3 direction3 = this.direction.toVector3(); 
		Vector3 side3 = this.side.toVector3(); 
		direction3 = direction3.rotate(side3, 90);
		System.out.println(direction3.x + " " + direction3.y + " " + direction3.z);
		System.out.println(side3.x + " " + side3.y + " " + side3.z);
		this.direction = new Vector3D(direction3); 
	}
	
	public void destroy()
	{
		
	}
}
