package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import shared.GameState;

import com.google.gson.Gson;

public class ClientTCPClient implements Runnable
{
	String address; 
	int port; 
	GameState gameState; 
	boolean done; 
	
	public ClientTCPClient(String address, int port)
	{
		this.address = address; 
		this.port = port; 
	}
	
	/**
	 * Connects to the game server, gets the gamestate
	 * @param address
	 * @param port
	 * @throws IOException 
	 * @throws UnknownHostException 
	*/
	@Override
	public void run() 
	{
		System.out.println("CLIENT: Connecting to " + address); 
		Socket socket;
		try 
		{
			socket = new Socket(address, port);
			System.out.println("CLIENT: Connected, receiving gamestate..."); 
			BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String in = fromServer.readLine();
			System.out.println("CLIENT: Received gamestate. Closing connection.");
			socket.close();
			Gson gson = new Gson(); 
			gameState = gson.fromJson(in, GameState.class);
			System.out.println(gameState);
			done = true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace(); 
			System.out.println("CLIENT: Failed to get gamestate from server");
		} 
	}
	
	public synchronized boolean isDone()
	{
		return done; 
	}
	
	public synchronized void done()
	{
		done = true; 
	}
}
