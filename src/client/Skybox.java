package client;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.BufferUtils;

public class Skybox {
	Texture[] textures = {            
			new Texture(Gdx.files.internal("lib/sky_front_02.jpg")),
			new Texture(Gdx.files.internal("lib/sky_left_04.jpg")),
			new Texture(Gdx.files.internal("lib/sky_bottom_05.jpg")),
			new Texture(Gdx.files.internal("lib/sky_right_06.jpg")),
			new Texture(Gdx.files.internal("lib/sky_top_07.jpg")),
			new Texture(Gdx.files.internal("lib/sky_back_09.jpg")),
	};
	private FloatBuffer vertexBuffer;
	private FloatBuffer texCoordBuffer;
	private Camera cam;

	public Skybox(Camera cam){
		this.cam = cam;
		Gdx.gl11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP_TO_EDGE);
		Gdx.gl11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP_TO_EDGE);
		vertexBuffer = BufferUtils.newFloatBuffer(72);
		vertexBuffer.put(new float[] {-0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f,
				0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f,
				0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f,
				0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f,
				0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f,
				-0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f,
				-0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f,
				-0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f,
				-0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f,
				0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f,
				-0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f,
				0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f});
		vertexBuffer.rewind();

		texCoordBuffer = BufferUtils.newFloatBuffer(48);
		texCoordBuffer.put(new float[] {0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
				0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
				0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
				0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
				0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
				0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f});
		texCoordBuffer.rewind();

	}
	public void draw()
	{
		Gdx.gl11.glPushMatrix();
		Gdx.gl11.glLoadIdentity();
		Gdx.gl11.glTranslatef(cam.position.x, cam.position.y, cam.position.z);
		Gdx.gl11.glScalef(200f,200f,200f);

		Gdx.gl11.glDisable(GL11.GL_DEPTH_TEST);
		Gdx.gl11.glDisable(GL11.GL_LIGHTING);
		Gdx.gl11.glDisable(GL11.GL_BLEND);
		Gdx.gl11.glShadeModel(GL11.GL_SMOOTH);
		Gdx.gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, vertexBuffer);

		for(int i=0; i<textures.length;i++){
			Gdx.gl11.glEnable(GL11.GL_TEXTURE_2D);
			Gdx.gl11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
			textures[i].bind();
			Gdx.gl11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, texCoordBuffer);
			
			if(i==0) Gdx.gl11.glNormal3f(0.0f, 0.0f, -1.0f);
			if(i==1) Gdx.gl11.glNormal3f(1.0f, 0.0f, 0.0f);
			if(i==2) Gdx.gl11.glNormal3f(0.0f, 0.0f, 1.0f);
			if(i==3) Gdx.gl11.glNormal3f(-1.0f, 0.0f, 0.0f);
			if(i==4) Gdx.gl11.glNormal3f(0.0f, 1.0f, 0.0f);
			if(i==5) Gdx.gl11.glNormal3f(0.0f, -1.0f, 0.0f);
			Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, i*4, 4);
			Gdx.gl11.glDisable(GL11.GL_TEXTURE_2D);
			Gdx.gl11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		}

		Gdx.gl11.glDisable(GL11.GL_TEXTURE_2D);
		Gdx.gl11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		Gdx.gl11.glEnable(GL11.GL_DEPTH_TEST);
		Gdx.gl11.glEnable(GL11.GL_LIGHTING);
		Gdx.gl11.glEnable(GL11.GL_BLEND);
		Gdx.gl11.glPopMatrix();
	}
}
