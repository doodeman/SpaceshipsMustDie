package network;

import java.io.IOException;
import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import server.Logger;
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
	Logger log; 
	
	public ClientUDPClient(int port) throws IOException
	{
		socket = new DatagramSocket(port); 
		gson = new Gson(); 
		log = new Logger("Client.log", false);
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
				log.log("UDP CLIENT: Received gamestate: " + instr);

				JsonReader reader = new JsonReader(new StringReader(instr));
				reader.setLenient(true);
				
				GameState update = new Gson().fromJson(reader, GameState.class); 
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			} 
		}
	}
}
