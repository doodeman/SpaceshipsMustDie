package client;

import java.io.IOException;
import java.util.ArrayList;

import shared.CollidableObject;
import shared.Logger;
import network.ClientTCPClient;
import network.ClientUDPClient;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class MainScreen implements Screen {

	private Camera camera;
	private ClientController controller;
	ClientAsteroid asteroid;
	ClientSun sun;
	Logger log; 
	ClientUDPClient udpClient; 
	ClientGameState gameState; 
    private Environment environment;
	private ModelBatch modelBatch;
	String host, playername; 
	private AssetManager assets; 
	private Array<ModelInstance> instances = new Array<ModelInstance>();
	private CollidableObject currentPlayer;
	private boolean thirdPerson = false;
	private boolean pressedP = false;
	private boolean firing = false; 
	public Integer playerId = null; 
	public int assignedPort; 
	
	public MainScreen(String host, String playername)
	{
		this.host = host; 
		this.playername = playername; 
		create();
	}
	
	public void create() {

		environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
       // environment.add(new PointLight().set(1f, 1f, 1f, 0, 0, 0, 1000));
        
		camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
	    camera.near = 0.1f;
	    camera.far = 1000f;
	    camera.update();
	    
	    assets = new AssetManager();
		assets.load("lib/spaceship.g3db", Model.class);
		assets.load("lib/spaceship2.g3db", Model.class);
		
		assets.load("lib/ast1.obj", Model.class);
		assets.load("lib/ast3.obj", Model.class);
		assets.load("lib/ast4.obj", Model.class);
		assets.load("lib/ast5.obj", Model.class);
		assets.load("lib/sun.obj", Model.class);
		assets.load("lib/ship.obj", Model.class);
		assets.load("lib/shot.g3db", Model.class);
		modelBatch = new ModelBatch();
		controller = new ClientController(host,1233, playername); 
	    Thread controlWorker = new Thread(controller);
	    controlWorker.start();
        
        
		try 
		{
			log = new Logger("Client.log", true);
		} catch (IOException e1) 
		{
			e1.printStackTrace();
		} 
		
		ClientTCPClient client;
		try 
		{
			client = new ClientTCPClient(host, 1234, this);
			Thread clientWorker = new Thread(client); 
			clientWorker.start();
		} catch (IOException e2) 
		{
			log.log("Failed to launch TCP client");
			e2.printStackTrace();
		} 
		
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
	public void render(float arg0) {
		if (gameState != null)
		{
			gameState.update(); 
		}
		update();
		display();
	}

	private void display() {
		// TODO Auto-generated method stub
        if(gameState == null) return;
        instances.clear();
        if(playerId != null && currentPlayer == null){
			for (CollidableObject o : gameState.objects)
			{
		    	//System.out.println("rendering");
				ModelInstance instance = o.draw();
				
				if(instance != null) instances.add(instance); 
				if(o.id == playerId){
					currentPlayer = o;
				}
				if(o.type == 4){
					environment.add(((ClientProjectile)o).light);
				}
		    }
        }
        else{
        	for (CollidableObject o : gameState.objects)
			{
        		
				ModelInstance instance = o.draw();
				if(o.id != currentPlayer.id && instance != null) instances.add(instance); 
			}
        }
		if(instances.size > 0){
			
			Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	        Gdx.gl.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	        
			environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
			for(CollidableObject o: gameState.objects){
				if(o.type == 5){
					if(((ClientExplosion)o).cam == null) ((ClientExplosion)o).cam = camera;
					o.draw();
				}
			}
			modelBatch.begin(camera);
			modelBatch.render(instances, environment);
			modelBatch.end();
		}

	}

	private void update() {
		camera.update();
		if(Gdx.input.isKeyPressed(Input.Keys.A)) 
			controller.left();
			
		if(Gdx.input.isKeyPressed(Input.Keys.D))
			controller.right();
			
		if(Gdx.input.isKeyPressed(Input.Keys.W))
			controller.up();
		if(Gdx.input.isKeyPressed(Input.Keys.S))
			controller.down();
		if (Gdx.input.isKeyPressed(Input.Keys.Q))
			controller.rollLeft();
		if (Gdx.input.isKeyPressed(Input.Keys.E))
			controller.rollRight();
		if (Gdx.input.isKeyPressed(Input.Keys.B))
			controller.breakShip(); 
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && !firing)
		{
			controller.fire();
			firing = true; 
		}
		else if (!Gdx.input.isKeyPressed(Input.Keys.SPACE) && firing)
		{
			firing = false; 
		}
		if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
			controller.forward();
		if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
			controller.backward();
		if(Gdx.input.isKeyPressed(Input.Keys.P) && !pressedP ){
			thirdPerson = !thirdPerson;
			pressedP = true;
		}
		else if(!Gdx.input.isKeyPressed(Input.Keys.P) && pressedP){
			pressedP = false;
		}
		if(currentPlayer != null){
            camera.position.set(currentPlayer.location.toVector3());
			camera.lookAt(currentPlayer.location.toVector3().add(currentPlayer.direction.toVector3()));
			camera.up.set(currentPlayer.up.toVector3());
			camera.update();
		}	
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}
	
	public synchronized void setCurrentPlayer(int id)
	{
		this.playerId = id; 
	}
	
	public synchronized int getCurrentPlayer()
	{
		return this.playerId;
	}
	
	public synchronized void setPort(int port)
	{
		this.assignedPort = port; 
	}
	
	public synchronized int getPort()
	{
		return this.assignedPort;
	}
	
	public synchronized void startUDP(int port)
	{
		try 
		{
			System.out.println("Starting client UDP Client on port " + port);
			controller.setClientId(playerId);
			udpClient = new ClientUDPClient(port);
			Thread udpclientworker = new Thread(udpClient); 
			udpclientworker.start();
			gameState = new ClientGameState(udpClient, assets);
		} catch (IOException e) 
		{
			System.out.println("Failed to launch UDP Client");
			e.printStackTrace();
		}
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
}
