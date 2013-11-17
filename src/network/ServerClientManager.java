package network;

import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import server.ServerGameState;
import client.ClientUpdate;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class ServerClientManager implements Runnable
{
	DatagramSocket socket; 
	ServerGameState state; 
	public ServerClientManager(int port, ServerGameState state) throws SocketException
	{
		socket = new DatagramSocket(port); 
		this.state = state; 
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
				state.updatePlayer(update);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
}
