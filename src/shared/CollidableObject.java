package shared;


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
	//ID is shared on client and server
	public int id; 
	//1 - sun, 2 - player, 3 - asteroid
	public int type; 
	
	protected CollidableObject(int id, int type, Vector3D location, Vector3D direction, Vector3D velocity, int radius){
		this.id = id; 
		this.location = location;
		this.direction = direction;
		this.radius = radius;
		this.velocity = velocity; 
		this.type = type; 
	}
	
	public void update()
	{
		
	}
	
	public void draw()
	{
		
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
	}
}
