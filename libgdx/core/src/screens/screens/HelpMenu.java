package screens.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import assetmanager.assetManager;
import screens.ScrabbleLauncher;

public class HelpMenu implements Screen {

	private ScrabbleLauncher game;
	private Stage stage;
	private Texture helpBackground;
	private BitmapFont font;
	TextButtonStyle textButtonStyle;
	private Skin skin;
	private TextureAtlas buttonAtlas;
	private Sound hover;
	private Skin tempSkin;
	private TextureAtlas tempTextures;
	private int helpCounter;
	private Texture img1 , img2 , img3 , img4;
	private Texture circle , circle1;

	SpriteBatch batch;
	String myText;

	public HelpMenu(ScrabbleLauncher game) {
		this.game = game;
		font = new BitmapFont();

		helpCounter = 0;

		hover = game.getAssetManager().manager.get(assetManager.mainClick);
		helpBackground = game.getAssetManager().manager.get(assetManager.helpBackground);

		img1 = game.getAssetManager().manager.get(assetManager.img1);
		img2 = game.getAssetManager().manager.get(assetManager.img2);
		img3 = game.getAssetManager().manager.get(assetManager.img3);
		img4 = game.getAssetManager().manager.get(assetManager.img4);
		
		circle = game.getAssetManager().manager.get(assetManager.circle);
		circle1 = game.getAssetManager().manager.get(assetManager.circle1);

		/// create stage and set it as input processor
		stage = new Stage(new ScreenViewport());

	}

	@Override
	public void show() {
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		skin = new Skin();
		buttonAtlas = game.getAssetManager().manager.get(assetManager.mainMenuButtonPack);
		skin.addRegions(buttonAtlas);

		tempSkin = new Skin();
		tempTextures = game.getAssetManager().manager.get(assetManager.texturesTemp);
		tempSkin.addRegions(tempTextures);

		this.create();
	}

	public void create() {

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		batch = new SpriteBatch();

		font = game.getAssetManager().manager.get(assetManager.PlayTime);
		myText = "";

		LabelStyle counterLabelStyle = new LabelStyle();
		counterLabelStyle.font = font;
		counterLabelStyle.background = skin.getDrawable("counter");

		final Label playersBoxText = new Label("0", counterLabelStyle);
		playersBoxText.setAlignment(Align.center);

		// Previous button

		TextButtonStyle leftArrowStyle = new TextButtonStyle();
		leftArrowStyle.up = skin.getDrawable("leftArrow");
		leftArrowStyle.over = skin.getDrawable("leftArrowPressed");
		leftArrowStyle.font = font;

		TextButton previous = new TextButton("", leftArrowStyle);
		previous.setPosition(100f, 350f);
		previous.setSize(68.0f, 68.0f);

		previous.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (helpCounter > 0) {
					helpCounter -= 1;
					hover.play(game.getSoundVol());
				}
			};
		});
		stage.addActor(previous);

		// next button
		TextButtonStyle rightArrowStyle = new TextButtonStyle();
		rightArrowStyle.up = skin.getDrawable("rightArrow");
		rightArrowStyle.over = skin.getDrawable("rightArrowPressed");
		rightArrowStyle.font = font;

		TextButton next = new TextButton("", rightArrowStyle);
		next.setPosition(1100f, 350f);
		next.setSize(68.0f, 68.0f);
		next.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (helpCounter < 3) {
					helpCounter += 1;

					hover.play(game.getSoundVol());
				}
			};
		});

		stage.addActor(next);

		// exit to main menu button
		TextButtonStyle exitButtonStyle = new TextButtonStyle();
		exitButtonStyle.up = skin.getDrawable("exitButton");
		exitButtonStyle.over = skin.getDrawable("exitPressed");
		exitButtonStyle.checked = skin.getDrawable("exitPressed");
		exitButtonStyle.font = font;

		TextButton exit = new TextButton("", exitButtonStyle);
		exit.setPosition(550f, 0f);
		exit.setSize(206.0f, 61.0f);

		exit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new MainMenu(game));
				hover.play(game.getSoundVol());
			}
		});
		stage.addActor(exit);

		Gdx.input.setInputProcessor(stage);

	}

	@Override
	public void render(float delta) {
		// clear the screen ready for next set of images to be drawn
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.getBatch().begin();
		stage.getBatch().draw(helpBackground, 0, 0);
		stage.getBatch().end();
		
		batch.begin();
		batch.draw(circle,530,110, 35 ,35);
		batch.draw(circle,600,110, 35 ,35);
		batch.draw(circle,670,110, 35 ,35);
		batch.draw(circle,740,110, 35 ,35);
		batch.end();

		if (helpCounter == 0) {

			batch.begin();
			batch.draw(img1, 300, 180, 700, 390);
			batch.draw(circle1,530,110, 35 ,35);
			batch.end();

		}
		if (helpCounter == 1) {

			batch.begin();
			batch.draw(img2, 300, 180, 700, 390);
			batch.draw(circle1,600,110, 35 ,35);
			batch.end();

		}
		if (helpCounter == 2) {

			batch.begin();
			batch.draw(img3, 300, 180, 700, 390);
			batch.draw(circle1,670,110, 35 ,35);
			batch.end();

		}
		if (helpCounter == 3) {

			batch.begin();
			batch.draw(img4, 300, 180, 700, 390);
			batch.draw(circle1,740,110, 35 ,35);
			batch.end();

		}
		
	
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