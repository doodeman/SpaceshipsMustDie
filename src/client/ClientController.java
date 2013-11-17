package client;

/**
 * A class that handles communication from client to server. 
 * @author kristleifur
 *
 */
public class ClientController implements Runnable
{
	private String serverAddress;
	int serverPort;
	volatile Integer clientId;
	@SuppressWarnings("unused")
	private boolean forward, backward, left, right, fire, up, down; 
	
	public ClientController (String serverAddress, int serverPort)
	{
		System.out.println("ClientController started");
		this.serverAddress = serverAddress; 
		this.serverPort = serverPort; 
		clientId = null; 
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
		System.out.println("ClientController thread is running");
		while (true)
		{
			try 
			{
				Thread.sleep(15);
				if (clientId != null)
				{			
				
					ClientUpdate update = new ClientUpdate(forward, backward, left, right, fire, up, down, clientId); 
					ClientUDPSender sender = new ClientUDPSender(serverAddress, 1233, update.toJson());
					Thread worker = new Thread(sender); 
					worker.start(); 
					reset(); 
				} 
			}
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void setClientId(int clientId)
	{
		this.clientId = clientId; 
	}
}
