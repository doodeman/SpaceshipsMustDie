package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import shared.Logger;

public class UDPSender implements Runnable
{
	InetAddress serverAddress; 
	int serverPort; 
	DatagramSocket socket; 
	String message; 
	Logger log; 
	List<byte[]> chunks; 
	
	public UDPSender(Client client, int port, String message, int sequenceNo) throws IOException
	{
		serverAddress = client.address; 
		serverPort = port; 
		//log = new Logger("Server.log", false);
		chunks = null; 
		chunks = splitMessages(message, sequenceNo);
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
	 * Splits the message into 5000 byte chunks, plus header information
	 * Message format
	 * gamestate sequence number (unsigned int) | 
	 * @return
	 */
	private List<byte[]> splitMessages(String message, int sequenceNumber)
	{
		byte[] msg = message.getBytes(); 
		int totalChunks = msg.length/5000; 
		
		if (msg.length%5000 != 0)
		{
			totalChunks += 1; 
		}
		
		List<byte[]> msgList = new ArrayList<byte[]>(); 
		//System.out.println("Sequence: " + sequenceNumber + " noChunks: " + totalChunks); 
		//System.out.println(msg.length);
		for (int i = 0; i < totalChunks-1; i++)
		{
			msgList.add(prepareMessage(Arrays.copyOfRange(msg, i*5000, (i+1)*5000), sequenceNumber, 5000, i, totalChunks));
		}
		msgList.add(prepareMessage(Arrays.copyOfRange(msg, (totalChunks-1)*5000, msg.length), sequenceNumber, msg.length-((totalChunks-1)*5000), totalChunks-1, totalChunks));
		return msgList; 
	}
	
	private byte[] prepareMessage(byte[] msg, int sequenceNo, int chunkLength, int chunkNo, int totalChunks)
	{
		//System.out.println(new String(msg));
		//Logger.log("Serverchunks.txt", chunkNo + " " + new String(msg));
		String header = ""; 
		header += sequenceNo + ";"; 
		header += chunkLength + ";";
		header += totalChunks + ";";
		header += chunkNo + ";";
		byte[] headerBytes = header.getBytes(); 
		byte[] retMsg = new byte[headerBytes.length + msg.length]; 
		System.arraycopy(headerBytes, 0, retMsg, 0, headerBytes.length); 
		System.arraycopy(msg, 0, retMsg, headerBytes.length, msg.length); 
		return retMsg; 
	}
	
	/**
	 * Sends a gson object to the server
	 * @param message
	 */
	@Override
	public void run()
	{
		try
		{
			//System.out.println("sending " + chunks.size() + " chunks");
			for (byte[] chunk : chunks)
			{
				//System.out.println(new String(chunk));
				DatagramPacket outPacket = new DatagramPacket(chunk, chunk.length, serverAddress, serverPort); 
				socket.send(outPacket); 
			}
			//Logger.log("Chunks.log", totalMsg);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
			log.log("UDP CLIENT: Failed to send update to client " + serverAddress.toString()); 
		} 
		
		/*byte[] data = message.getBytes(); 
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
		} */
		socket.close(); 
		return; 
	}
}
