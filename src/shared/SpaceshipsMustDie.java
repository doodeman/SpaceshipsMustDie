package shared;

import menu.MenuScreen;
import client.MainScreen;

import com.badlogic.gdx.Game;

public class SpaceshipsMustDie extends Game
{
	MainScreen mainScreen; 
	MenuScreen menuScreen; 
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
		menuScreen = new MenuScreen(800,600);
		setScreen(menuScreen); 
	}
}
