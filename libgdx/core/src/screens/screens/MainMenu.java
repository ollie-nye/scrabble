package screens.screens;

import assetmanager.assetManager;
import data.Coordinate;
import data.Move;
import data.Tile;
import player.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import scrabble.Board;
import scrabble.Game;
import scrabble.LetterBag;
import screens.ScrabbleLauncher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

public class MainMenu implements Screen {

	private ScrabbleLauncher game;
	private Table playOptions, namingPlayer, tempTable;
	private TextButton play, continues, settings, rules, exit, website, exitMenu, skipToEndScreen;
	private int menuType, playerCounter, aiNumber, playerNumber, screen;
	private Label[] playerLabel = new Label[4];
	private TextField[] playerNameEntry = new TextField[4];
	private Label noPlayers;
	private Stage stage;
	private Sound hover;
	private Skin skin, altSkin;
	private TextureAtlas buttonAtlas, altButtonAtlas;
	// fiddy
	private Sound gunit;
	private Texture background;
	private BitmapFont font;
	private TextButtonStyle exitButtonStyle;
	private Table resetGame, noCurrentGame;

	public MainMenu(ScrabbleLauncher game) {
		this.game = game;
		hover = game.getAssetManager().manager.get(assetManager.mainClick);
		// fiddy
		gunit = game.getAssetManager().manager.get(assetManager.click12);
		background = game.getAssetManager().manager.get(assetManager.mainBackground);
		stage = new Stage(new ScreenViewport());
		font = new BitmapFont();
		menuType = 0;
		playerCounter = 0;
		aiNumber = 0;
		playerNumber = 0;
		font = game.getAssetManager().manager.get(assetManager.PlayTime);
		screen = 0;
		skin = new Skin();
		altSkin = new Skin();

		buttonAtlas = game.getAssetManager().manager.get(assetManager.mainMenuButtonPack);
		skin.addRegions(buttonAtlas);
		altButtonAtlas = game.getAssetManager().manager.get(assetManager.gameButtonPack);
		altSkin.addRegions(altButtonAtlas);

	}

	@Override
	public void show() {

		Gdx.input.setInputProcessor(stage);

		final Skin skin = new Skin();
		TextureAtlas buttonAtlas = game.getAssetManager().manager.get(assetManager.mainMenuButtonPack);
		skin.addRegions(buttonAtlas);

		Skin tempSkin = new Skin();
		TextureAtlas tempTextures = game.getAssetManager().manager.get(assetManager.texturesTemp);
		tempSkin.addRegions(tempTextures);

		TextButtonStyle playButtonStyle = new TextButtonStyle();
		playButtonStyle.up = skin.getDrawable("play");
		playButtonStyle.over = skin.getDrawable("playPressed");
		playButtonStyle.font = font;
		TextButton continues1 = new TextButton("Save", playButtonStyle);
		continues1.setPosition(115, 400f);
		continues1.setSize(65.0f, 65.0f);
		continues1.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				save();
			}
		});
		stage.addActor(continues1);

		TextButton continues2 = new TextButton("Load", playButtonStyle);
		continues2.setPosition(115, 300f);
		continues2.setSize(65.0f, 65.0f);
		continues2.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

			load();
				
			}
		});
		stage.addActor(continues2);

		play = new TextButton("", playButtonStyle);
		play.setPosition(515, 320f);
		play.setSize(254.0f, 65.0f);
		play.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (Game.getPlayers().size() == 0) {
					hover.play(game.getSoundVol());
					menuType = 1;
				} else {
					resetGame.setVisible(true);
				}

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
				game.setScreen(new HelpMenu(game));

			}
		});

		stage.addActor(rules);

		exitButtonStyle = new TextButtonStyle();
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

				stage.addAction(Actions.sequence(Actions.fadeOut(0.5f), Actions.run(new Runnable() {

					@Override
					public void run() {

						hover.play(game.getSoundVol());
						Gdx.app.exit();

					}
				})));
			}
		});
		stage.addActor(exit);

		TextButtonStyle websiteButtonStyle = new TextButtonStyle();
		websiteButtonStyle.up = skin.getDrawable("website");
		websiteButtonStyle.over = skin.getDrawable("websiteHover");
		websiteButtonStyle.font = font;
		website = new TextButton("", websiteButtonStyle);
		website.setPosition(1190f, 0f);
		website.setSize(90.0f, 90.0f);
		website.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

				Gdx.net.openURI("https://scrabbleemphasis.wordpress.com/");

			}
		});
		stage.addActor(website);

		skipToEndScreen = new TextButton("SKIP", websiteButtonStyle);
		skipToEndScreen.setPosition(90f, 0f);
		skipToEndScreen.setSize(90.0f, 90.0f);
		skipToEndScreen.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

				hover.play(game.getSoundVol());
				game.setScreen(new ResultsScreen(game));

			}
		});
		stage.addActor(skipToEndScreen);

		/*
		 * creating the menu that comes up after clicking play
		 */
		// first adding the button styles on
		TextButtonStyle tempButtonStyle = new TextButtonStyle();
		tempButtonStyle.up = tempSkin.getDrawable("purple");
		tempButtonStyle.over = tempSkin.getDrawable("yellow");
		tempButtonStyle.font = font;

		TextButtonStyle leftArrowStyle = new TextButtonStyle();
		leftArrowStyle.up = skin.getDrawable("leftArrow");
		leftArrowStyle.over = skin.getDrawable("leftArrowPressed");
		leftArrowStyle.font = font;

		// sub menu button styles (until // --)
		TextButtonStyle exitMenuStyle = new TextButtonStyle();
		exitMenuStyle.font = font;
		exitMenuStyle.over = skin.getDrawable("homeButtonPressed");
		exitMenuStyle.up = skin.getDrawable("homeButton");
		
		TextButtonStyle startButtonStyle= new TextButtonStyle();
		startButtonStyle.up = skin.getDrawable("quickStart");
		startButtonStyle.over = skin.getDrawable("quickStartPressed");
		startButtonStyle.font = font;
		
		LabelStyle createPlayerStyle = new LabelStyle();
		createPlayerStyle.background = skin.getDrawable("createPlayer");
		createPlayerStyle.font = font;
		
		final TextButtonStyle namingButtonStyle = new TextButtonStyle();
		namingButtonStyle.up = skin.getDrawable("editNames");
		namingButtonStyle.over = skin.getDrawable("editNamesPressed");
		namingButtonStyle.font = font;
		
		LabelStyle boxLabelStyle = new LabelStyle();
		boxLabelStyle.background = skin.getDrawable("creationBox");
		boxLabelStyle.font = font;
		
		LabelStyle addPlayerLabelStyle = new LabelStyle();
		addPlayerLabelStyle.background = skin.getDrawable("addPlayer");
		addPlayerLabelStyle.font = font;

		LabelStyle addBotLabelStyle = new LabelStyle();
		addBotLabelStyle.background = skin.getDrawable("addPlayer");
		addBotLabelStyle.font = font;
		
		LabelStyle labelLineStyle = new LabelStyle();
		labelLineStyle.background = skin.getDrawable("whiteLine");
		labelLineStyle.font = font;
		
		LabelStyle noLabelStyle = new LabelStyle();
		noLabelStyle.font = font;
		
		TextFieldStyle shorterTextBarStyle= new TextFieldStyle();
		shorterTextBarStyle.background = skin.getDrawable("textBarShorter");
		shorterTextBarStyle.font = font;			
		shorterTextBarStyle.messageFont = font;
		shorterTextBarStyle.fontColor = new Color(0.5f, 0.5f, 0.5f, 1f);
		shorterTextBarStyle.focusedBackground = skin.getDrawable("textBarShorterLight");
		
		// --
		/*
		TextButtonStyle  = new TextButtonStyle();
		.up = skin.getDrawable("");
		.over = skin.getDrawable("");
		.font = font;
		*/
		TextButtonStyle rightArrowStyle = new TextButtonStyle();
		rightArrowStyle.up = skin.getDrawable("rightArrow");
		rightArrowStyle.over = skin.getDrawable("rightArrowPressed");
		rightArrowStyle.font = font;
		
	
		TextButtonStyle altButtonStyle = new TextButtonStyle();
		altButtonStyle.up = tempSkin.getDrawable("lightblue");
		altButtonStyle.over = tempSkin.getDrawable("blue");
		altButtonStyle.font = font;

		// setting up a label style and font
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = font;
		labelStyle.background = tempSkin.getDrawable("green");

		LabelStyle altLabelStyle = new LabelStyle();
		altLabelStyle.font = font;
		altLabelStyle.background = tempSkin.getDrawable("blue");

		LabelStyle counterLabelStyle = new LabelStyle();
		counterLabelStyle.font = font;
		counterLabelStyle.background = skin.getDrawable("counter");

		TextFieldStyle textFieldStyle = new TextFieldStyle();
		textFieldStyle.font = font;
		textFieldStyle.background = tempSkin.getDrawable("yellow");
		textFieldStyle.messageFont = font;
		textFieldStyle.fontColor = new Color(0.5f, 0.5f, 0.5f, 1f);
		textFieldStyle.focusedBackground = tempSkin.getDrawable("purple");

		// creating the main table
		playOptions = new Table();
		float gameStartY = 350;
		playOptions.setPosition(640.0f - (gameStartY * (5.0f / 7.0f)), 360.0f - 300.0f);
		
		playOptions.setSize(gameStartY * (12.0f / 7.0f), gameStartY);

		// creating main header and also headers above ai and player
		Label header = new Label("", createPlayerStyle);
		header.setAlignment(Align.center);

		// playersBox is a table to store the number of players selection button
		// collection for non ai
		Table playersBox = new Table();
		

		Label playerHeader = new Label("", addPlayerLabelStyle);
		playerHeader.setAlignment(Align.center);
		
		final Label playersBoxText = new Label("0", counterLabelStyle);
		playersBoxText.setAlignment(Align.center);
		TextButton playersBoxLeftArrow = new TextButton("", leftArrowStyle);
		TextButton playersBoxRightArrow = new TextButton("", rightArrowStyle);

		playersBoxRightArrow.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (playerCounter < 4) {
					playersBoxText.setText(Integer.toString(Integer.parseInt(playersBoxText.getText().toString()) + 1));
					playerCounter += 1;
					System.out.println(playerCounter);
					playerNumber += 1;
				}
			}
		});
		playersBoxLeftArrow.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (playerCounter > 0 && Integer.parseInt(playersBoxText.getText().toString()) != 0) {
					playersBoxText.setText(Integer.toString(Integer.parseInt(playersBoxText.getText().toString()) - 1));
					playerCounter -= 1;
					playerNumber -= 1;
				}
			}
		});

		playersBox.add(playerHeader).colspan(3).padBottom(gameStartY / 28).height(gameStartY / 7)
				.width(gameStartY * (17.0f / 28.0f));
		playersBox.row();
		playersBox.add(playersBoxLeftArrow);
		playersBox.add(playersBoxText).align(Align.center).size(125.0f, 88.0f);
		playersBox.add(playersBoxRightArrow);

		// playersBox is a table to store the number of players selection button
		// collection for ai
		Table aIBox = new Table();
	

		Label aiHeader = new Label("", addBotLabelStyle);
		aiHeader.setAlignment(Align.center);

		final Label aIBoxText = new Label("0", counterLabelStyle);
		aIBoxText.setAlignment(Align.center);
		TextButton aIBoxLeftArrow = new TextButton("", leftArrowStyle);
		// aIBoxLeftArrow.setSize(47.0f, 47.0f);
		TextButton aIBoxRightArrow = new TextButton("", rightArrowStyle);

		aIBoxRightArrow.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (playerCounter < 4) {
					aIBoxText.setText(Integer.toString(Integer.parseInt(aIBoxText.getText().toString()) + 1));
					aiNumber += 1;
					playerCounter += 1;
				}
			}
		});
		aIBoxLeftArrow.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (playerCounter > 0 && Integer.parseInt(aIBoxText.getText().toString()) != 0) {
					aIBoxText.setText(Integer.toString(Integer.parseInt(aIBoxText.getText().toString()) - 1));
					aiNumber -= 1;
					playerCounter -= 1;
				}
			}
		});

		aIBox.add(aiHeader).colspan(3).padBottom(gameStartY / 28).height(gameStartY / 7)
				.width(gameStartY * (17.0f / 28.0f));
		aIBox.row();
		// aIBox.add(aIBoxLeftArrow).size(gameStartY * (6.5f / 28.0f),
		// gameStartY * (4.5f / 14.0f));
		aIBox.add(aIBoxLeftArrow);
		aIBox.add(aIBoxText).align(Align.center).size(125.0f, 88.0f);
		aIBox.add(aIBoxRightArrow);
		;

		Label line = new Label ("", labelLineStyle);
		
		// adding these to one table
		tempTable = new Table();
		tempTable.setBackground(skin.getDrawable("creationBoxWithLine"));
		tempTable.add(playersBox).size(gameStartY / 28.0f * 18.0f, gameStartY / 14.0f * 6.0f).padLeft(gameStartY / 14)
				.padRight(gameStartY / 56);
	//	tempTable.add(line).width(5.0f).expand().fill();
		
		
		tempTable.add(aIBox).size(gameStartY / 28.0f * 18.0f, gameStartY / 14.0f * 6.0f).padRight(gameStartY / 14)
				.padLeft(gameStartY / 56);

		namingPlayer = new Table();
		namingPlayer.setBackground(skin.getDrawable("creationBox"));

		// namingPlayer.add(p1);
		namingPlayer.setSize(gameStartY * (9.0f / 7.0f), gameStartY * (6.0f / 14.0f));

		namingPlayer.setVisible(false);

		playerLabel[0] = new Label("Player 1", noLabelStyle);
		playerLabel[0].setAlignment(Align.center);
		playerNameEntry[0] = new TextField("", shorterTextBarStyle);
		playerNameEntry[0].setAlignment(Align.center);

		playerLabel[1] = new Label("Player 2", noLabelStyle);
		playerLabel[1].setAlignment(Align.center);
		playerNameEntry[1] = new TextField("", shorterTextBarStyle);
		playerNameEntry[1].setAlignment(Align.center);

		playerLabel[2] = new Label("Player 3", noLabelStyle);
		playerNameEntry[2] = new TextField("", shorterTextBarStyle);
		playerLabel[2].setAlignment(Align.center);
		playerNameEntry[2].setAlignment(Align.center);

		playerLabel[3] = new Label("Player 4", noLabelStyle);
		playerNameEntry[3] = new TextField("", shorterTextBarStyle);
		playerLabel[3].setAlignment(Align.center);
		playerNameEntry[3].setAlignment(Align.center);

		namingPlayer.add(playerLabel[0]).width(gameStartY / 14 * 8.75f).padRight(gameStartY / 56.0f);

		namingPlayer.add(playerNameEntry[0]).height(30.0f).width(gameStartY / 14 * 8.75f).padLeft(gameStartY / 56.0f);
		namingPlayer.row();
		namingPlayer.add(playerLabel[1]).width(gameStartY / 14 * 8.75f).padRight(gameStartY / 56.0f);
		namingPlayer.add(playerNameEntry[1]).height(30.0f).width(gameStartY / 14 * 8.75f).padLeft(gameStartY / 56.0f);
		namingPlayer.row();
		namingPlayer.add(playerLabel[2]).width(gameStartY / 14 * 8.75f).padRight(gameStartY / 56.0f);
		namingPlayer.add(playerNameEntry[2]).height(30.0f).width(gameStartY / 14 * 8.75f).padLeft(gameStartY / 56.0f);
		namingPlayer.row();
		namingPlayer.add(playerLabel[3]).width(gameStartY / 14 * 8.75f).padRight(gameStartY / 56.0f);
		;
		namingPlayer.add(playerNameEntry[3]).height(30.0f).width(gameStartY / 14 * 8.75f).padLeft(gameStartY / 56.0f);
		namingPlayer.setWidth(gameStartY / 14.0f * 18.0f);

		noPlayers = new Label("Add Some Players First", boxLabelStyle);
		noPlayers.setAlignment(Align.center);
		noPlayers.setVisible(false);

		Stack stack = new Stack();
		stack.add(tempTable);
		stack.add(namingPlayer);
		stack.add(noPlayers);

		// start button, at bottom of box, starts the game
		TextButton start = new TextButton("", startButtonStyle);
		start.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (playerCounter > 1) {
					setPlayerArray();
					for (int i = 0; i < playerNumber; i++) {
						Game.addPlayer(playerNameEntry[i].getText(), 1);
					}
					for (int i = 0; i < aiNumber; i++) {
						Game.addPlayer("Bot " + i, 2);
					}
					Game.start();

					// fiddy
					gunit.play(game.getSoundVol());

					System.out.println(setPlayerArray().toString());
					game.setScreen(new GameScreen(game, setPlayerArray()));
				}
			}
		});

		// naming button, at bottom of box, changes screen to name player screen
		final TextButton naming = new TextButton("", namingButtonStyle);
		naming.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

				if (screen == 0) {
					namingButtonStyle.up = skin.getDrawable("editNames");
					namingButtonStyle.over = skin.getDrawable("editNamesPressed");
					screen = 1;
				} else if (screen == 1) {
					namingButtonStyle.up = skin.getDrawable("editPlayers");
					namingButtonStyle.over = skin.getDrawable("editPlayersPressed");
					screen = 0;
				}
			}
		});

		// creating the main table
		playOptions.add(header).colspan(2).pad(gameStartY / 14, gameStartY / 14, gameStartY / 14, gameStartY / 14)
				.size(gameStartY / 14 * 12, gameStartY / 5);
		playOptions.row();
		playOptions.add(stack).colspan(2).maxWidth(500.0f);
		playOptions.row();
		playOptions.add(naming).pad(gameStartY / 14, gameStartY / 14, gameStartY / 14, gameStartY / 56)
				.size(gameStartY / 28.0f * 18.0f, gameStartY / 7);
		playOptions.add(start).pad(gameStartY / 14, gameStartY / 56, gameStartY / 14, gameStartY / 14)
				.size(gameStartY / 28.0f * 18.0f, gameStartY / 7);

		stage.addActor(playOptions);

		exitMenu = new TextButton("", exitMenuStyle);
		exitMenu.setPosition(847.0f, 335.0f);

		exitMenu.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

				for (TextField textField : playerNameEntry)
					textField.setText("");
				setMainMenuVisible();
				setGameMenuInvisible();
				menuType = 0;
				screen = 0;
				playerCounter = 0;
				playerNumber = 0;
				aIBoxText.setText("0");
				playersBoxText.setText("0");

			}
		});
		exitMenu.setVisible(false);
		stage.addActor(exitMenu);

		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = altSkin.getDrawable("plainButton");
		textButtonStyle.over = altSkin.getDrawable("plainButtonPressed");
		textButtonStyle.font = font;
		LabelStyle llabelStyle = new LabelStyle();
		llabelStyle.font = font;
		resetGame = continues(textButtonStyle, llabelStyle, skin.getDrawable("creationBox"),
				"Are you sure you want to start a new game instead of continuing?");
		resetGame.setVisible(false);
		stage.addActor(resetGame);
		noCurrentGame = continues(textButtonStyle, llabelStyle, skin.getDrawable("creationBox"),
				"You have no game in progress. Start a new one?");
		noCurrentGame.setVisible(false);
		// stage.addActor(noCurrentGame);

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

		/*
		 * if (continues.isOver()) { continues.setSize(274f, 85f);
		 * continues.setPosition(507f, 392f); } else {
		 * continues.setPosition(515, 400f); continues.setSize(254.0f, 65.0f); }
		 */

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

		} else if (menuType == 1) {
			setGameMenuVisible();
			setMainMenuInvisible();
		}
		if (screen == 0) {
			tempTable.setVisible(true);
			namingPlayer.setVisible(false);
			noPlayers.setVisible(false);

		} else {
			tempTable.setVisible(false);
			namingPlayer.setVisible(true);
			noPlayers.setVisible(false);

		}
		switch (playerCounter) {
		case 0:
			playerNameEntry[0].setVisible(false);
			playerNameEntry[1].setVisible(false);
			playerNameEntry[2].setVisible(false);
			playerNameEntry[3].setVisible(false);
			playerLabel[0].setVisible(false);
			playerLabel[1].setVisible(false);
			playerLabel[2].setVisible(false);
			playerLabel[3].setVisible(false);
			if (screen == 1) {
				noPlayers.setVisible(true);
			}
			break;
		case 1:
			playerNameEntry[0].setVisible(true);
			playerNameEntry[1].setVisible(false);
			playerNameEntry[2].setVisible(false);
			playerNameEntry[3].setVisible(false);
			playerLabel[0].setVisible(true);
			playerLabel[1].setVisible(false);
			playerLabel[2].setVisible(false);
			playerLabel[3].setVisible(false);
			noPlayers.setVisible(false);
			break;

		case 2:
			playerNameEntry[0].setVisible(true);
			playerNameEntry[1].setVisible(true);
			playerNameEntry[2].setVisible(false);
			playerNameEntry[3].setVisible(false);
			playerLabel[0].setVisible(true);
			playerLabel[1].setVisible(true);
			playerLabel[2].setVisible(false);
			playerLabel[3].setVisible(false);
			noPlayers.setVisible(false);
			break;
		case 3:
			playerNameEntry[0].setVisible(true);
			playerNameEntry[1].setVisible(true);
			playerNameEntry[2].setVisible(true);
			playerNameEntry[3].setVisible(false);
			playerLabel[0].setVisible(true);
			playerLabel[1].setVisible(true);
			playerLabel[2].setVisible(true);
			playerLabel[3].setVisible(false);
			noPlayers.setVisible(false);
			break;
		case 4:
			playerNameEntry[0].setVisible(true);
			playerNameEntry[1].setVisible(true);
			playerNameEntry[2].setVisible(true);
			playerNameEntry[3].setVisible(true);
			playerLabel[0].setVisible(true);
			playerLabel[1].setVisible(true);
			playerLabel[2].setVisible(true);
			playerLabel[3].setVisible(true);
			noPlayers.setVisible(false);
			break;
		default:
			playerNameEntry[0].setVisible(false);
			playerNameEntry[1].setVisible(false);
			playerNameEntry[2].setVisible(false);
			playerNameEntry[3].setVisible(false);
			playerLabel[0].setVisible(false);
			playerLabel[1].setVisible(false);
			playerLabel[2].setVisible(false);
			playerLabel[3].setVisible(false);
			noPlayers.setVisible(false);
			break;
		}
		switch (playerNumber) {
		case 0:
			playerLabel[0].setText("AI P1");
			playerLabel[1].setText("AI P2");
			playerLabel[2].setText("AI P3");
			playerLabel[3].setText("AI P4");
			break;
		case 1:
			playerLabel[0].setText("Human P1");
			playerLabel[1].setText("AI P1");
			playerLabel[2].setText("AI P2");
			playerLabel[3].setText("AI P3");
			break;
		case 2:
			playerLabel[0].setText("Human P1");
			playerLabel[1].setText("Human P2");
			playerLabel[2].setText("AI P1");
			playerLabel[3].setText("AI P2");
			break;
		case 3:
			playerLabel[0].setText("Human P1");
			playerLabel[1].setText("Human P2");
			playerLabel[2].setText("Human P3");
			playerLabel[3].setText("AI P1");
			break;
		case 4:
			playerLabel[0].setText("Human P1");
			playerLabel[1].setText("Human P2");
			playerLabel[2].setText("Human P3");
			playerLabel[3].setText("Human P4");
			break;

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
		exitMenu.setVisible(true);
	}

	private void setGameMenuInvisible() {
		playOptions.setVisible(false);
		exitMenu.setVisible(false);
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

	private Queue<String> setPlayerArray() {
		Queue<String> names = new Queue<String>();
		for (TextField textField : playerNameEntry) {
			names.addLast(textField.getText());
		}
		return names;
	};

	public Table resetGame(TextButtonStyle tempStyle, LabelStyle labelStyle, Drawable drawable, String labelText) {

		Table table = new Table();
		table.setWidth(450.0f);

		Label label = new Label(labelText, labelStyle);
		label.setWrap(true);
		label.setAlignment(Align.center);

		TextButton yes = new TextButton("yes", tempStyle);
		yes.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Game.reset();
				resetGame.setVisible(false);
				menuType = 1;

			};
		});
		TextButton no = new TextButton("no", tempStyle);
		no.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				resetGame.setVisible(false);
			};
		});

		table.add(label).pad(10.0f, 30.0f, 10.0f, 30.0f).colspan(2).fill().expand();
		table.row();
		table.add(yes).pad(10.0f).size(130.0f, 40.0f);
		table.add(no).pad(10.0f).size(130.0f, 40.0f);

		table.setBackground(drawable);
		table.pack();
		table.setHeight(table.getHeight() - 60.0f);
		table.setPosition((1280.0f - table.getWidth()) * 0.5f, (720.0f - table.getHeight()) * 0.5f);

		return table;
	}

	public Table continues(TextButtonStyle tempStyle, LabelStyle labelStyle, Drawable drawable, String labelText) {

		final Table table = new Table();
		table.setWidth(450.0f);

		Label label = new Label(labelText, labelStyle);
		label.setWrap(true);
		label.setAlignment(Align.center);

		TextButton yes = new TextButton("Continue", tempStyle);
		yes.getLabel().setColor(Color.BLACK);
		yes.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new GameScreen(game, null));
				noCurrentGame.setVisible(false);

			};
		});
		TextButton no = new TextButton("New Game", tempStyle);
		no.getLabel().setColor(Color.BLACK);
		no.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				menuType = 1;
				Game.reset();
				table.setVisible(false);
				noCurrentGame.setVisible(false);
			};
		});

		// little x button for do you want to continue or start new game pop up
		TextButton cancel = new TextButton("Cancel", tempStyle);
		cancel.getLabel().setColor(Color.BLACK);
		cancel.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				table.setVisible(false);
				noCurrentGame.setVisible(false);
			};
		});

		table.add(label).pad(10.0f, 30.0f, 10.0f, 30.0f).colspan(3).fill().expand();
		table.row();
		table.add(yes).pad(10.0f).size(180.0f, 40.0f);
		table.add(no).pad(10.0f).size(180.0f, 40.0f);
		table.add(cancel).pad(10.0f).size(180.0f, 40.0f);

		table.setBackground(drawable);
		table.pack();
		table.setHeight(table.getHeight() - 60.0f);
		table.setPosition((1280.0f - table.getWidth()) * 0.5f, (720.0f - table.getHeight()) * 0.5f);

		return table;
	}
	
	private void load(){
		ArrayList<Player> players = null;
		ArrayBlockingQueue<Player> playersOrder = null;
		Player currentPlayer = null;
		Tile[][] letters;
		LetterBag letterBag = null;
		ArrayList<Move> moveList = null;
		Game gamsu = new Game();
		try {
			FileInputStream fileIn = new FileInputStream("save.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			players = (ArrayList) in.readObject();
			playersOrder = (ArrayBlockingQueue) in.readObject();
			currentPlayer = (Player) in.readObject();
			letters = (Tile[][]) in.readObject();
			letterBag = (LetterBag) in.readObject();
			moveList = (ArrayList) in.readObject();
			
			//gamsu = (Game) in.readObject();
			in.close();
			fileIn.close();
			
			for (int i = 0; i<players.size(); i++){
				
			}
			Board.getInstance().setBoard(letters);
			Game.getLetterBag().setList(letterBag.getList());
			System.out.println(letterBag.pick().getContent() + letterBag.pick().getContent()+ letterBag.pick().getContent()+ letterBag.pick().getContent());
			for (int i=0; i<players.size(); i++){
				Game.addPlayerCheatForSaves(players.get(i));
			}
			for (int i=0; i<players.size(); i++){
				Game.addPlayerOrderCheatForSaves(playersOrder.poll());
			}
			for (int i=0; i<moveList.size(); i++){
				Game.addMove(moveList.get(i));
			}
			Game.setCurrentPlayer(currentPlayer);
			
			
			game.setScreen(new GameScreen(game, setPlayerArray()));
			
			
			
		} catch (IOException i) {
			i.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("Employee class not found");
			c.printStackTrace();
			return;
		}
	}
	private void save(){
		try {
			//Game gamsu = new Game();
			
			FileOutputStream fileOut =

					new FileOutputStream(new File("save.ser"));
			ObjectOutputStream out = new ObjectOutputStream(fileOut);

			
			out.writeObject(Game.getPlayers());
			out.writeObject(Game.getPlayersOrder());
			out.writeObject(Game.getCurrentPlayer());
			out.writeObject(Board.getInstance().returnBoard());
			out.writeObject(Game.getLetterBag());
			out.writeObject(Game.getMoveList());
			
			
			//out.writeObject(gamsu);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved in save.ser");
		} catch (IOException i) {
			i.printStackTrace();
		}
		
	}
}