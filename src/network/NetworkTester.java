package network;

import java.io.IOException;

import server.GameEngine;
import shared.GameState;

public class NetworkTester 
{
	public static void main(String arg[]) throws IOException
	{
		GameEngine ge = new GameEngine(1234);
		Thread gameWorker = new Thread(ge); 
		gameWorker.start();
		
		ClientTCPClient client = new ClientTCPClient("localhost", 1234); 
		Thread clientWorker = new Thread(client); 
		clientWorker.start();
		
		ClientUDPClient udpclient = new ClientUDPClient(1234);
		Thread udpclientworker = new Thread(udpclient); 
		udpclientworker.start();
		
		//ClientUDPClient udpclient2 = new ClientUDPClient(1234);
		//Thread udpclientworker2 = new Thread(udpclient2); 
		//udpclientworker2.start();
	}
}
