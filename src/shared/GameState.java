package shared;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class GameState 
{
	List<CollidableObject> objects;
	
	public String toJson()
	{
		objects = new ArrayList<CollidableObject>();
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
	public void update()
	{
		
	}
}
