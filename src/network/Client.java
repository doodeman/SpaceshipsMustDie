package network;

import java.net.InetAddress;
import java.net.UnknownHostException;

import shared.Vector3D;


public class Client 
{
	public InetAddress address; 
	public int playerId; 
	public boolean isThrusting; 
	public Vector3D facing; 
	public boolean isFiring; 
	
	public Client(String address, int playerId) throws UnknownHostException
	{
		this.playerId = playerId; 
		address = address.trim();
		if (address.startsWith("/"))
		{
			address = address.substring(1);
		}
		this.address = InetAddress.getByName(address); 
	}
	
	
	public synchronized void update(UpdateMessage update)
	{
		setFacing(update.facing);
		setFiring(update.isFiring);
		setThrusting(update.isThrusting);
	}
	
	public synchronized boolean isThrusting()
	{
		return isThrusting; 
	}
	
	public synchronized Vector3D getFacing()
	{
		return facing; 
	}
	
	public synchronized boolean isFiring()
	{
		return isFiring; 
	}
	
	public synchronized void setFacing(Vector3D facing)
	{
		this.facing = facing; 
	}
	
	public synchronized void setThrusting(boolean thrusting)
	{
		isThrusting = thrusting; 
	}
	
	public synchronized void setFiring(boolean fired)
	{
		isFiring = fired; 
	}
}
