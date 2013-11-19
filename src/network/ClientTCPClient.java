package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import shared.GameState;
import shared.Logger;
import client.MainScreen;

import com.google.gson.Gson;

public class ClientTCPClient implements Runnable
{
	String address; 
	int port; 
	GameState gameState; 
	boolean done; 
	Logger log; 
	int player; 
	MainScreen clientGame; 
	
	public ClientTCPClient(String address, int port, MainScreen clientGame) throws IOException
	{
		this.address = address; 
		this.port = port; 
		log = new Logger("Client.log", false);
		this.clientGame = clientGame; 
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
		log.log("UDP CLIENT: Connecting to " + address); 
		Socket socket;
		try 
		{
			socket = new Socket(address, port);
			System.out.println("TCP CLIENT: Connected, receiving gamestate..."); 
			BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String in = fromServer.readLine();
			System.out.println("TCP CLIENT: Received gamestate. Closing connection.");
			socket.close();
			Gson gson = new Gson(); 
			InitialConnection init = gson.fromJson(in, InitialConnection.class);
			gameState = init.state;
			clientGame.setCurrentPlayer(init.playerId);
			clientGame.startUDP(init.port);
			System.out.println(gameState);
			done = true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace(); 
			log.log("TCP Client: Failed to get gamestate from server");
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
