package screens.screens;

import assetmanager.assetManager;

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

import player.AIPlayer;
import scrabble.Game;
import screens.ScrabbleLauncher;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class MainMenu implements Screen {

	private ScrabbleLauncher game;
	private Table playOptions, namingPlayer, tempTable;
	private TextButton play, continues, settings, rules, exit, website, exitMenu, skipToEndScreen;
	private int menuType, playerCounter, aiNumber, playerNumber, screen;
	private TextButton[] playerLabel = new TextButton[4];
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
	private String[] aiDifficulties;
	private String[] playerText;

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

	private void setDifficultyArray() {
		aiDifficulties = new String[2];
		aiDifficulties[0] = "Normal Bot";
		aiDifficulties[1] = "Hard Bot";
		playerText = new String[4];
		playerText[0] = "Normal Bot";
		playerText[1] = "Normal Bot";
		playerText[2] = "Normal Bot";
		playerText[3] = "Normal Bot";

	}

	@Override
	public void show() {

		Gdx.input.setInputProcessor(stage);

		setDifficultyArray();

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

				stage.addAction(Actions.sequence(Actions.fadeOut(0.15f), Actions.run(new Runnable() {

					@Override
					public void run() {

						hover.play(game.getSoundVol());
						if (Game.getNumberOfPlayers() > 0) {
							Game.save();
						}
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

		TextButtonStyle startButtonStyle = new TextButtonStyle();
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
		addBotLabelStyle.background = skin.getDrawable("addBot");
		addBotLabelStyle.font = font;

		LabelStyle labelLineStyle = new LabelStyle();
		labelLineStyle.background = skin.getDrawable("whiteLine");
		labelLineStyle.font = font;

		LabelStyle noLabelStyle = new LabelStyle();
		noLabelStyle.font = font;

		TextButtonStyle noButtonStyle = new TextButtonStyle();
		noButtonStyle.font = font;

		TextFieldStyle shorterTextBarStyle = new TextFieldStyle();
		shorterTextBarStyle.background = skin.getDrawable("textBarShorter");
		shorterTextBarStyle.font = font;
		shorterTextBarStyle.messageFont = font;
		shorterTextBarStyle.fontColor = new Color(0.5f, 0.5f, 0.5f, 1f);
		shorterTextBarStyle.focusedBackground = skin.getDrawable("textBarShorterLight");

		// --
		/*
		 * TextButtonStyle = new TextButtonStyle(); .up = skin.getDrawable("");
		 * .over = skin.getDrawable(""); .font = font;
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
		playOptions.setPosition(590.0f - (gameStartY * (5.0f / 7.0f)), 360.0f - 300.0f);

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
				.width(gameStartY * (17.0f / 28.0f)).align(Align.center).padRight(12.0f);
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
				.width(gameStartY * (17.0f / 28.0f)).align(Align.center);
		aIBox.row();
		// aIBox.add(aIBoxLeftArrow).size(gameStartY * (6.5f / 28.0f),
		// gameStartY * (4.5f / 14.0f));
		aIBox.add(aIBoxLeftArrow);
		aIBox.add(aIBoxText).align(Align.center).size(125.0f, 88.0f);
		aIBox.add(aIBoxRightArrow);
		;

		Label line = new Label("", labelLineStyle);

		// adding these to one table
		tempTable = new Table();
		tempTable.setBackground(skin.getDrawable("creationBoxWithLine"));
		tempTable.add(playersBox).height(gameStartY / 14.0f * 6.0f).align(Align.center).padRight(15.0f).uniform();
		// tempTable.add(line).width(5.0f).expand().fill();

		tempTable.add(aIBox).height(gameStartY / 14.0f * 6.0f).align(Align.center).padLeft(15.0f).uniform();

		namingPlayer = new Table();
		namingPlayer.setBackground(skin.getDrawable("creationBox"));

		// namingPlayer.add(p1);
		namingPlayer.setSize(gameStartY * (9.0f / 7.0f), gameStartY * (6.0f / 14.0f));

		namingPlayer.setVisible(false);

		playerLabel[0] = new TextButton("", noButtonStyle);
		playerLabel[0].align(Align.center);
		playerLabel[0].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (playerLabel[0].getText().charAt(0) != 'P') {
					for (int i = 0; i < aiDifficulties.length; i++) {
						if (playerLabel[0].getText().toString().equals(aiDifficulties[i])) {
							if (i < aiDifficulties.length - 1) {
								playerText[0] = (aiDifficulties[i + 1]);
								break;
							} else {
								playerText[0] = (aiDifficulties[0]);
								break;
							}
						}
					}
				}

			}
		});

		playerNameEntry[0] = new TextField("", shorterTextBarStyle);
		playerNameEntry[0].setAlignment(Align.center);

		playerLabel[1] = new TextButton("Player 2", noButtonStyle);
		playerLabel[1].align(Align.center);
		playerLabel[1].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (playerLabel[1].getText().charAt(0) != 'P') {
					for (int i = 0; i < aiDifficulties.length; i++) {
						if (playerLabel[1].getText().toString().equals(aiDifficulties[i])) {
							if (i < aiDifficulties.length - 1) {
								playerText[1] = (aiDifficulties[i + 1]);
								break;
							} else {
								playerText[1] = (aiDifficulties[0]);
								break;
							}
						}
					}
				}

			}
		});
		playerNameEntry[1] = new TextField("", shorterTextBarStyle);
		playerNameEntry[1].setAlignment(Align.center);

		playerLabel[2] = new TextButton("Player 3", noButtonStyle);
		playerNameEntry[2] = new TextField("", shorterTextBarStyle);
		playerLabel[2].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (playerLabel[2].getText().charAt(0) != 'P') {
					for (int i = 0; i < aiDifficulties.length; i++) {
						if (playerLabel[2].getText().toString().equals(aiDifficulties[i])) {
							if (i < aiDifficulties.length - 1) {
								playerText[2] = (aiDifficulties[i + 1]);
								break;
							} else {
								playerText[2] = (aiDifficulties[0]);
								break;
							}
						}
					}
				}

			}
		});
		playerLabel[2].align(Align.center);
		playerNameEntry[2].setAlignment(Align.center);

		playerLabel[3] = new TextButton("Player 4", noButtonStyle);
		playerNameEntry[3] = new TextField("", shorterTextBarStyle);
		playerLabel[3].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (playerLabel[3].getText().charAt(0) != 'P') {
					for (int i = 0; i < aiDifficulties.length; i++) {
						if (playerLabel[3].getText().toString().equals(aiDifficulties[i])) {
							if (i < aiDifficulties.length - 1) {
								playerText[3] = (aiDifficulties[i + 1]);
								break;
							} else {
								playerText[3] = (aiDifficulties[0]);
								break;
							}
						}
					}
				}

			}
		});
		playerLabel[3].align(Align.center);
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
						Game.addPlayer(playerNameEntry[i].getText(), 2);
					}
					Game.start();

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
					namingButtonStyle.up = skin.getDrawable("editPlayers");
					namingButtonStyle.over = skin.getDrawable("editPlayersPressed");
					screen = 1;
				} else if (screen == 1) {
					namingButtonStyle.up = skin.getDrawable("editNames");
					namingButtonStyle.over = skin.getDrawable("editNamesPressed");
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
		playOptions.add(naming).height(gameStartY / 7).padRight(15.0f).uniform();
		playOptions.add(start).height(gameStartY / 7).padRight(15.0f).uniform();

		stage.addActor(playOptions);

		exitMenu = new TextButton("", exitMenuStyle);
		exitMenu.setPosition(407.0f, 317.0f);

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

		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("save.ser"));
			try {
				if (br.readLine() != null) {
					Game.load();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter pw;
		try {
			pw = new PrintWriter("save.ser");
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			playerLabel[0].setText(playerText[0]);
			playerLabel[1].setText(playerText[1]);
			playerLabel[2].setText(playerText[2]);
			playerLabel[3].setText(playerText[3]);
			break;
		case 1:
			playerLabel[0].setText("Human P1");
			playerLabel[1].setText(playerText[1]);
			playerLabel[2].setText(playerText[2]);
			playerLabel[3].setText(playerText[3]);
			break;
		case 2:
			playerLabel[0].setText("Human P1");
			playerLabel[1].setText("Human P2");
			playerLabel[2].setText(playerText[2]);
			playerLabel[3].setText(playerText[3]);
			break;
		case 3:
			playerLabel[0].setText("Human P1");
			playerLabel[1].setText("Human P2");
			playerLabel[2].setText("Human P3");
			playerLabel[3].setText(playerText[3]);
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
                Game.getTimer().startTimer();
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
}