package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import shared.Logger;

public class UDPSender implements Runnable
{
	InetAddress serverAddress; 
	int serverPort; 
	DatagramSocket socket; 
	String message; 
	Logger log; 
	
	public UDPSender(Client client, int port, String message) throws IOException
	{
		serverAddress = client.address; 
		serverPort = port; 
		log = new Logger("Server.log", false);
		try 
		{
			socket = new DatagramSocket();
		} 
		catch (SocketException e) {
			e.printStackTrace();
			//log.log("UDP CLIENT: Failed to send update to client " + serverAddress.toString()); 
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
			//log.log("UDP CLIENT: Gamestate sent");
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			//log.log("UDP CLIENT: Failed to send update to client " + serverAddress.toString()); 
		} 
		socket.close(); 
		return; 
	}
}
