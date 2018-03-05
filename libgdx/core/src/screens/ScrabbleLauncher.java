package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

import screens.Screens.MainMenuScreen;

public class ScrabbleLauncher extends Game {
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	public SpriteBatch batch;
	
	private Music theme;
	private float soundVol = 1.0f;
	public Stage stage;
	
	
	@Override
	public void create () {
		
		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));
		
		theme = Gdx.audio.newMusic(Gdx.files.internal("sounds/Flintstones.mp3"));
		theme.setVolume(0.5f);
		theme.play();
		theme.setLooping(true);
			
	
	}
	public Music getTheme(){
		return theme;
	}
	
	public void setSoundVol(float i){
		soundVol = i;
	}
	
	public float getSoundVol(){
		return soundVol;
	}

	@Override
	public void render () {
		
		super.render();
		
		
	}
	
}
