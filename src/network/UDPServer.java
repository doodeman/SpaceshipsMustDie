package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import com.google.gson.Gson;

/**
 * An UDP Server that handles communication between the server and one client. 
 * @author kristleifur
 */
public class UDPServer implements Runnable
{	
	private DatagramSocket socket; 
	public Client client; 
	
	public UDPServer(int port) throws SocketException
	{
		socket = new DatagramSocket(port); 
	}
	
	@Override
	public void run()
	{
		byte[] data = new byte[5096]; 
		while (true)
		{
			DatagramPacket in = new DatagramPacket(data, data.length); 
			try 
			{
				socket.receive(in);
				UpdateMessage update = new Gson().fromJson(in.toString(), UpdateMessage.class); 
				client.update(update);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			} 
		}
	}
}
