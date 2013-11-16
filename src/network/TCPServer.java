package network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import server.GameEngine;
import shared.GameState;
import shared.Logger;

/**
 * A TCP server that sends the gamestate to connecting clients and notifies the game engine. 
 * @author kristleifur
 *
 */
public class TCPServer implements Runnable
{
	ServerSocket serverSocket; 
	GameState state; 
	GameEngine engine; 
	List<Client> newClients; 
	int lastPort;
	private Logger log; 
	boolean newPlayer; 
	int lastPlayerId; 
	
	public TCPServer(int port, GameState state, GameEngine engine) throws IOException
	{
		serverSocket = new ServerSocket(port); 
		this.state = state; 
		this.engine = engine; 
		newClients = new ArrayList<Client>();
		newPlayer = false; 
		log = new Logger("Server.log", false);
		lastPort = 1234; 
		lastPlayerId = -1; 
	}
	
	@Override
	public void run()
	{
		while (true)
		{
			Socket connectionSocket;
			try 
			{
				connectionSocket = serverSocket.accept();

				System.out.println(connectionSocket.getInetAddress().toString() + " connected");
				
				DataOutputStream outStream = new DataOutputStream(connectionSocket.getOutputStream());
				
				//Send gamestate
				log.log("TCP SERVER: Connection received, sending gamestate and port..."); 
				InitialConnection initialConnection = new InitialConnection(state, lastPort, lastPlayerId);
				
				outStream.writeBytes(initialConnection.toJson());
				//Add client to list of new clients
				InetAddress clientaddr; 
				clientaddr = connectionSocket.getInetAddress();
				String str = clientaddr.toString();
				newClients.add(new Client(str, lastPlayerId, lastPort));
				newPlayer = true; 
				lastPort++; 
				lastPlayerId--;
				log.log("TCP SERVER: Gamestate sent, closing connection"); 
				
				connectionSocket.close();
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					serverSocket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				log.log("TCP SERVER: TCP server failed to send gamestate"); 
			}  
		}
	}
	
	/**
	 * Returns the list of clients that have connected since last polling and then empties list. 
	 * @return
	 */
	public synchronized List<Client> getNewClients()
	{
		List<Client> retval = newClients; 
		newClients = new ArrayList<Client>(); 
		setNewPlayer(false);
		return retval; 
	}
	
	public synchronized boolean newPlayer()
	{
		return newPlayer; 
	}
	
	public synchronized void setNewPlayer(boolean tf)
	{
		newPlayer = tf; 
	}
}
