package client;

import java.io.IOException;

import shared.CollidableObject;
import shared.Logger;
import shared.Point3D;
import shared.Vector3D;
import network.ClientTCPClient;
import network.ClientUDPClient;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;

public class ClientGame implements ApplicationListener, InputProcessor {

	private Camera camera;
	ClientAsteroid asteroid;
	ClientSun sun;
	Logger log; 
	ClientUDPClient udpClient; 
	ClientGameState gameState; 
    private Environment environment;
	private CameraInputController camController;
	
	@Override
	public void create() {

		environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
        environment.add(new PointLight().set(1f, 1f, 1f, 0, 0, 0, 1000));
        
		Gdx.gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(40f, 40f, 40f);
	    camera.lookAt(0,0,0);
	    camera.near = 0.1f;
	    camera.far = 300f;
	    camera.update();
		//this.cam = new Camera1(new Point3D(3.5f, 1.0f, 2.0f), new Point3D(2.0f, 1.0f, 3.0f), new Vector3D(0.0f, 1.0f, 0.0f), this);
	    camController = new CameraInputController(camera);
        Gdx.input.setInputProcessor(camController);
        
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
		
		gameState = new ClientGameState(udpClient, environment, camera);
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
		camController.update();
        
		gameState.update(); 
		
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        
		for (CollidableObject o : gameState.objects)
		{
				o.draw();
	
		}
		
		//float deltaTime = Gdx.graphics.getDeltaTime();
		
//		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) 
//			cam.yaw(-90.0f * deltaTime);
//		
//		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) 
//			cam.yaw(90.0f * deltaTime);
//		if(Gdx.input.isKeyPressed(Input.Keys.UP)) 
//			cam.pitch(-90.0f * deltaTime);
//		
//		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) 
//			cam.pitch(90.0f * deltaTime);
//		
//		if(Gdx.input.isKeyPressed(Input.Keys.W)) 
//			cam.slide(0.0f, 0.0f, -40.0f * deltaTime);
//		
//		if(Gdx.input.isKeyPressed(Input.Keys.S)) 
//			cam.slide(0.0f, 0.0f, 40.0f * deltaTime);
//		
//		if(Gdx.input.isKeyPressed(Input.Keys.A)) 
//			cam.slide(-20.0f * deltaTime, 0.0f, 0.0f);
//		
//		if(Gdx.input.isKeyPressed(Input.Keys.D)) 
//			cam.slide(20.0f * deltaTime, 0.0f, 0.0f);
//		if(Gdx.input.isKeyPressed(Input.Keys.R)) 
//			cam.slide(0.0f, 10.0f * deltaTime, 0.0f);
//		
//		if(Gdx.input.isKeyPressed(Input.Keys.F)) 
//			cam.slide(0.0f, -10.0f * deltaTime, 0.0f);
		
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
