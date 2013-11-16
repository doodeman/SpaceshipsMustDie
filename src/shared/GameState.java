package shared;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class GameState extends Jsonable
{
	public List<CollidableObject> objects;
	public int sequenceNumber; 

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
