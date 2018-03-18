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
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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
	private TextureAtlas buttonAtlass;

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
		buttonAtlas = game.getAssetManager().manager.get(assetManager.gameButtonPack);
		buttonAtlass = game.getAssetManager().manager.get(assetManager.mainMenuButtonPack);
		skin.addRegions(buttonAtlas);
		skin.addRegions(buttonAtlass);

		

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
		previous.setPosition(209f, 350f);
		previous.setSize(47.0f, 47.0f);

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
		next.setPosition(1050f, 350f);
		next.setSize(47.0f, 47.0f);
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
		exitButtonStyle.up = skin.getDrawable("mainMenuButton");
		exitButtonStyle.over = skin.getDrawable("mainMenuButtonPressed");
		exitButtonStyle.checked = skin.getDrawable("mainMenuButtonPressed");
		exitButtonStyle.font = font;

		TextButton exit = new TextButton("", exitButtonStyle);
		exit.setPosition(550f, 70f);
		exit.setSize(208.0f, 64.0f);

		exit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

				

						hover.play(game.getSoundVol());
						game.setScreen(new MainMenu(game));

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
		batch.draw(circle,530,160, 45 ,40);
		batch.draw(circle,600,160, 45 ,40);
		batch.draw(circle,670,160, 45 ,40);
		batch.draw(circle,740,160, 45 ,40);
		batch.end();

		if (helpCounter == 0) {

			batch.begin();
			batch.draw(img1, 342, 230, 631, 298);
			batch.draw(circle1,520,155, 59 ,55);
			batch.end();

		}
		if (helpCounter == 1) {

			batch.begin();
			batch.draw(img2, 342, 230, 631, 298);
			batch.draw(circle1,590,155, 59 ,55);
			batch.end();

		}
		if (helpCounter == 2) {

			batch.begin();
			batch.draw(img3, 300, 180, 700, 390);
			batch.draw(circle1,660,155, 59 ,55);
			batch.end();

		}
		if (helpCounter == 3) {

			batch.begin();
			batch.draw(img4, 300, 180, 700, 390);
			batch.draw(circle1,730,155, 59 ,55);
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