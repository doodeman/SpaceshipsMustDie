package network;

import java.io.IOException;

import server.GameEngine;
import shared.GameState;

public class NetworkTester 
{
	public static void main(String arg[]) throws IOException
	{
		TCPServer server = new TCPServer(1234, new GameState(), new GameEngine());
		Thread serverWorker = new Thread(server); 
		serverWorker.start();
		
		ClientTCPClient client = new ClientTCPClient("localhost", 1234); 
		Thread clientWorker = new Thread(client); 
		clientWorker.start();
	}
}
