package shared;

import java.util.ArrayList;
import java.util.List;


import com.google.gson.Gson;

public class GameState 
{
	public List<CollidableObject> objects;
	
	public String toJson()
	{
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
	public void update()
	{
		
	}
	
	public GameState()
	{
		objects = new ArrayList<CollidableObject>();
	}
	
	public boolean contains(CollidableObject o)
	{
		for (CollidableObject object : objects)
		{
			if (object.id == o.id)
				return true; 
		}
		return false; 
	}
	
	public CollidableObject getById(int id)
	{
		for (CollidableObject object : objects)
		{
			if (object.id == id)
				return object; 
		}
		return null; 
	}
}
