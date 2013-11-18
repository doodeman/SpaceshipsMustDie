package client;

public class ClientExplosion 
{
	public int lifetime; 
	
	public ClientExplosion()
	{
		lifetime = 0; 
	}
	
	public void update()
	{
		lifetime++;
	}
}
