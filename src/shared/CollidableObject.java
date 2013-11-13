package shared;

import com.badlogic.gdx.math.Vector3;

/**
 * @author matti
 *	Simple class to keep track of each asteroid.
 *	It stores the size, direction and position of each asteroid (and color if I wanna go crazy).
 *	It also stores the buffer that keeps track of the asteroid shape.
 */
public class CollidableObject{
	protected int radius;
	protected Vector3 location;
	protected Vector3 direction;
	
	protected CollidableObject(Vector3 location, Vector3 direction, Vector3 velocity, int radius){
		this.location = location;
		this.direction = direction;
		this.radius = radius;
	}
	
}
