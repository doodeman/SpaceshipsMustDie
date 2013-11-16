package network;

import java.io.IOException;
import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import shared.GameState;
import shared.Logger;

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
	GameState gameState; 
	
	Chunk[] chunks;
	Integer currentSequence; 
	Integer receivedOfCurrentChunk; 
	
	public ClientUDPClient(int port) throws IOException
	{
		System.out.println(port);
		socket = new DatagramSocket(port); 
		gson = new Gson(); 
		log = new Logger("Client.log", false);
		gameState = new GameState(); 
		
		currentSequence = null;
		chunks = null; 
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
				Chunk received = decodeChunk(instr); 
				if (currentSequence == null || currentSequence != received.sequenceNo)
				{
					//System.out.println(received.sequenceNo);
					currentSequence = received.sequenceNo; 
					receivedOfCurrentChunk = 1; 
					chunks = new Chunk[received.totalChunks];
					chunks[received.chunkNo] = received; 
					if (received.totalChunks == 1)
					{
						//Logger.log("ClientGameState.log", "Constructing gamestate from 1 chunk.");
						constructGamestate(chunks); 
						currentSequence = null; 
					}
				}
				else if (currentSequence == received.sequenceNo)
				{
					//System.out.println("chunkno " + received.chunkNo + " sequenceno " + received.sequenceNo);
					receivedOfCurrentChunk += 1; 
					chunks[received.chunkNo] = received; 
					if (receivedOfCurrentChunk == received.totalChunks)
					{
						//Logger.log("ServerGamestate.log", "Constructing gamestate from " + receivedOfCurrentChunk + " chunks.");
						constructGamestate(chunks); 
						currentSequence = null; 
						chunks = null; 
					}
				}
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			} 
		}
	}
	
	private Chunk decodeChunk(String str)
	{
		String[] parts = str.split(";"); 
		int sequenceNo = Integer.parseInt(parts[0]); 
		int messageLength = Integer.parseInt(parts[1]);
		int totalChunks = Integer.parseInt(parts[2]); 
		int chunkNo = Integer.parseInt(parts[3]); 
		String chunkMsg = parts[4].substring(0, messageLength); 
		return new Chunk(chunkMsg, sequenceNo, messageLength, totalChunks, chunkNo); 
	}
	
	private void constructGamestate(Chunk[] chunks)
	{
		String json = ""; 
		for (int i = 0; i < chunks.length; i++)
		{
			//Logger.log("Clientchunks.txt", i + " " + chunks[i].chunkMsg);
			json = json + chunks[i].chunkMsg; 
		}
		JsonReader reader = new JsonReader(new StringReader(json));
		reader.setLenient(true);
		gameState = new Gson().fromJson(reader, GameState.class); 
	}
	
	public synchronized GameState getState()
	{
		//log.log("UDP CLIENT: Gamestate updating"); 
		return gameState; 
	}
	
	private class Chunk
	{
		public String chunkMsg; 
		public int sequenceNo, totalChunks, chunkNo, chunkLength; 
		
		public Chunk(String chunkMsg, int sequenceNo, int chunkLength, int totalChunks, int chunkNo)
		{
			this.chunkMsg = chunkMsg; 
			this.sequenceNo = sequenceNo; 
			this.totalChunks = totalChunks; 
			this.chunkNo = chunkNo; 
			this.chunkLength = chunkLength; 
		}
	}
}

