package screens;

import assetmanager.assetManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import screens.screens.MainMenu;

public class ScrabbleLauncher extends Game {
	
	private assetManager assets;
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public SpriteBatch batch;
	private Music theme;
	private static float soundVol = 0.5f;
	public Stage stage;
	
	
	@Override
	public void create () {
		assets = new assetManager();
        batch = new SpriteBatch();
		assets.load();
		assets.manager.finishLoading();
		this.setScreen(new MainMenu(this));
		theme = assets.manager.get(assetManager.mainMusic);
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
	
	public assetManager getAssetManager(){
		return assets;
	}

	@Override
	public void render () {
		super.render();	
	}
	
	@Override
	public void dispose(){
		assets.dispose();
	}
}
