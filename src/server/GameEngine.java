package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import shared.Logger;
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
			
			try {
				sendUpdate(gameString);
			} catch (IOException e1) {
				log.log("Failed to send update to clients");
				e1.printStackTrace();
			} 
			
			try 
			{
				Thread.sleep(100);
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
	 * @throws IOException 
	 */
	private void sendUpdate(String gameString) throws IOException
	{
		for (Client client : clients)
		{
			//System.out.println("Sending packet to " + client.toString()); 
			UDPSender sender;
			try {
				sender = new UDPSender(client, port, gameString);
				Thread worker = new Thread(sender);
				worker.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
