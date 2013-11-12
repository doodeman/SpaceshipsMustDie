package client;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.BufferUtils;
import shared.CollidableObject;

/**
 * @author matti
 *	Simple class to keep track of each asteroid.
 *	It stores the size, direction and position of each asteroid (and color if I wanna go crazy).
 *	It also stores the buffer that keeps track of the asteroid shape.
 */
class ClientCollidableObject extends CollidableObject
{
	int radius;
	private Vector3 location, direction;
	private FloatBuffer vertexBuffer;
	private FloatBuffer normalBuffer;
	private int stacks;
	private int slices;
	private int vertexCount;
	
	ClientCollidableObject(Vector3 location, Vector3 direction, Vector3 velocity, int radius, FloatBuffer vertexBuffer){
		super(location, direction, velocity, radius); 
		//Drawing stuff
		this.vertexBuffer = vertexBuffer;
    	stacks = 100;
    	slices = 100;
    	vertexCount = 0;
    	
    	float[] array = new float[(100)*(1+1)*6];
    	float stackInterval = (float)Math.PI / (float)stacks;
    	float sliceInterval = 2.0f*(float)Math.PI / (float)slices;
    	float stackAngle, sliceAngle;
    	for(int stackCount = 0; stackCount < stacks; stackCount++) {
    		stackAngle = stackCount * stackInterval;
    		for(int sliceCount = 0; sliceCount < slices+1; sliceCount++) {
    			sliceAngle = sliceCount * sliceInterval;
    			array[vertexCount*3] =          (float)Math.sin(stackAngle) * (float)Math.cos(sliceAngle);
    			array[vertexCount*3 + 1] = (float)Math.cos(stackAngle);
    			array[vertexCount*3 + 2] = (float)Math.sin(stackAngle) * (float)Math.sin(sliceAngle);

    			array[vertexCount*3 + 3] = (float)Math.sin(stackAngle + stackInterval) * (float)Math.cos(sliceAngle);
    			array[vertexCount*3 + 4] = (float)Math.cos(stackAngle + stackInterval);
    			array[vertexCount*3 + 5] = (float)Math.sin(stackAngle + stackInterval) * (float)Math.sin(sliceAngle);

    			vertexCount += 2;
    		}
    	}
    	vertexBuffer = BufferUtils.newFloatBuffer(vertexCount*3);
    	vertexBuffer.put(array);
    	vertexBuffer.rewind();
    	normalBuffer = BufferUtils.newFloatBuffer(vertexCount*3);
    	normalBuffer.put(array);
    	normalBuffer.rewind();
		
	}
	
	/**
	 * Changes the position, velocity and acceleration of the object.
	 */
	public void changePosition(Vector3 location, Vector3 direction, Vector3 velocity){
		this.location = location;
		this.direction = direction;
	}
	
	/**
	 * Draws the object
	 */
	public void draw(){ 
		Gdx.gl11.glEnableClientState(GL11.GL_NORMAL_ARRAY);	
    	
    	Gdx.gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, vertexBuffer);
    	Gdx.gl11.glNormalPointer(GL11.GL_FLOAT, 0, normalBuffer);
    	Gdx.gl11.glTranslatef(location.x, location.y, location.z);
    	Gdx.gl11.glScalef(0.2f, 0.2f, 0.2f);
    	for(int i = 0; i < vertexCount; i += (slices+1)*2) {
    		Gdx.gl11.glDrawArrays(GL11.GL_LINE_LOOP, i, (slices+1)*2);
    	}
	}

}
