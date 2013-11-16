package shared;

import com.google.gson.Gson;

public class Jsonable 
{
	public String toJson()
	{
		Gson gson = new Gson();
		String json = gson.toJson(this);
		return json; 
	}
}
