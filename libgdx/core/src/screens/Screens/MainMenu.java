package screens.Screens;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
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
	private int aiNumber;
	private int playerNumber;
	private int screen;
	private ArrayList<String> nameList;
	private final float gameStartY = 350;

	TextField txtUsername;
	Table namingPlayer;
	Table tempTable;

	Label p1Label;
	Label p2Label;
	Label p3Label;
	Label p4Label;
	Label noPlayers;
	TextField p1NameEntry;
	TextField p2NameEntry;
	TextField p3NameEntry;
	TextField p4NameEntry;
	TextButton exitMenu;

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
		aiNumber = 0;
		playerNumber = 0;
		font = game.getAssetManager().manager.get(assetManager.PlayTime);
		screen = 0;
		nameList = new ArrayList<String>();
		nameList.add("1");
		nameList.add("2");
		nameList.add("3");
		nameList.add("4");
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
		playOptions.setPosition(640.0f - (gameStartY * (5.0f / 7.0f)), 360.0f - 300.0f);
		playOptions.setBackground(tempSkin.getDrawable("orange"));
		playOptions.setSize(gameStartY * (10.0f / 7.0f), gameStartY);

		// creating main header and also headers above ai and player
		Label header = new Label("Create Players", labelStyle);
		header.setAlignment(Align.center);

		// playersBox is a table to store the number of players selection button
		// collection for non ai
		Table playersBox = new Table();
		playersBox.setBackground(tempSkin.getDrawable("lightblue"));

		Label playerHeader = new Label("Add Players", labelStyle);
		playerHeader.setAlignment(Align.center);

		Label playersBoxText = new Label("0", counterLabelStyle);
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
		playersBox.add(playerHeader).colspan(3).padBottom(gameStartY / 28).height(gameStartY / 14)
				.width(gameStartY * (17.0f / 28.0f));
		playersBox.row();
		playersBox.add(playersBoxLeftArrow);
		playersBox.add(playersBoxText).align(Align.center).size(125.0f, 88.0f);
		playersBox.add(playersBoxRightArrow);

		// playersBox is a table to store the number of players selection button
		// collection for ai
		Table aIBox = new Table();
		aIBox.setBackground(tempSkin.getDrawable("lightblue"));

		Label aiHeader = new Label("Add CPU", labelStyle);
		aiHeader.setAlignment(Align.center);

		Label aIBoxText = new Label("0", counterLabelStyle);
		aIBoxText.setAlignment(Align.center);
		TextButton aIBoxLeftArrow = new TextButton("", leftArrowStyle);
		// aIBoxLeftArrow.setSize(47.0f, 47.0f);
		TextButton aIBoxRightArrow = new TextButton("", rightArrowStyle);

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
		// aIBox.add(aIBoxLeftArrow).size(gameStartY * (6.5f / 28.0f),
		// gameStartY * (4.5f / 14.0f));
		aIBox.add(aIBoxLeftArrow);
		aIBox.add(aIBoxText).align(Align.center).size(125.0f, 88.0f);
		aIBox.add(aIBoxRightArrow);
		;

		// adding these to one table
		tempTable = new Table();
		tempTable.setBackground(tempSkin.getDrawable("purple"));
		tempTable.add(playersBox).size(gameStartY / 28.0f * 18.0f, gameStartY / 14.0f * 6.0f).padLeft(gameStartY / 14)
				.padRight(gameStartY / 56);
		tempTable.add(aIBox).size(gameStartY / 28.0f * 18.0f, gameStartY / 14.0f * 6.0f).padRight(gameStartY / 14)
				.padLeft(gameStartY / 56);

		namingPlayer = new Table();
		namingPlayer.setBackground(tempSkin.getDrawable("green"));

		// namingPlayer.add(p1);
		namingPlayer.setSize(gameStartY * (9.0f / 7.0f), gameStartY * (6.0f / 14.0f));

		namingPlayer.setVisible(false);

		p1Label = new Label("Player 1", altLabelStyle);
		p1Label.setAlignment(Align.center);
		p1NameEntry = new TextField("", textFieldStyle);
		p1NameEntry.setAlignment(Align.center);

		p2Label = new Label("Player 2", altLabelStyle);
		p2Label.setAlignment(Align.center);
		p2NameEntry = new TextField("", textFieldStyle);
		p2NameEntry.setAlignment(Align.center);

		p3Label = new Label("Player 3", altLabelStyle);
		p3NameEntry = new TextField("", textFieldStyle);
		p3Label.setAlignment(Align.center);
		p3NameEntry.setAlignment(Align.center);

		p4Label = new Label("Player 4", altLabelStyle);
		p4NameEntry = new TextField("", textFieldStyle);
		p4Label.setAlignment(Align.center);
		p4NameEntry.setAlignment(Align.center);

		namingPlayer.add(p1Label).width(gameStartY / 14 * 8.75f).padRight(gameStartY / 56.0f);
		namingPlayer.add(p1NameEntry).height(30.0f).width(gameStartY / 14 * 8.75f).padLeft(gameStartY / 56.0f);
		namingPlayer.row();
		namingPlayer.add(p2Label).width(gameStartY / 14 * 8.75f).padRight(gameStartY / 56.0f);
		namingPlayer.add(p2NameEntry).height(30.0f).width(gameStartY / 14 * 8.75f).padLeft(gameStartY / 56.0f);
		namingPlayer.row();
		namingPlayer.add(p3Label).width(gameStartY / 14 * 8.75f).padRight(gameStartY / 56.0f);
		namingPlayer.add(p3NameEntry).height(30.0f).width(gameStartY / 14 * 8.75f).padLeft(gameStartY / 56.0f);
		namingPlayer.row();
		namingPlayer.add(p4Label).width(gameStartY / 14 * 8.75f).padRight(gameStartY / 56.0f);
		;
		namingPlayer.add(p4NameEntry).height(30.0f).width(gameStartY / 14 * 8.75f).padLeft(gameStartY / 56.0f);
		namingPlayer.setWidth(gameStartY / 14.0f * 18.0f);

		noPlayers = new Label("Add Some Players First", altLabelStyle);
		noPlayers.setAlignment(Align.center);
		noPlayers.setVisible(false);

		Stack stack = new Stack();
		stack.add(tempTable);
		stack.add(namingPlayer);
		stack.add(noPlayers);

		// start button, at bottom of box, starts the game
		TextButton start = new TextButton("Quickstart", altButtonStyle);
		start.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (playerCounter >= 2) {
					setPlayerArray();
					game.setScreen(
							new scrabbleMain(game, playerNumber, playerCounter - playerNumber, setPlayerArray()));
				}
			}
		});

		// naming button, at bottom of box, changes screen to name player screen
		TextButton naming = new TextButton("Name Players", altButtonStyle);
		naming.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

				if (screen == 0) {
					naming.setText("Edit Players");
					screen = 1;
				} else if (screen == 1) {
					naming.setText("Name Players");
					screen = 0;
				}
			}
		});

		// creating the main table
		playOptions.add(header).colspan(2).size(gameStartY / 14 * 12, gameStartY / 7)
				.pad(gameStartY / 14, gameStartY / 14, gameStartY / 14, gameStartY / 14)
				.size(gameStartY / 14 * 12, gameStartY / 7);
		playOptions.row();
		playOptions.add(stack).colspan(2).maxWidth(500.0f);
		playOptions.row();
		playOptions.add(naming).pad(gameStartY / 14, gameStartY / 14, gameStartY / 14, gameStartY / 56)
				.size(gameStartY / 28.0f * 18.0f, gameStartY / 7);
		playOptions.add(start).pad(gameStartY / 14, gameStartY / 56, gameStartY / 14, gameStartY / 14)
				.size(gameStartY / 28.0f * 18.0f, gameStartY / 7);

		stage.addActor(playOptions);

		exitMenu = new TextButton("", rightArrowStyle);
		exitMenu.setVisible(false);
		exitMenu.setPosition(817.0f, 335.0f);
	
		exitMenu.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				p1NameEntry.clearSelection();
				p2NameEntry.clearSelection();
				p3NameEntry.clearSelection();
				p4NameEntry.clearSelection();				
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
			exitMenu.setVisible(false);

		} else if (menuType == 1) {
			setGameMenuVisible();
			setMainMenuInvisible();
			exitMenu.setVisible(true);
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
			p1NameEntry.setVisible(false);
			p2NameEntry.setVisible(false);
			p3NameEntry.setVisible(false);
			p4NameEntry.setVisible(false);
			p1Label.setVisible(false);
			p2Label.setVisible(false);
			p3Label.setVisible(false);
			p4Label.setVisible(false);
			if (screen == 1) {
				noPlayers.setVisible(true);
			}
			break;
		case 1:
			p1NameEntry.setVisible(true);
			p2NameEntry.setVisible(false);
			p3NameEntry.setVisible(false);
			p4NameEntry.setVisible(false);
			p1Label.setVisible(true);
			p2Label.setVisible(false);
			p3Label.setVisible(false);
			p4Label.setVisible(false);
			noPlayers.setVisible(false);
			break;

		case 2:
			p1NameEntry.setVisible(true);
			p2NameEntry.setVisible(true);
			p3NameEntry.setVisible(false);
			p4NameEntry.setVisible(false);
			p1Label.setVisible(true);
			p2Label.setVisible(true);
			p3Label.setVisible(false);
			p4Label.setVisible(false);
			noPlayers.setVisible(false);
			break;
		case 3:
			p1NameEntry.setVisible(true);
			p2NameEntry.setVisible(true);
			p3NameEntry.setVisible(true);
			p4NameEntry.setVisible(false);
			p1Label.setVisible(true);
			p2Label.setVisible(true);
			p3Label.setVisible(true);
			p4Label.setVisible(false);
			noPlayers.setVisible(false);
			break;
		case 4:
			p1NameEntry.setVisible(true);
			p2NameEntry.setVisible(true);
			p3NameEntry.setVisible(true);
			p4NameEntry.setVisible(true);
			p1Label.setVisible(true);
			p2Label.setVisible(true);
			p3Label.setVisible(true);
			p4Label.setVisible(true);
			noPlayers.setVisible(false);
			break;
		default:
			p1NameEntry.setVisible(false);
			p2NameEntry.setVisible(false);
			p3NameEntry.setVisible(false);
			p4NameEntry.setVisible(false);
			p1Label.setVisible(false);
			p2Label.setVisible(false);
			p3Label.setVisible(false);
			p4Label.setVisible(false);
			noPlayers.setVisible(false);
			break;
		}
		switch (playerNumber) {
		case 0:
			p1Label.setText("AI P1");
			p2Label.setText("AI P2");
			p3Label.setText("AI P3");
			p4Label.setText("AI P4");
			break;
		case 1:
			p1Label.setText("Human P1");
			p2Label.setText("AI P1");
			p3Label.setText("AI P2");
			p4Label.setText("AI P3");
			break;
		case 2:
			p1Label.setText("Human P1");
			p2Label.setText("Human P2");
			p3Label.setText("AI P1");
			p4Label.setText("AI P2");
			break;
		case 3:
			p1Label.setText("Human P1");
			p2Label.setText("Human P2");
			p3Label.setText("Human P3");
			p4Label.setText("AI P1");
			break;
		case 4:
			p1Label.setText("Human P1");
			p2Label.setText("Human P2");
			p3Label.setText("Human P3");
			p4Label.setText("Human P4");
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

	private ArrayList<String> setPlayerArray() {
		ArrayList<String> x = new ArrayList<String>();
		if (p1NameEntry.getSelection() != null) {
			x.add(p1NameEntry.getSelection());
		} else {
			x.add("FuckYouLibgdx");
		}
		if (p2NameEntry.getSelection() != null) {
			x.add(p2NameEntry.getSelection());
		} else {
			x.add("FuckYouLibgdx");
		}
		if (p3NameEntry.getSelection() != null) {
			x.add(p3NameEntry.getSelection());
		} else {
			x.add("FuckYouLibgdx");
		}
		if (p4NameEntry.getSelection() != null) {
			x.add(p4NameEntry.getSelection());
		} else {
			x.add("FuckYouLibgdx");
		}
		return x;
	};
}
