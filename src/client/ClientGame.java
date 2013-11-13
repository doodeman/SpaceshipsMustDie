package client;

import java.io.IOException;
import java.nio.FloatBuffer;

import shared.CollidableObject;
import shared.Logger;
import network.ClientTCPClient;
import network.ClientUDPClient;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.BufferUtils;

public class ClientGame implements ApplicationListener, InputProcessor {

	private Camera cam;
	ClientAsteroid asteroid;
	ClientSun sun;
	Logger log; 
	ClientUDPClient udpClient; 
	ClientGameState gameState; 
	@Override
	public void create() {

		Gdx.input.setInputProcessor(this);

		// turns on lighting

		Gdx.gl11.glEnable(GL11.GL_LIGHTING);
		Gdx.gl11.glEnable(GL11.GL_LIGHT1);
     	// setting diffuse light color like a bulb or neon tube
		Gdx.gl11.glLightfv(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, new float[]{1f, 1f, 1f, 1f}, 0);
		Gdx.gl11.glLightfv(GL11.GL_LIGHT1, GL11.GL_SPECULAR, new float[]{1f, 1f, 1f, 1f}, 0);
		Gdx.gl11.glLightfv(GL11.GL_LIGHT1, GL11.GL_POSITION, new float[] { 0f,0f,0f,1f }, 0); 

		Gdx.gl11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl11.glEnable(GL11.GL_TEXTURE_2D);
		Gdx.gl11.glMatrixMode(GL11.GL_PROJECTION);
		Gdx.gl11.glLoadIdentity();
		Gdx.glu.gluPerspective(Gdx.gl11, 90, 1.333333f, 0.001f, 30.0f);

		Gdx.gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);

		this.cam = new Camera(new Point3D(3.5f, 1.0f, 2.0f), new Point3D(2.0f, 1.0f, 3.0f), new Vector3D(0.0f, 1.0f, 0.0f), this);
		
		try 
		{
			log = new Logger("Client.log", true);
		} catch (IOException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		ClientTCPClient client;
		try 
		{
			client = new ClientTCPClient("localhost", 1234);
			Thread clientWorker = new Thread(client); 
			clientWorker.start();
		} catch (IOException e2) 
		{
			log.log("Failed to launch TCP client");
			e2.printStackTrace();
		} 
		
		try 
		{
			udpClient = new ClientUDPClient(1234);
			Thread udpclientworker = new Thread(udpClient); 
			udpclientworker.start();
		} catch (IOException e) 
		{
			log.log("Failed to launch UDP Client");
			e.printStackTrace();
		}
		
		gameState = new ClientGameState(udpClient);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		Gdx.gl11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		//Gdx.gl11.glEnable(GL11.GL_LIGHT0);
		cam.setModelViewMatrix();
		
		gameState.update(); 
		
		for (CollidableObject o : gameState.objects)
		{
			o.draw(); 
		}
		
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) 
			cam.yaw(-90.0f * deltaTime);
		
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) 
			cam.yaw(90.0f * deltaTime);
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) 
			cam.pitch(-90.0f * deltaTime);
		
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) 
			cam.pitch(90.0f * deltaTime);
		
		if(Gdx.input.isKeyPressed(Input.Keys.W)) 
			cam.slide(0.0f, 0.0f, -2.0f * deltaTime);
		
		if(Gdx.input.isKeyPressed(Input.Keys.S)) 
			cam.slide(0.0f, 0.0f, 2.0f * deltaTime);
		
		if(Gdx.input.isKeyPressed(Input.Keys.A)) 
			cam.slide(-5.0f * deltaTime, 0.0f, 0.0f);
		
		if(Gdx.input.isKeyPressed(Input.Keys.D)) 
			cam.slide(5.0f * deltaTime, 0.0f, 0.0f);
		if(Gdx.input.isKeyPressed(Input.Keys.R)) 
			cam.slide(0.0f, 10.0f * deltaTime, 0.0f);
		
		if(Gdx.input.isKeyPressed(Input.Keys.F)) 
			cam.slide(0.0f, -10.0f * deltaTime, 0.0f);
		
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean keyDown(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

}
