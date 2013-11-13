package network;

import shared.Vector3D;


public class UpdateMessage 
{
	public int clientId; 
	public boolean isThrusting, isFiring;
	public Vector3D facing; 
}