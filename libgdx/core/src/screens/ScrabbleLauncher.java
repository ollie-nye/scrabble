package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import screens.Screens.MainMenuScreen;

public class ScrabbleLauncher extends Game {
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	public SpriteBatch batch;
	public SpriteBatch batchs;
	
	
	@Override
	public void create () {

		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));
		
		Sound theme = Gdx.audio.newSound(Gdx.files.internal("music.mp3"));
		theme.loop();
		theme.play();
			
	
	}

	@Override
	public void render () {
		super.render();
		
	}
	
}
