package screens.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import assetmanager.assetManager;
import screens.ScrabbleLauncher;

public class SettingsMenu implements Screen {

	private ScrabbleLauncher game;
	private Stage stage;
	private Texture sliderBackgroundTexture;
	private Texture sliderKnobTexture;
	private Texture settingsBackground;
	private Slider musicSlider;
	private Slider soundSlider;
	private TextButtonStyle textButtonStyle;
	private TextButtonStyle textButtonStyle2;
	private TextButtonStyle textButtonStyle3;
	private BitmapFont font;
	private Skin skin;
	private TextureAtlas buttonAtlas;
	private TextButton soundButton;
	private Sound hover;
	private TextButton musicButton;
	

	public SettingsMenu(ScrabbleLauncher game) {
		this.game = game;
		font = new BitmapFont();
		
		hover = game.getAssetManager().manager.get(assetManager.mainClick);
		settingsBackground = game.getAssetManager().manager.get(assetManager.settingsBackground);
		
		/// create stage and set it as input processor
		stage = new Stage(new ScreenViewport());

	}

	@Override
	public void show() {
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		skin = new Skin();
		buttonAtlas = game.getAssetManager().manager.get(assetManager.gameButtonPack);
		skin.addRegions(buttonAtlas);

		// Music Slider volume control
		sliderBackgroundTexture = game.getAssetManager().manager.get(assetManager.sliderBar);
		sliderKnobTexture = game.getAssetManager().manager.get(assetManager.sliderKnob);
		SliderStyle ss = new SliderStyle();
		ss.background = new TextureRegionDrawable(new TextureRegion(sliderBackgroundTexture));
		ss.knob = new TextureRegionDrawable(new TextureRegion(sliderKnobTexture));
		musicSlider = new Slider(0f, 1f, 0.1f, false, ss);
		musicSlider.setValue(game.getTheme().getVolume());
		musicSlider.setPosition(522, 440);
		musicSlider.setSize(340f, 48f);
		musicSlider.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				game.getTheme().setVolume(musicSlider.getValue());

				return false;
			}
		});
		stage.addActor(musicSlider);

		// Sound Slider volume control
		sliderBackgroundTexture = game.getAssetManager().manager.get(assetManager.sliderBar);
		sliderKnobTexture = game.getAssetManager().manager.get(assetManager.sliderKnob);
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

		// Music Toggle button
		textButtonStyle2 = new TextButtonStyle();
		textButtonStyle2.up = skin.getDrawable("musicButton");
		textButtonStyle2.checked = skin.getDrawable("musicButtonPressed");
		textButtonStyle2.over = skin.getDrawable("musicButtonHover");
		textButtonStyle2.font = font;
		musicButton = new TextButton("", textButtonStyle2);
		musicButton.setPosition(411f, 425f);
		musicButton.setSize(85.0f, 85.0f);
		musicButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.getTheme().setVolume(0f);
				musicSlider.setValue(0f);
				if (musicButton.isChecked() == false) {
					musicSlider.setValue(0.5f);
				}

			};
		});

		stage.addActor(musicButton);

		// Sound Toggle button
		textButtonStyle3 = new TextButtonStyle();
		textButtonStyle3.up = skin.getDrawable("soundButton");
		textButtonStyle3.checked = skin.getDrawable("soundButtonPressed");
		textButtonStyle3.over = skin.getDrawable("soundButtonHover");
		textButtonStyle3.font = font;

		soundButton = new TextButton("", textButtonStyle3);
		soundButton.setPosition(411.0f, 334.0f);
		soundButton.setSize(85.0f, 85.0f);

		soundButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				soundSlider.setValue(0f);
				if (soundButton.isChecked() == false) {
					soundSlider.setValue(0.5f);
				}

			};
		});

		stage.addActor(soundButton);

		// Main menu button
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("mainMenuButton");
		textButtonStyle.over = skin.getDrawable("mainMenuButtonPressed");
		textButtonStyle.font = font;

		TextButton menu = new TextButton("", textButtonStyle);
		menu.setPosition(550f, 250f);
		menu.setSize(206.0f, 61.0f);

		menu.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new MainMenu(game));
				hover.play(game.getSoundVol());
			};
		});
		stage.addActor(menu);

	}

	@Override
	public void render(float delta) {
		// clear the screen ready for next set of images to be drawn
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// So the Sound button is checked when returning to settings menu
		if (soundSlider.getValue() == 0) {
			soundButton.setChecked(true);
		} else 
			soundButton.setChecked(false);
		
		// So the Music button is checked when returning to settings menu
		if (game.getTheme().getVolume() == 0) {
			musicButton.setChecked(true);
		} else 
			musicButton.setChecked(false);
		
		stage.getBatch().begin();
		stage.getBatch().draw(settingsBackground, 0, 0);
		stage.getBatch().end();
		stage.draw();
		stage.act();

	}
	
	public Slider getSoundSlider(){
		return soundSlider;
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