package menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class Menu implements Screen
{
	private static final float BUTTON_WIDTH = 300f; 
	private static final float BUTTON_HEIGHT = 60f; 
	private static final float BUTTON_SPACING = 10f;
	private final Stage stage; 
	private Table table; 
	int viewPortWidth, viewPortHeight; 
	
	public Menu(int viewPortWidth, int viewPortHeight)
	{
		stage = new Stage(viewPortWidth, viewPortHeight, true);
		table = new Table(); 
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage); 
		TextButton button = new TextButton("Host", new Skin()); 
		table.row(); 
		table.add(button); 
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
		
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
}
