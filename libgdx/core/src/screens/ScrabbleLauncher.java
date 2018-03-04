package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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
		
		Music theme = Gdx.audio.newMusic(Gdx.files.internal("sounds/Flintstones.mp3"));
		theme.setVolume(0.2f);
		theme.play();
		theme.setLooping(true);
			
	
	}

	@Override
	public void render () {
		super.render();
		
	}
	
}
