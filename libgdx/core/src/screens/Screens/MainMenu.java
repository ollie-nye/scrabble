package screens.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.scrabble.game.scrabbleMain;

import assetmanager.assetManager;
import scrabble.Game;
import screens.ScrabbleLauncher;

public class MainMenu implements Screen {
	
	private ScrabbleLauncher game;
	private Stage stage;
	private TextButton play;
	private TextButton settings;
	private TextButton rules;
	private TextButton exit;
	private TextButton website;
	private Skin skin;
	private TextureAtlas buttonAtlas;
	private Sound hover;
	private Texture background;
	private BitmapFont font;
	
	public MainMenu(ScrabbleLauncher game){
          
	     this.game = game;
		 hover = game.getAssetManager().manager.get(assetManager.mainClick);
		 background = game.getAssetManager().manager.get(assetManager.mainBackground);
		 stage = new Stage(new ScreenViewport());
		 font = new BitmapFont();

	}

	@Override
	public void show() {
		
		Gdx.input.setInputProcessor(stage);
		
		skin = new Skin();
		buttonAtlas = game.getAssetManager().manager.get(assetManager.mainMenuButtonPack);;
		skin.addRegions(buttonAtlas);
		
		TextButtonStyle playButtonStyle = new TextButtonStyle();	
		playButtonStyle.up = skin.getDrawable("play");		
		playButtonStyle.over = skin.getDrawable("playPressed");	
		playButtonStyle.checked = skin.getDrawable("playPressed");
		playButtonStyle.font = font;
		play = new TextButton("", playButtonStyle);
		play.setPosition(515, 330f);
		play.setSize(254.0f, 65.0f);
		play.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				//TODO: Implement another screen that allows you to select player amounts
				Game gameSession = new Game(4); // temporary till above to do is implemented.
				hover.play(game.getSoundVol());
				game.setScreen(new scrabbleMain(game));
				
				}		
		});
		stage.addActor(play);
		
		TextButtonStyle settingsButtonStyle = new TextButtonStyle();	
		settingsButtonStyle.up = skin.getDrawable("settings");		
		settingsButtonStyle.over = skin.getDrawable("settingsPressed");	
		settingsButtonStyle.checked = skin.getDrawable("settingsPressed");
		settingsButtonStyle.font = font;
		settings = new TextButton("", settingsButtonStyle);
		settings.setPosition(480, 240f);
		settings.setSize(320.0f, 80.0f);
		settings.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
				hover.play(game.getSoundVol());
				game.setScreen(new SettingsMenu(game));	
				
				}		
		});
		stage.addActor(settings);
		
		TextButtonStyle rulesButtonStyle = new TextButtonStyle();	
		rulesButtonStyle.up = skin.getDrawable("help");		
		rulesButtonStyle.over = skin.getDrawable("helpPressed");	
		rulesButtonStyle.checked = skin.getDrawable("helpPressed");
		rulesButtonStyle.font = font;
		rules = new TextButton("", rulesButtonStyle);
		rules.setPosition(520f, 160f);
		rules.setSize(244.0f, 69.0f);
		rules.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
				hover.play(game.getSoundVol());
				game.setScreen(new HelpScreen(game));
				
				}		
		});
		stage.addActor(rules);
		
		TextButtonStyle exitButtonStyle = new TextButtonStyle();	
		exitButtonStyle.up = skin.getDrawable("exitButton");		
		exitButtonStyle.over = skin.getDrawable("exitPressed");	
		exitButtonStyle.checked = skin.getDrawable("exitPressed");
		exitButtonStyle.font = font;
		exit = new TextButton("", exitButtonStyle);
		exit.setPosition(515, 80f);
		exit.setSize(260.0f, 75.0f);
		exit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
				hover.play(game.getSoundVol());
				Gdx.app.exit();	
				
				}		
		});
		stage.addActor(exit);
		
		TextButtonStyle websiteButtonStyle = new TextButtonStyle();	
		websiteButtonStyle.up = skin.getDrawable("website");		
		websiteButtonStyle.over = skin.getDrawable("websiteHover");	
		websiteButtonStyle.font = font;
		website = new TextButton("", websiteButtonStyle);
		website.setPosition(0f, 0f);
		website.setSize(90.0f, 90.0f);
		website.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
				 Gdx.net.openURI("https://scrabbleemphasis.wordpress.com/");
		           
				
				}		
		});
		stage.addActor(website);
		
	}

	@Override
	public void render(float delta) {
		
            	Gdx.gl.glClearColor(0f, 0f, 0f, 1);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				
				// for animation of the play button 
				if(play.isOver()){
					play.setSize(274f, 85f);
					play.setPosition(507f, 322f);
				} else { 
					play.setPosition(515, 330f);
				    play.setSize(254.0f, 65.0f);
				}
				
				// for animation of the settings button 
				if(settings.isOver()){
					settings.setSize(331f, 91f);
					settings.setPosition(475, 232f);
				} else { 
					settings.setPosition(480, 240f);
					settings.setSize(320.0f, 80.0f);
				}
				
				// for animation of the rules button 
				if(rules.isOver()){
					rules.setSize(264f, 85f);
					rules.setPosition(505f, 153f);
				} else { 
					rules.setPosition(520f, 160f);
					rules.setSize(244.0f, 69.0f);
				}
				
				// for animation of the exit button 
				if(exit.isOver()){
					exit.setSize(270, 80f);
					exit.setPosition(510f, 79f);
				} else { 
					exit.setPosition(515, 80f);
					exit.setSize(260.0f, 75.0f);
				}
				
				
				stage.getBatch().begin();
				stage.getBatch().draw(background, 0, 0);
				stage.getBatch().end();
				
				stage.draw();
				stage.act();

		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
