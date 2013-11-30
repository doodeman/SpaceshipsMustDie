package shared;

import java.io.IOException;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

import server.GameEngine;

public class Runner 
{
	public static void main(String arg[]) throws IOException
	{
		new LwjglApplication(new SpaceshipsMustDie("localhost", "Player"), "Asteroids", 1024, 768, false);
	}
}
