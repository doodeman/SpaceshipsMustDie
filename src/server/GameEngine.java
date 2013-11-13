package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import network.Client;
import network.TCPServer;
import network.UDPSender;

public class GameEngine implements Runnable
{
	int port; 
	TCPServer tcpServer; 
	
	List<Client> clients; 
	List<Client> newClients;
	ServerGameState gameState; 
	
	public static Logger log; 
	
	public static Logger getLogger()
	{
		return log; 
	}
	
	public GameEngine(int port) throws IOException
	{
		gameState = new ServerGameState(); 
		gameState.addAsteroid();
		tcpServer = new TCPServer(1234, gameState, this);
		clients = new ArrayList<Client>();
		this.port = port; 
		
		log = new Logger("Server.log", true);
	}
	
	/**
	 * Runs the game server
	 */
	@Override
	public void run()
	{
		Thread serverWorker = new Thread(tcpServer); 
		serverWorker.start(); 
		
		while(true)
		{
			if (tcpServer.newPlayer() == true)
			{
				newClients = tcpServer.getNewClients();
				for (Client client : newClients)
				{
					log.log("GameEngine: Adding new client #" + clients.size());
					client.id = clients.size(); 
					gameState.addPlayer(client); 
					clients.add(client); 
				}
				newClients.clear();
			}
			
			gameState.update();
			
			String gameString = gameState.toJson();
			
			for (Client client : clients)
			{
				//System.out.println("Sending packet to " + client.toString()); 
				UDPSender sender = new UDPSender(client, port, gameString);
				Thread worker = new Thread(sender);
				worker.start();
			}
			
			try 
			{
				Thread.sleep(15);
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Sends gamestate update to all clients
	 */
	private void sendUpdate()
	{
		String updateString = gameState.toJson(); 
		for (Client client : clients)
		{
			Runnable task = new UDPSender(client, port, updateString);
			Thread worker = new Thread(task); 
			worker.start(); 
		}
	}
}
