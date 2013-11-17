package network;

import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import server.ServerGameState;
import client.ClientUpdate;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class ServerClientManager implements Runnable
{
	DatagramSocket socket; 
	ServerGameState state; 
	List<ClientUpdate> pendingUpdates; 
	public ServerClientManager(int port, ServerGameState state) throws SocketException
	{
		socket = new DatagramSocket(port); 
		this.state = state; 
		pendingUpdates = new ArrayList<ClientUpdate>(); 
	}
	
	public synchronized void addPending(ClientUpdate update)
	{
		pendingUpdates.add(update); 
	}
	
	public synchronized List<ClientUpdate> getPending()
	{
		ArrayList<ClientUpdate> retlist = new ArrayList<ClientUpdate>(pendingUpdates);
		pendingUpdates.clear();
		return retlist; 
		
	}
	
	@Override
	public void run() 
	{
		System.out.println("ServerClientManager running");
		byte[] data = new byte[10000]; 
		while (true)
		{
			DatagramPacket in = new DatagramPacket(data, data.length); 
			try
			{
				socket.receive(in); 
				String json = new String(in.getData());
				JsonReader reader = new JsonReader(new StringReader(json));
				reader.setLenient(true);
				ClientUpdate update = new Gson().fromJson(reader, ClientUpdate.class);
				addPending(update);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
}
