package screens.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.scrabble.game.scrabbleMain;
import screens.ScrabbleLauncher;

public class MainMenuScreen implements Screen {
	
	private static final int PLAY_BUTTON_WIDTH = 70;
	private static final int PLAY_BUTTON_HEIGHT = 50;
	
	private static final int HELP_BUTTON_WIDTH = 70;
	private static final int HELP_BUTTON_HEIGHT = 40;
	
	private static final int EXIT_BUTTON_WIDTH = 60;
	private static final int EXIT_BUTTON_HEIGHT = 30;
	
	private static final int PLAY_BUTTON_Y = 150;
	private static final int EXIT_BUTTON_Y = 50;
	private static final int HELP_BUTTON_Y = 100;
	
	private static final int TITLE_WIDTH = 200;
	private static final int TITLE_HEIGHT = 200;
	private static final int TITLE_Y = 230;
	
	
	
	ScrabbleLauncher game;
	
	Texture background;
	Texture logo;

	Sound theme;
	
	Texture playButtonActive;
	Texture playButtonInactive;
	
	Texture helpButtonActive;
	Texture helpButtonInactive;
	
	Texture exitButtonActive;
	Texture exitButtonInactive;
	
	public MainMenuScreen(ScrabbleLauncher game) {
		
		this.game= game;
		
		background = new Texture("backgroundmain.jpg");
		logo = new Texture("scrabble.png");
		
		

		
		playButtonActive = new Texture("playPressed.png");
		playButtonInactive = new Texture("play.png");
		
		helpButtonActive = new Texture("helpPressed.png");
		helpButtonInactive = new Texture("help.png");
		
		exitButtonActive = new Texture("exitPressed.png");
		exitButtonInactive = new Texture("exit.png");
		
		
		
		
	}
	



	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		
		
		
		
		game.batch.draw(background,0,0,ScrabbleLauncher.WIDTH,ScrabbleLauncher.HEIGHT);
		game.batch.draw(logo,340,TITLE_Y,TITLE_WIDTH,TITLE_HEIGHT);
		int x = 400;
	
		// creates the play button with user input to start the game and hover-over effects
		
		if (Gdx.input.getX() < x + PLAY_BUTTON_WIDTH  && Gdx.input.getX() > x && ScrabbleLauncher.HEIGHT - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && ScrabbleLauncher.HEIGHT - Gdx.input.getY() > PLAY_BUTTON_Y ) {
		game.batch.draw(playButtonActive, x , PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH,PLAY_BUTTON_HEIGHT);
		if (Gdx.input.isTouched()) {
			game.setScreen(new scrabbleMain(game));
		
		}
		} else { 
		game.batch.draw(playButtonInactive, x ,PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH,PLAY_BUTTON_HEIGHT);		
		}
		
		// creates the help button with user input to open the help screen and hover-over effects
		
		if (Gdx.input.getX() < x + HELP_BUTTON_WIDTH  && Gdx.input.getX() > x && ScrabbleLauncher.HEIGHT - Gdx.input.getY() < HELP_BUTTON_Y + HELP_BUTTON_HEIGHT && ScrabbleLauncher.HEIGHT - Gdx.input.getY() > HELP_BUTTON_Y ) {
		game.batch.draw(helpButtonActive, x , HELP_BUTTON_Y, HELP_BUTTON_WIDTH,HELP_BUTTON_HEIGHT);
		if (Gdx.input.isTouched()) {
			game.setScreen(new HelpScreen(game));
		}
		} else { 
		game.batch.draw(helpButtonInactive, x ,HELP_BUTTON_Y, HELP_BUTTON_WIDTH,HELP_BUTTON_HEIGHT);		
		}
		
		// creates the exit button with user input to close the game and hover-over effects
		
		if (Gdx.input.getX() < x + EXIT_BUTTON_WIDTH  && Gdx.input.getX() > x && ScrabbleLauncher.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT && ScrabbleLauncher.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y ) {
		game.batch.draw(exitButtonActive, x , EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH,EXIT_BUTTON_HEIGHT);
			if (Gdx.input.isTouched()) {
				Gdx.app.exit();			
			}
		} else { 
		game.batch.draw(exitButtonInactive, x ,EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH,EXIT_BUTTON_HEIGHT);		
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
