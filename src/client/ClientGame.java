package client;

import java.io.IOException;

import shared.CollidableObject;
import shared.Logger;
import network.ClientTCPClient;
import network.ClientUDPClient;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.utils.Array;

public class ClientGame implements ApplicationListener, InputProcessor {

	private Camera camera;
	private ClientController controller;
	ClientAsteroid asteroid;
	ClientSun sun;
	Logger log; 
	ClientUDPClient udpClient; 
	ClientGameState gameState; 
    private Environment environment;
	private ModelBatch modelBatch;
	String host;
	private AssetManager assets; 
	private Array<ModelInstance> instances = new Array<ModelInstance>();
	private ClientPlayer currentPlayer;
	private boolean thirdPerson = false;
	private boolean pressedP = false;
	
	public ClientGame(String host)
	{
		this.host = host; 
	}
	
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
	    
	    assets = new AssetManager();
		assets.load("lib/spaceship.g3db", Model.class);
		assets.load("lib/ast1.obj", Model.class);
		assets.load("lib/ast3.obj", Model.class);
		assets.load("lib/ast4.obj", Model.class);
		assets.load("lib/ast5.obj", Model.class);
		assets.load("lib/sun.obj", Model.class);
		modelBatch = new ModelBatch();
	    controller = new ClientController(this.host,1234);
	    Thread controlWorker = new Thread(controller);
	    controlWorker.start();
        
        
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
			client = new ClientTCPClient(host, 1234);
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
		
		gameState = new ClientGameState(udpClient, assets);
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
		
		gameState.update(); 
		camera.update();
//		//angle, x, y, z
		if(Gdx.input.isKeyPressed(Input.Keys.A)) 
			controller.left();
		if(Gdx.input.isKeyPressed(Input.Keys.D))
			controller.right();
		if(Gdx.input.isKeyPressed(Input.Keys.W))
			controller.up();
		if(Gdx.input.isKeyPressed(Input.Keys.S))
			controller.down();
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
			controller.fire();
		if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
			controller.forward();
		if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
			controller.backward();
		if(Gdx.input.isKeyPressed(Input.Keys.P) && !pressedP ){
			thirdPerson = !thirdPerson;
			pressedP = true;
		}
		else if(!pressedP){
			pressedP = false;
		}
//		
		camera.position.set(currentPlayer.location.toVector3());
		camera.direction.set(currentPlayer.direction.toVector3());
		camera.update();
		
		
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        
        instances.clear();
        
		for (CollidableObject o : gameState.objects)
		{
				ModelInstance instance = o.draw();
				if(instance != null) instances.add(instance); 
		}
		if(instances.size > 0){
			modelBatch.begin(camera);
			modelBatch.render(instances, environment);
			modelBatch.end();
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
