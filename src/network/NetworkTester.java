package network;

import java.io.IOException;

import client.ClientGame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

import server.GameEngine;
import shared.GameState;

public class NetworkTester 
{
	public static void main(String arg[]) throws IOException
	{
		GameEngine ge = new GameEngine(1234);
		Thread gameWorker = new Thread(ge); 
		gameWorker.start();
		
		new LwjglApplication(new ClientGame(), "Asteroids", 800, 600, false);

		//ClientUDPClient udpclient2 = new ClientUDPClient(1234);
		//Thread udpclientworker2 = new Thread(udpclient2); 
		//udpclientworker2.start();
	}
}
