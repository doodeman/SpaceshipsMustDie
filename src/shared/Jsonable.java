package shared;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Jsonable 
{
	public String toJson()
	{
		GsonBuilder gsonBuilder = new GsonBuilder().serializeSpecialFloatingPointValues();
		Gson gson = gsonBuilder.create();
		String json = gson.toJson(this);
		return json; 
	}
}
