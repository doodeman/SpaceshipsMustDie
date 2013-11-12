package network;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.badlogic.gdx.math.Vector3;

public class Client 
{
	public InetAddress address; 
	public int id; 
	
	public boolean isThrusting; 
	public Vector3 facing; 
	public boolean isFiring; 
	
	public Client(String address) throws UnknownHostException
	{
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
	
	public synchronized Vector3 getFacing()
	{
		return facing; 
	}
	
	public synchronized boolean isFiring()
	{
		return isFiring; 
	}
	
	public synchronized void setFacing(Vector3 facing)
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
