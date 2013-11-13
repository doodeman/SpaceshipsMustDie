package client;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.BufferUtils;

public class Box
{
	Point3D location;
	private FloatBuffer vertexBuffer;
	private Box[][] maze; 
	private int xpos, ypos;
	private Texture texture;
	private FloatBuffer texCoordBuffer;
	
	Box(FloatBuffer buffer, Point3D loc, Box[][] maze, int mazePosX, int mazePosY){
		this.location =loc;
		this.vertexBuffer = buffer;
		texCoordBuffer = BufferUtils.newFloatBuffer(48);
        texCoordBuffer.put(new float[] {0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                                        0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                                        0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                                        0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                                        0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                                        0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f});
        texCoordBuffer.rewind();
        
        texture = new Texture(Gdx.files.internal("lib/box.png"));
        
        this.xpos = mazePosY; 
        this.ypos = mazePosX; 
        
        this.maze = maze; 
	}
	
	public void draw(){
		Gdx.gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, this.vertexBuffer);
		Gdx.gl11.glPushMatrix();
		Gdx.gl11.glShadeModel(GL11.GL_SMOOTH);
           
        Gdx.gl11.glEnable(GL11.GL_TEXTURE_2D);
        Gdx.gl11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
           
        texture.bind(); //Gdx.gl11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

        Gdx.gl11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, texCoordBuffer);

		
		Gdx.gl11.glTranslatef(this.location.x, this.location.y, this.location.z);
		//Gdx.gl11.glScalef(1f, 1f, 1f);
		Gdx.gl11.glNormal3f(0.0f, 0.0f, -1.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		Gdx.gl11.glNormal3f(1.0f, 0.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 4, 4);
		Gdx.gl11.glNormal3f(0.0f, 0.0f, 1.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 8, 4);
		Gdx.gl11.glNormal3f(-1.0f, 0.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 12, 4);
		Gdx.gl11.glNormal3f(0.0f, 1.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 16, 4);
		Gdx.gl11.glNormal3f(0.0f, -1.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 20, 4);
		Gdx.gl11.glPopMatrix();
		
        Gdx.gl11.glDisable(GL11.GL_TEXTURE_2D);
        Gdx.gl11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
	}
	
	public List<Box> getNeighbors()
	{
		List<Box> neighbors = new ArrayList<Box>(); 
		try
		{
			neighbors.add(maze[xpos-1][ypos-1]);
		} catch (Exception e) {}
		try 
		{
			neighbors.add(maze[xpos-1][ypos]);
		} catch (Exception e) {}
		try 
		{
			neighbors.add(maze[xpos-1][ypos+1]);
		} catch (Exception e) {}
		try
		{
			neighbors.add(maze[xpos][ypos+1]); 
		} catch (Exception e) {}
		try
		{
			neighbors.add(maze[xpos][ypos-1]); 
		} catch (Exception e) {}
		try
		{
			neighbors.add(maze[xpos+1][ypos+1]); 
		} catch (Exception e) {}
		try
		{
			neighbors.add(maze[xpos+1][ypos]); 
		} catch (Exception e) {}
		try
		{
			neighbors.add(maze[xpos+1][ypos-1]); 
		} catch (Exception e) {}
		//neighbors.add(this);
		return neighbors;
	}
	
	public List<Point3D[]> getEdges()
	{
		Point3D[] top, bottom, left, right; 
		top = new Point3D[2]; 
		top[0] = new Point3D(location.x + 0.5, (double) 0, location.z + 0.5); 
		top[1] = new Point3D(location.x - 0.5, (double) 0, location.z + 0.5); 
		bottom = new Point3D[2]; 
		bottom[0] = new Point3D(location.x + 0.5, (double) 0, location.z - 0.5); 
		bottom[1] = new Point3D(location.x - 0.5, (double) 0, location.z - 0.5);
		left = new Point3D[2]; 
		left[0] = new Point3D(location.x + 0.5, (double) 0, location.z + 0.5); 
		left[1] = new Point3D(location.x + 0.5, (double) 0, location.z - 0.5); 
		right = new Point3D[2]; 
		right[0] = new Point3D(location.x - 0.5, (double) 0, location.z + 0.5); 
		right[1] = new Point3D(location.x - 0.5, (double) 0, location.z -0.5); 
		
		List<Point3D[]> edges = new ArrayList<Point3D[]>(); 
		edges.add(top);
		edges.add(bottom); 
		edges.add(left); 
		edges.add(right);
		return edges; 
	}
}