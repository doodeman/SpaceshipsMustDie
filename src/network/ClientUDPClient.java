package network;

import java.io.IOException;
import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import shared.GameState;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

/**
 * An UDP Server that handles communication between the server and one client. 
 * @author kristleifur
 */
public class ClientUDPClient implements Runnable
{	
	private DatagramSocket socket; 
	public Client client; 
	public Gson gson; 
	
	public ClientUDPClient(int port) throws SocketException
	{
		socket = new DatagramSocket(port); 
		gson = new Gson(); 
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
				String instr = new String(in.getData());
				System.out.println(instr);

				JsonReader reader = new JsonReader(new StringReader(instr));
				reader.setLenient(true);
				
				GameState update = new Gson().fromJson(reader, GameState.class); 
				System.out.println("CLIENT: Received " + in.toString());
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			} 
		}
	}
}
