package shared;

import java.io.IOException;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

import server.GameEngine;

public class Runner 
{
	public static void main(String arg[]) throws IOException
	{
		if (arg[0].equals("host"))
		{
		
			new LwjglApplication(new SpaceshipsMustDie("localhost", arg[1]), "Asteroids", 1024, 768, false);
		}
		else
		{
			new LwjglApplication(new SpaceshipsMustDie(arg[0], arg[1]), "Asteroids", 1024, 768, false);
		}

		//ClientUDPClient udpclient2 = new ClientUDPClient(1234);
		//Thread udpclientworker2 = new Thread(udpclient2); 
		//udpclientworker2.start();
	}
}
