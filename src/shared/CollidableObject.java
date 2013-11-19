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
	public boolean destroyed = false; 
	public int collidedWith; 
	
	
	protected CollidableObject(int id, int type, Vector3D location, Vector3D direction, Vector3D velocity, Vector3D up, int radius){
		this.id = id; 

		this.radius = radius;
		this.velocity = velocity; 
		this.type = type; 
		this.location = location;
		hasCollided = false; 
		this.direction = direction;
		this.velocity = velocity;
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
		this.up = that.up;
		this.side = that.side;
	}
	
	public boolean hasCollided(CollidableObject o)
	{
		return false; 
	}
	
	public void yaw(float degrees)
	{
		Vector3 newSide = side.toVector3();
		Vector3 newDir = direction.toVector3();
		newSide.rotate(this.up.toVector3(), degrees);
		newDir.rotate(this.up.toVector3(), degrees);
		direction.fromVector3(newDir);
		side.fromVector3(newSide);
		System.out.println(this.up.length());
		System.out.println(this.direction.length());
		System.out.println(this.side.length());
		System.out.println(this.up.toVector3().dot(this.side.toVector3()));
		System.out.println(this.up.toVector3().dot(this.direction.toVector3()));
		System.out.println(this.direction.toVector3().dot(this.side.toVector3()));
	}
	
	
	public void roll(float degrees)
	{
		Vector3 newUp = up.toVector3();
		Vector3 newSide = side.toVector3();
		
		newUp.rotate( direction.toVector3(),degrees);
		newSide.rotate(direction.toVector3(), degrees);
		up.fromVector3(newUp.nor());
		side.fromVector3(newSide.nor());
		System.out.println(this.up.toVector3().dot(this.side.toVector3()));
		System.out.println(this.up.toVector3().dot(this.direction.toVector3()));
		System.out.println(this.direction.toVector3().dot(this.side.toVector3()));

	}
	
	public void pitch (float degrees)
	{
		Vector3 newUp = up.toVector3();
		Vector3 newDir = direction.toVector3();
		
		newUp.rotate(side.toVector3(), degrees);
		newDir.rotate(side.toVector3(), degrees);

		
		up.fromVector3(newUp);
		direction.fromVector3(newDir);
		System.out.println(this.up.toVector3().dot(this.side.toVector3()));
		System.out.println(this.up.toVector3().dot(this.direction.toVector3()));
		System.out.println(this.direction.toVector3().dot(this.side.toVector3()));
		
		
	}
	
	public void destroy()
	{
		destroyed = true; 
	}
}
