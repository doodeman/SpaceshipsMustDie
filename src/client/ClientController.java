package client;

import com.google.gson.Gson;

/**
 * A class that handles communication from client to server. 
 * @author kristleifur
 *
 */
public class ClientController implements Runnable
{
	private String serverAddress;
	int serverPort; 
	@SuppressWarnings("unused")
	private boolean forward, backward, left, right, fire, up, down; 
	
	public ClientController(String serverAddress, int serverPort)
	{
		this.serverAddress = serverAddress; 
		this.serverPort = serverPort; 
		reset(); 
	}
	
	public synchronized void forward()
	{
		forward = true; 
	}
	
	public synchronized void backward()
	{
		backward = true; 
	}
	
	public synchronized void left()
	{
		left = true; 
	}
	
	public synchronized void right()
	{
		right = true; 
	}
	
	public synchronized void fire()
	{
		fire = true; 
	}
	
	public synchronized void up()
	{
		up = true; 
	}
	
	public synchronized void down()
	{
		down = true; 
	}
	
	private String toJson()
	{
		Gson gson = new Gson();
		String json =  gson.toJson(this);
		return json; 
	}
	
	private synchronized void reset()
	{
		forward = false; 
		backward = false; 
		left = false; 
		right = false; 
		fire = false; 
		up = false; 
		down = false; 
	}

	@Override
	public void run() 
	{ 
		while (true)
		{
			try 
			{
				ClientUDPSender sender = new ClientUDPSender(serverAddress, serverPort, toJson());
				Thread worker = new Thread(sender); 
				worker.start(); 
				Thread.sleep(15);
				reset(); 
			} catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
