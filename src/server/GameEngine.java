package server;

import java.util.List;

import network.Client;
import network.TCPServer;
import network.UDPSender;
import network.UDPServer;

public class GameEngine 
{
	int port; 
	UDPServer server; 
	TCPServer tcpServer; 
	
	
	List<Client> clients; 
	GameState gameState; 
	
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
