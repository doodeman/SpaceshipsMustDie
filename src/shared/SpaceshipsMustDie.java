package shared;

import client.MainScreen;

import com.badlogic.gdx.Game;

public class SpaceshipsMustDie extends Game
{
	MainScreen mainScreen; 
	String hostname; 
	String playerName; 
	
	public SpaceshipsMustDie(String hostname, String playerName)
	{
		this.hostname = hostname; 
		this.playerName = playerName; 
	}
	
	@Override 
	public void create()
	{
		mainScreen = new MainScreen(hostname, playerName); 
		setScreen(mainScreen); 
	}
}
