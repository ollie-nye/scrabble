package screens.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.scrabble.game.scrabbleMain;

import assetmanager.assetManager;
import screens.ScrabbleLauncher;

public class MainMenuScreen implements Screen {
	
	private static final int PLAY_BUTTON_WIDTH = 254;
	private static final int PLAY_BUTTON_HEIGHT = 65;
	
	private static final int HELP_BUTTON_WIDTH = 244;
	private static final int HELP_BUTTON_HEIGHT = 69;
	
	private static final int SETTING_BUTTON_WIDTH = 320;
	private static final int SETTING_BUTTON_HEIGHT = 80;
	
	private static final int EXIT_BUTTON_WIDTH = 244;
	private static final int EXIT_BUTTON_HEIGHT = 69;
	
	private static final int PLAY_BUTTON_Y = 330;
	private static final int SETTING_BUTTON_Y = 240;
	private static final int HELP_BUTTON_Y = 160;
	private static final int EXIT_BUTTON_Y = 80;
	
	private static final int PLAY_BUTTON_X = 515;
	private static final int SETTING_BUTTON_X = 480;
	private static final int HELP_BUTTON_X = 520;
	private static final int EXIT_BUTTON_X = 515;
	
	ScrabbleLauncher game;
	
	Texture background;
	Texture logo;

	Sound hover;
	
	Texture playButtonActive;
	Texture playButtonInactive;
	
	Texture helpButtonActive;
	Texture helpButtonInactive;
	
	Texture exitButtonActive;
	Texture exitButtonInactive;
	
	Texture settingButtonActive;
	Texture settingButtonInactive;
	
	public MainMenuScreen(ScrabbleLauncher game) {
		
		this.game= game;
		
		background = game.getAssetManager().manager.get(assetManager.mainBackground);

		playButtonActive = game.getAssetManager().manager.get(assetManager.playButtonPressed);
		playButtonInactive = game.getAssetManager().manager.get(assetManager.playButton);
		
		helpButtonActive = game.getAssetManager().manager.get(assetManager.helpButtonPressed);
		helpButtonInactive = game.getAssetManager().manager.get(assetManager.helpButton);
		
		settingButtonActive = game.getAssetManager().manager.get(assetManager.settingsButtonPressed);
		settingButtonInactive = game.getAssetManager().manager.get(assetManager.settingsButton);
		
		exitButtonActive = game.getAssetManager().manager.get(assetManager.exitButtonPressed);
		exitButtonInactive = game.getAssetManager().manager.get(assetManager.exitButton);
		
		hover = game.getAssetManager().manager.get(assetManager.mainClick);
		
		
	}
	



	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		game.batch.draw(background,0,0,ScrabbleLauncher.WIDTH,ScrabbleLauncher.HEIGHT);
		
		
	
		// creates the play button with user input to start the game and hover-over effects
		
		if (Gdx.input.getX() < PLAY_BUTTON_X + PLAY_BUTTON_WIDTH  && Gdx.input.getX() > PLAY_BUTTON_X && ScrabbleLauncher.HEIGHT - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && ScrabbleLauncher.HEIGHT - Gdx.input.getY() > PLAY_BUTTON_Y ) {
		game.batch.draw(playButtonActive, PLAY_BUTTON_X - 8, PLAY_BUTTON_Y - 10, PLAY_BUTTON_WIDTH + 20 ,PLAY_BUTTON_HEIGHT + 20);
		if (Gdx.input.isTouched()) {
			hover.play(game.getSoundVol());
			game.setScreen(new scrabbleMain(game));
		}
		} else { 
		game.batch.draw(playButtonInactive, PLAY_BUTTON_X , PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH,PLAY_BUTTON_HEIGHT);		
		}
		
		// creates the help button with user input to open the help screen and hover-over effects
		
		if (Gdx.input.getX() < HELP_BUTTON_X + HELP_BUTTON_WIDTH  && Gdx.input.getX() > HELP_BUTTON_X && ScrabbleLauncher.HEIGHT - Gdx.input.getY() < HELP_BUTTON_Y + HELP_BUTTON_HEIGHT && ScrabbleLauncher.HEIGHT - Gdx.input.getY() > HELP_BUTTON_Y ) {
		game.batch.draw(helpButtonActive, HELP_BUTTON_X - 15, HELP_BUTTON_Y - 7, HELP_BUTTON_WIDTH + 20,HELP_BUTTON_HEIGHT + 16);
		if (Gdx.input.isTouched()) {
			hover.play(game.getSoundVol());
			game.setScreen(new HelpScreen(game));
		}
		} else { 
		game.batch.draw(helpButtonInactive, HELP_BUTTON_X ,HELP_BUTTON_Y, HELP_BUTTON_WIDTH,HELP_BUTTON_HEIGHT);		
		}
		
		// creates the exit button with user input to close the game and hover-over effects
		
		if (Gdx.input.getX() < SETTING_BUTTON_X + SETTING_BUTTON_WIDTH  && Gdx.input.getX() > SETTING_BUTTON_X && ScrabbleLauncher.HEIGHT - Gdx.input.getY() < SETTING_BUTTON_Y + SETTING_BUTTON_HEIGHT && ScrabbleLauncher.HEIGHT - Gdx.input.getY() > SETTING_BUTTON_Y ) {
		game.batch.draw(settingButtonActive, SETTING_BUTTON_X - 5, SETTING_BUTTON_Y - 8, SETTING_BUTTON_WIDTH + 11,SETTING_BUTTON_HEIGHT + 11);
			if (Gdx.input.isTouched()) {
				hover.play(game.getSoundVol());
				game.setScreen(new SettingsMenu(game));			
			}
		} else { 
		game.batch.draw(settingButtonInactive, SETTING_BUTTON_X ,SETTING_BUTTON_Y, SETTING_BUTTON_WIDTH,SETTING_BUTTON_HEIGHT);		
		}
		
		if (Gdx.input.getX() < EXIT_BUTTON_X + EXIT_BUTTON_WIDTH  && Gdx.input.getX() > EXIT_BUTTON_X && ScrabbleLauncher.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT && ScrabbleLauncher.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y ) {
			game.batch.draw(exitButtonActive, EXIT_BUTTON_X - 5, EXIT_BUTTON_Y - 8, EXIT_BUTTON_WIDTH + 11,EXIT_BUTTON_HEIGHT + 11);
				if (Gdx.input.isTouched()) {
					hover.play(game.getSoundVol());
					Gdx.app.exit();			
				}
			} else { 
			game.batch.draw(exitButtonInactive, EXIT_BUTTON_X ,EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH,EXIT_BUTTON_HEIGHT);		
			}
				
		
		game.batch.end();
	
		
	}
	
	
	@Override
	public void resize(int width, int height) {

		
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

}
