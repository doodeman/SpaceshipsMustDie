package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPSender implements Runnable
{
	InetAddress serverAddress; 
	int serverPort; 
	DatagramSocket socket; 
	String message; 
	
	public UDPSender(Client client, int port, String message)
	{
		serverAddress = client.address; 
		serverPort = port; 
		try 
		{
			socket = new DatagramSocket();
		} 
		catch (SocketException e) {
			e.printStackTrace();
			System.out.println("Failed to send update to client " + serverAddress.toString()); 
		} 
		this.message = message; 
	}
	
	/**
	 * Sends a gson object to the server
	 * @param message
	 */
	@Override
	public void run()
	{
		byte[] data = message.getBytes(); 
		DatagramPacket outPacket = new DatagramPacket(data, data.length, serverAddress, serverPort); 

		try 
		{
			socket.send(outPacket);
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Failed to send update to client " + serverAddress.toString()); 
		} 
		socket.close(); 
		return; 
	}
}
