package network;

import com.badlogic.gdx.math.Vector3;

public class UpdateMessage 
{
	public int clientId; 
	public boolean isThrusting, isFiring;
	public Vector3 facing; 
}
