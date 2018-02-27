package screens.Screens;

import javax.swing.text.StyledEditorKit.FontSizeAction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import screens.ScrabbleLauncher;

public class HelpScreen implements Screen {
	
	private static final int BACK_BUTTON_WIDTH = 67;
	private static final int BACK_BUTTON_HEIGHT = 48;
	
	private static final int BACK_BUTTON_Y = 50;
	
	private BitmapFont font;
	
	ScrabbleLauncher game;
	
	Texture background;
	Texture backButtonActive;
	Texture backButtonInactive;
	
	public HelpScreen(ScrabbleLauncher game) {
		
		this.game= game;
		background = new Texture("graphics/HelpMenu/helpbackground.png");
		backButtonActive = new Texture("graphics/HelpMenu/backOn.png");
		backButtonInactive = new Texture("graphics/HelpMenu/back.png");
		
		font = new BitmapFont();
		font.setColor(Color.BLACK);
		font.getData().scale(0.3f);
		
	}
	
	@Override
	public void show() {
		
		
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		
		
		
		game.batch.draw(background,0,0,ScrabbleLauncher.WIDTH,ScrabbleLauncher.HEIGHT);
		
		font.draw(game.batch,"Sample Text, Sample Text , Sample Text , Sample Text",100,350);
		font.draw(game.batch,"Sample Text, Sample Text , Sample Text , Sample Text",100,370);
		
		int x = 20;
		
		// creates the BACK button with user input to return to main menu screen and hover-over effects
		
		if (Gdx.input.getX() < x + BACK_BUTTON_WIDTH  && Gdx.input.getX() > x && ScrabbleLauncher.HEIGHT - Gdx.input.getY() < BACK_BUTTON_Y + BACK_BUTTON_HEIGHT && ScrabbleLauncher.HEIGHT - Gdx.input.getY() > BACK_BUTTON_Y ) {
		game.batch.draw(backButtonActive, x , BACK_BUTTON_Y, BACK_BUTTON_WIDTH,BACK_BUTTON_HEIGHT);
		if (Gdx.input.isTouched()) 
			{
			game.setScreen(new MainMenuScreen(game));		
			}
		} else { 
		game.batch.draw(backButtonInactive, x ,BACK_BUTTON_Y, BACK_BUTTON_WIDTH,BACK_BUTTON_HEIGHT);		
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
	public void dispose() {	}

}
