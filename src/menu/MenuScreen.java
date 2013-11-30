package menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import network.ClientUDPClient;
import shared.SpaceshipsMustDie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.StringBuilder;

public class MenuScreen implements Screen, InputProcessor
{
	private SpaceshipsMustDie game;
	private int position;
	private List<Sound> sounds;
	private String title = "SPACE SHIPS MUST DIE!";
	private String nameTitle = "Name: ";
	private String name = "Unnamed Player";
	private String host = "Host Game";
	private String join = "Join Game: ";
	private String ip = "0.0.0.0";
	StringBuilder input = new StringBuilder();
	private Camera camera;
	private BitmapFont large_font;
	
	private boolean downPressed = false;
	private boolean upPressed = false;
	private boolean enterPressed = false;
	private boolean escPressed = false;
	private SpriteBatch batch;
	private Vector2 position_heading;
	private Vector2 name_heading;
	private Vector2 host_game_position;
	private BitmapFont small_font;
	private Vector2 join_game_position;
	
	public MenuScreen(int xSize, int ySize, SpaceshipsMustDie spaceshipsMustDie) 
	{
		position = 0;
		game = spaceshipsMustDie; 
		sounds = new ArrayList<Sound>(); 
		sounds.add(Gdx.audio.newSound(Gdx.files.internal("sounds/thrust.mp3")));
		sounds.add(Gdx.audio.newSound(Gdx.files.internal("sounds/bangSmall.mp3")));
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.input.setInputProcessor(this);
		batch = new SpriteBatch();
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
				Gdx.files.internal("lib/Hyperspace.ttf"));
		large_font = generator.generateFont(72);
		small_font = generator.generateFont(36);
		generator.dispose();
		large_font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		small_font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();
	
		position_heading = new Vector2((width / 2) - (int) large_font.getBounds(title).width / 2,
									   height / 2 + (int) large_font.getBounds(title).height * 4);
		name_heading = new Vector2((width / 2) - (int) small_font.getBounds(name + nameTitle).width / 2,
				   					height / 2 + (int) small_font.getBounds(name + nameTitle).height * 4);
		host_game_position = new Vector2((width/2) - (int)small_font.getBounds(name + nameTitle).width / 2,
				   					height / 2 + (int) small_font.getBounds(host).height * 2);
		join_game_position = new Vector2((width/2) - (int)small_font.getBounds(name + nameTitle).width / 2,
					height / 2);
		
	}
	
	@Override
	public void show() {
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float arg0) {
		// TODO Auto-generated method stub
		//while(true) sounds.get(0).play();
		try {
			update();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		display();
	}

	private void display() {
		// TODO Auto-generated method stub
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		batch.begin();
		large_font.draw(batch, title, position_heading.x, position_heading.y);
		if(position == 0 || position == 1) small_font.setColor(0.65f,0.1f,0.1f, 1);
		small_font.draw(batch, nameTitle + name, name_heading.x, name_heading.y);
		small_font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		if(position == 2) small_font.setColor(0.65f,0.1f,0.1f, 1);
		small_font.draw(batch, host, host_game_position.x, host_game_position.y);
		small_font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		if(position > 2) small_font.setColor(0.65f,0.1f,0.1f, 1);
		small_font.draw(batch, join + ip, join_game_position.x, join_game_position.y);
		small_font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		batch.end();
		
	}

	private void update() throws IOException {
		// TODO Auto-generated method stub
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && !downPressed){
			downPressed  = true;	
			if(position == 0) position = 2;
			else if(position == 2) position = 3;
			sounds.get(0).play();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP) && !upPressed){
			upPressed = true;
			if(position == 2) position = 0;
			else if(position == 3) position = 2;
			sounds.get(0).play();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && !escPressed){
			escPressed = true;
			if(position == 1) position = 0;
			else if(position == 4) position = 3;
			else if(position == 5) position = 3;
		}

		if(Gdx.input.isKeyPressed(Input.Keys.ENTER) && !enterPressed){
			enterPressed  = true;
			sounds.get(1).play();
			switch(position){
			case 0:	//Name selected
				input.delete(0, input.length);
				position = 1;
				break;
			case 1: //Name change in process
				position = 0;	
				name = input.toString();
				break;
			case 2: //Host game
				game.changeScreen("localhost", name);
				break;
			case 3:	//Join Game
				position = 4;
				break;
			case 4: //Type in IP
				//input.delete(0, input.length);
				position = 5;
				break;
			case 5: //waiting for ip
				ip = input.toString();
				game.changeScreen(ip, name);
				break;
			default:
				break;
			}
		}
		
		
		if(!Gdx.input.isKeyPressed(Input.Keys.DOWN) && downPressed) downPressed = false;
		if(!Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && escPressed) escPressed = false;
		if(!Gdx.input.isKeyPressed(Input.Keys.UP) && upPressed) upPressed = false;
		if(!Gdx.input.isKeyPressed(Input.Keys.ENTER)) enterPressed = false;
	}



	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean keyDown(int arg0) {
		if(position == 4){
			if(arg0 >6 && arg0 < 17) {
				input.append((arg0-7));
			}
			if(arg0 == 56) input.append('.');
			if(arg0 > 143 && arg0 < 154) input.append((arg0-144));
			ip = input.toString();
		}
		if((position == 4 || position ==1) && arg0 == 67 && input.length > 1){
			input.deleteCharAt(input.length()-1);
			if(position == 1) name = input.toString() + "_";
			else ip = input.toString() + ".";
			input.length--;
			
		}
		return false;
	}

	@Override
	public boolean keyTyped(char arg0) {
		if(position == 1){
			input.append(arg0);
			name = input.toString() + "_";
		} //name
		return false;
	}

	@Override
	public boolean keyUp(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}
}
