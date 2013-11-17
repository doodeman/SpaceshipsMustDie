package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import network.Client;
import network.ServerClientManager;
import network.TCPServer;
import network.UDPSender;
import shared.Logger;

public class GameEngine implements Runnable
{
	int port; 
	TCPServer tcpServer; 
	ServerClientManager clientManager; 
	
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

		//for (int i = 0; i < 1; i++)
		//{
			gameState.addAsteroid(0, 50, 0); 
		//}
		gameState.addAsteroid(0, -50, 0);
			
		tcpServer = new TCPServer(1234, gameState, this);
		clients = new ArrayList<Client>();
		this.port = port; 
		
		clientManager = new ServerClientManager(1233, gameState);
		
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
		
		Thread clientManagerWorker = new Thread(clientManager);
		clientManagerWorker.start();
		
		while(true)
		{
			if (tcpServer.newPlayer() == true)
			{
				newClients = tcpServer.getNewClients();
				for (Client client : newClients)
				{
					log.log("GameEngine: Adding new client #" + clients.size());
					gameState.addPlayer(client); 
					clients.add(client); 
				}
				newClients.clear();
			}
			
			gameState.update();
			
			String gameString = gameState.toJson();
			//System.out.println(gameString);
			//System.out.println("gameString length: " + gameString.length());
			try {
				sendUpdate(gameString, gameState.sequenceNumber);
			} catch (IOException e1) {
				log.log("Failed to send update to clients");
				e1.printStackTrace();
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
	 * @throws IOException 
	 */
	private void sendUpdate(String gameString, int sequenceNo) throws IOException
	{
		for (Client client : clients)
		{
			//System.out.println("Sending packet to " + client.toString()); 
			UDPSender sender;
			try {
				sender = new UDPSender(client, client.port, gameString, sequenceNo);
				Thread worker = new Thread(sender);
				worker.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
