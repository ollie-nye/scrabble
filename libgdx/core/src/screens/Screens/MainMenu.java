package screens.Screens;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.scrabble.game.scrabbleMain;

import assetmanager.assetManager;
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
	private Skin tempSkin;
	private TextureAtlas buttonAtlas;
	private TextureAtlas tempTextures;
	private Sound hover;
	private Texture background;
	private BitmapFont font;
	private HashMap<String, TextButton> playOptionsArray;
	private HashMap<String, Label> playOptionsHeaderArray;
	private Table playOptions;
	private int menuType;
	private int playerCounter;

	private final float gameStartX = 500;
	private final float gameStartY = 350;

	public MainMenu(ScrabbleLauncher game) {

		this.game = game;
		hover = game.getAssetManager().manager.get(assetManager.mainClick);
		background = game.getAssetManager().manager.get(assetManager.mainBackground);
		stage = new Stage(new ScreenViewport());
		font = new BitmapFont();
		playOptionsArray = new HashMap<String, TextButton>();
		playOptionsHeaderArray = new HashMap<String, Label>();
		menuType = 0;
		playerCounter = 0;
		font = game.getAssetManager().manager.get(assetManager.PlayTime);
	}

	@Override
	public void show() {

		Gdx.input.setInputProcessor(stage);

		skin = new Skin();
		buttonAtlas = game.getAssetManager().manager.get(assetManager.mainMenuButtonPack);
		skin.addRegions(buttonAtlas);

		tempSkin = new Skin();
		tempTextures = game.getAssetManager().manager.get(assetManager.texturesTemp);
		tempSkin.addRegions(tempTextures);

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

				hover.play(game.getSoundVol());
				menuType = 1;

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

		/*
		 * creating the menu that comes up after clicking play
		 */
		// first adding the button styles on
		TextButtonStyle tempButtonStyle = new TextButtonStyle();
		tempButtonStyle.up = tempSkin.getDrawable("purple");
		tempButtonStyle.over = tempSkin.getDrawable("yellow");
		tempButtonStyle.font = font;

		TextButtonStyle altButtonStyle = new TextButtonStyle();
		altButtonStyle.up = tempSkin.getDrawable("lightblue");
		altButtonStyle.over = tempSkin.getDrawable("blue");
		altButtonStyle.font = font;

		// setting up a label style and font
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = font;
		labelStyle.background = tempSkin.getDrawable("green");

		// creating the main table
		playOptions = new Table();
		playOptions.setPosition(640.0f - (gameStartY * (5.0f / 7.0f)), 360.0f - 300.0f);
		playOptions.setBackground(tempSkin.getDrawable("orange"));
		playOptions.setSize(gameStartY * (10.0f / 7.0f), gameStartY);

		// creating main header and also headers above ai and player
		Label header = new Label("Select Players", labelStyle);
		header.setAlignment(Align.center);

		// playersBox is a table to store the number of players selection button
		// collection for non ai
		Table playersBox = new Table();
		playersBox.setBackground(tempSkin.getDrawable("lightblue"));

		Label playerHeader = new Label("Add Players", labelStyle);
		playerHeader.setAlignment(Align.center);

		Label playersBoxText = new Label("0", labelStyle);
		playersBoxText.setAlignment(Align.center);
		TextButton playersBoxLeftArrow = new TextButton("", tempButtonStyle);
		TextButton playersBoxRightArrow = new TextButton("", tempButtonStyle);

		playersBoxRightArrow.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (playerCounter < 4) {
					playersBoxText.setText(Integer.toString(Integer.parseInt(playersBoxText.getText().toString()) + 1));
					playerCounter += 1;
				}
			}
		});
		playersBoxLeftArrow.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (playerCounter > 0 && Integer.parseInt(playersBoxText.getText().toString()) != 0) {
					playersBoxText.setText(Integer.toString(Integer.parseInt(playersBoxText.getText().toString()) - 1));
					playerCounter -= 1;
				}
			}
		});
		playersBox.add(playerHeader).colspan(3).padBottom(gameStartY / 28).height(gameStartY / 14)
				.width(gameStartY * (17.0f / 28.0f));
		playersBox.row();
		playersBox.add(playersBoxLeftArrow).size(gameStartY * (6.5f / 28.0f), gameStartY * (4.5f / 14.0f));
		;
		playersBox.add(playersBoxText).align(Align.center).height(gameStartY / 14.0f * 4.5f).width(gameStartY / 7.0f);
		playersBox.add(playersBoxRightArrow).size(gameStartY * (6.5f / 28.0f), gameStartY * (4.5f / 14.0f));
		;

		// playersBox is a table to store the number of players selection button
		// collection for ai
		Table aIBox = new Table();
		aIBox.setBackground(tempSkin.getDrawable("lightblue"));

		Label aiHeader = new Label("Add CPU", labelStyle);
		aiHeader.setAlignment(Align.center);

		Label aIBoxText = new Label("0", labelStyle);
		aIBoxText.setAlignment(Align.center);
		TextButton aIBoxLeftArrow = new TextButton("", tempButtonStyle);
		TextButton aIBoxRightArrow = new TextButton("", tempButtonStyle);

		aIBoxRightArrow.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (playerCounter < 4) {
					aIBoxText.setText(Integer.toString(Integer.parseInt(aIBoxText.getText().toString()) + 1));
					playerCounter += 1;
				}
			}
		});
		aIBoxLeftArrow.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (playerCounter > 0 && Integer.parseInt(aIBoxText.getText().toString()) != 0) {
					aIBoxText.setText(Integer.toString(Integer.parseInt(aIBoxText.getText().toString()) - 1));
					playerCounter -= 1;
				}
			}
		});
		aIBox.add(aiHeader).colspan(3).padBottom(gameStartY / 28).height(gameStartY / 14)
				.width(gameStartY * (17.0f / 28.0f));
		aIBox.row();
		aIBox.add(aIBoxLeftArrow).size(gameStartY * (6.5f / 28.0f), gameStartY * (4.5f / 14.0f));
		aIBox.add(aIBoxText).align(Align.center).height(gameStartY / 14.0f * 4.5f).width(gameStartY / 7.0f);
		aIBox.add(aIBoxRightArrow).size(gameStartY * (6.5f / 28.0f), gameStartY * (4.5f / 14.0f));
		;

		// start button, at bottom of box, starts the game
		TextButton start = new TextButton("Quickstart", altButtonStyle);
		start.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new scrabbleMain(game, 0, 0));
			}
		});
		
		// naming button, at bottom of box, changes screen to name player screen
		TextButton naming = new TextButton("Name Players", altButtonStyle);
		start.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {		
			}
		});

		// creating the main table
		playOptions.add(header).colspan(2).size(gameStartY / 14 * 12, gameStartY / 7)
				.pad(gameStartY / 14, gameStartY / 14, gameStartY / 14, gameStartY / 14)
				.size(gameStartY / 14 * 12, gameStartY / 7);
		playOptions.row();
		playOptions.add(playersBox).size(gameStartY / 28.0f * 18.0f, gameStartY / 14.0f * 6.0f).padLeft(gameStartY / 14).padRight(gameStartY / 56);
		playOptions.add(aIBox).size(gameStartY / 28.0f * 18.0f, gameStartY / 14.0f * 6.0f).padRight(gameStartY / 14).padLeft(gameStartY / 56);
		playOptions.row();
		playOptions.add(naming).pad(gameStartY / 14, gameStartY / 14, gameStartY / 14, gameStartY / 56)
				.size(gameStartY / 28.0f * 18.0f, gameStartY / 7);
		playOptions.add(start).pad(gameStartY / 14, gameStartY / 56, gameStartY / 14, gameStartY / 14)
				.size(gameStartY / 28.0f * 18.0f, gameStartY / 7);

		stage.addActor(playOptions);

	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// for animation of the play button
		if (play.isOver()) {
			play.setSize(274f, 85f);
			play.setPosition(507f, 322f);
		} else {
			play.setPosition(515, 330f);
			play.setSize(254.0f, 65.0f);
		}

		// for animation of the settings button
		if (settings.isOver()) {
			settings.setSize(331f, 91f);
			settings.setPosition(475, 232f);
		} else {
			settings.setPosition(480, 240f);
			settings.setSize(320.0f, 80.0f);
		}

		// for animation of the rules button
		if (rules.isOver()) {
			rules.setSize(264f, 85f);
			rules.setPosition(505f, 153f);
		} else {
			rules.setPosition(520f, 160f);
			rules.setSize(244.0f, 69.0f);
		}

		// for animation of the exit button
		if (exit.isOver()) {
			exit.setSize(270, 80f);
			exit.setPosition(510f, 79f);
		} else {
			exit.setPosition(515, 80f);
			exit.setSize(260.0f, 75.0f);
		}

		if (menuType == 0) {
			setGameMenuInvisible();
			setMainMenuVisible();

		}
		if (menuType == 1) {
			setGameMenuVisible();
			setMainMenuInvisible();
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

	private void setGameMenuVisible() {
		playOptions.setVisible(true);
	}

	private void setGameMenuInvisible() {
		playOptions.setVisible(false);
	}

	private void setMainMenuVisible() {
		settings.setVisible(true);
		exit.setVisible(true);
		play.setVisible(true);
		rules.setVisible(true);
	}

	private void setMainMenuInvisible() {
		settings.setVisible(false);
		exit.setVisible(false);
		play.setVisible(false);
		rules.setVisible(false);
	}
	

}
