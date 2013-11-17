package network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import server.ServerGameState;
import client.ClientUpdate;

import com.google.gson.Gson;

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
		byte[] data = new byte[10000]; 
		while (true)
		{
			DatagramPacket in = new DatagramPacket(data, data.length); 
			try
			{
				socket.receive(in); 
				ClientUpdate update = new Gson().fromJson(in.toString(), ClientUpdate.class);
				
			}
			catch (Exception e)
			{
				
			}
		}
	}
	
}
