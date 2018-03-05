package screens.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import screens.ScrabbleLauncher;

public class SettingsMenu implements Screen{

	private ScrabbleLauncher game;
	private Stage stage;
	private Texture sliderBackgroundTexture;
	private Texture sliderKnobTexture;
	private Texture settingsBackground;
	private Slider MusicSlider;
	private Slider  soundSlider;
	private TextButtonStyle textButtonStyle;
	private BitmapFont font;
	private Skin skin;
	private TextureAtlas buttonAtlas;


	
	public SettingsMenu(ScrabbleLauncher game){
		this.game = game;
		font = new BitmapFont();
		settingsBackground = new Texture("graphics/SettingsMenu/SettingsMenuBackground.png");
		/// create stage and set it as input processor
		stage = new Stage(new ScreenViewport());
		
	}

	@Override
	public void show() {
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		skin = new Skin();
		buttonAtlas = new TextureAtlas(Gdx.files.internal("graphics/BoardScreen/gameButtons.pack"));
		skin.addRegions(buttonAtlas);

		// music volume
		sliderBackgroundTexture =new Texture(Gdx.files.internal("graphics/SettingsMenu/bar.png"));
		sliderKnobTexture = new Texture(Gdx.files.internal("graphics/SettingsMenu/knob.png"));
		SliderStyle ss = new SliderStyle();
		ss.background = new TextureRegionDrawable(new TextureRegion(sliderBackgroundTexture));
		ss.knob = new TextureRegionDrawable(new TextureRegion(sliderKnobTexture));
		MusicSlider = new Slider(0f, 1f, 0.1f, false, ss);
		MusicSlider.setValue(game.getTheme().getVolume());
		MusicSlider.setPosition(522, 440);
		MusicSlider.setSize(340f,48f);
		MusicSlider.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				game.getTheme().setVolume(MusicSlider.getValue());
				
				return false;
			}
		});
		stage.addActor(MusicSlider);
		
		// sound volume
		sliderBackgroundTexture =new Texture(Gdx.files.internal("graphics/SettingsMenu/bar.png"));
		sliderKnobTexture = new Texture(Gdx.files.internal("graphics/SettingsMenu/knob.png"));
		soundSlider = new Slider(0f, 1f, 0.1f, false, ss);
		soundSlider.setValue(game.getSoundVol());
		soundSlider.setPosition(522, 350);
		soundSlider.setSize(340f, 48f);
		soundSlider.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				game.setSoundVol(soundSlider.getValue());
				
				return false;
			}
		});
		stage.addActor(soundSlider);
		
		// Main menu button
		textButtonStyle = new TextButtonStyle();	
		textButtonStyle.up = skin.getDrawable("homeButton");		
		textButtonStyle.over = skin.getDrawable("homeButtonPressed");	
		textButtonStyle.font = font;
		
		TextButton menu = new TextButton("", textButtonStyle);
		menu.setPosition(550f,250f);
		menu.setSize(206.0f, 61.0f);
		
		menu.addListener( new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new MainMenuScreen(game));
			};
		});	
		stage.addActor(menu);
		

	}

	@Override
	public void render(float delta) {
		// clear the screen ready for next set of images to be drawn
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.getBatch().begin();
		stage.getBatch().draw(settingsBackground, 0, 0);
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
		// TODO Auto-generated method stub
		
	}

}