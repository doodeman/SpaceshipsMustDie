package network;

import shared.GameState;
import shared.Jsonable;

public class InitialConnection extends Jsonable
{
	public GameState state; 
	public int port; 
	public int playerId;
	
	public InitialConnection(GameState state, int port, int playerId)
	{
		this.state = state; 
		this.port = port; 
		this.playerId = playerId; 
	}
}
