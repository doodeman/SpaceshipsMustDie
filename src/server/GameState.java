package server;

import com.google.gson.Gson;

public class GameState 
{
	public String toJson()
	{
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
