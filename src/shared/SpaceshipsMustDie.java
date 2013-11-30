package shared;

import java.io.IOException;

import server.GameEngine;
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
		menuScreen = new MenuScreen(800,600, this);
		setScreen(menuScreen); 
	}

	public void changeScreen(String hostname, String playerName) throws IOException {
		// TODO Auto-generated method stub
		this.hostname = hostname;
		this.playerName = playerName;
		if(this.hostname == "localhost"){
			GameEngine ge = new GameEngine(1234);
			Thread gameWorker = new Thread(ge); 
			gameWorker.start();
		}
		System.out.println(hostname);
		mainScreen = new MainScreen(hostname, playerName); 	
		setScreen(mainScreen);
	}
}
