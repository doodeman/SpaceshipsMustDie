package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientUDPSender implements Runnable
{
	InetAddress serverAddress; 
	int port; 
	DatagramSocket socket; 
	String message; 
	
	public ClientUDPSender (String address, int port, String message) throws Exception
	{
		serverAddress = InetAddress.getByName(address); 
		this.port = port;
		socket = new DatagramSocket(); 
		this.message = message; 
	}

	@Override
	public void run() 
	{
		byte[] msg = message.getBytes(); 
		DatagramPacket outPacket = new DatagramPacket(msg, msg.length, serverAddress, port);
		try 
		{
			socket.send(outPacket);
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		socket.close();
	}
}
