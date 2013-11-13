package shared;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger 
{
	File file; 
	BufferedWriter writer; 
	String name;
	public Logger(String name, boolean clear) throws IOException
	{
		this.name = name;
		if (clear)
		{
			clear();
		}
	}
	
	public void clear() throws IOException
	{
		File file = new File(name);
		 
		if (!file.exists()) 
		{
			file.createNewFile();
		}
		writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
		writer.write("");
		writer.close();
	}
	public void log(String content)
	{
		try {
			File file = new File(name);
			writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true)); 
			writer.write(content + '\n');
			writer.close();
		} catch (IOException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void log(String filename, String content)
	{
		BufferedWriter writer; 

		try {
			File file = new File(filename);
			writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true)); 
			writer.write(content + '\n');
			writer.close();
		} catch (IOException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
