package client;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class DesktopRunner {
	/**
	 * And so it all begin...
	 * @param args
	 */
	public static void main(String[] args) {
		new LwjglApplication(new ClientGame(), "Asteroids", 800, 600, false);
	}

}
