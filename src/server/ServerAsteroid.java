package server;

import shared.Logger;
import shared.Vector3D;

public class ServerAsteroid extends ServerCollidableObject
{
	protected ServerAsteroid(int id, Vector3D location, Vector3D direction, Vector3D velocity, Vector3D up, int radius, ServerSun sun) 
	{
		super(id, 3, location, direction, velocity, up, radius, sun);
	}
	
	@Override
	public void orbit()
	{
		/*
		 * Orbital mechanics courtesy of http://www.emanueleferonato.com/2012/03/28/simulate-radial-gravity-also-know-as-planet-gravity-with-box2d-as-seen-on-angry-birds-space/
		 */
		//Gets debris position
		//I need to know the mass of the planet because the bigger the mass, the more intense the gravity attraction. 
		float gravity = 10; 
		//Gets planet position
		//Creates a new b2Vec2 variable which will store the distance between the planet and the debris
		//Calculates the distance between the planet and the debris
		//Inverts planet distance, so that the force will move the debris in the direction of the planet origin
		float sunDistance = sunDistance(); 
		float force = (1/sunDistance)*(gravity/sunDistance);
		this.applyForce(this.vectorTo(sun, 1), force); 
		//System.out.println("LOCATION: " + this.location.x + " " + this.location.y + " " + this.location.z);
		//System.out.println("VELOCITY: " + this.velocity.x + " " + this.velocity.y + " " + this.velocity.z);
	}
	
	/**
	 * Checks to see if the asteroid is close or far enough away from the sun
	 * to initiate shenanigans to get it back into the allowed orbit
	 * @return
	 */
	public void keepInOrbit()
	{
		float dist = sunDistance(); 
		//System.out.println(dist);
		if (dist < 150)
		{
			Vector3D sunVector = this.vectorTo(sun, ((150-dist)/100)); 
			this.velocity = Vector3D.difference2(this.velocity, sunVector);
		}
		if (dist > 250)
		{
			Vector3D sunVector = this.vectorTo(sun, ((dist-250)/100));
			this.velocity = Vector3D.sum(this.velocity, sunVector); 
		}
	}
	
	private float sunDistance()
	{
		return Vector3D.difference2(sun.location, this.location).length();
	}
}
