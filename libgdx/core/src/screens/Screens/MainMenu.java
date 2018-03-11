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
	private TextureAtlas buttonAtlas;
	private Sound hover;
	private Texture background;
	private BitmapFont font;
	private HashMap<String, TextButton> playOptionsArray;
	private HashMap<String, Label> playOptionsHeaderArray;
	private Table playOptions;
	private int menuType;
	private int playerCounter;

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
		;
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
		
		
		//setting up the play menu thing that comes up
		playOptions = new Table();
		playOptions.setPosition(640.0f, 240.0f);
		playOptions.setBackground(skin.getDrawable("website"));
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = font;		

		Label header = new Label("Just click play not actually linked yet", labelStyle);
		Label aiHeader = new Label("numberOfAi", labelStyle);
		Label playerHeader = new Label("numberOfHeaders", labelStyle);

		playOptionsHeaderArray.put("TopHeader", header);
		playOptionsHeaderArray.put("AIHeader", aiHeader);
		playOptionsHeaderArray.put("PlayerHeader", playerHeader);
		
		
		TextButton start = new TextButton("", playButtonStyle);
		start.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new scrabbleMain(game));
			}
		});

		
		Table playersBox = new Table();
	
		playersBox.setBackground(skin.getDrawable("help"));
		Label playersBoxText = new Label("0", labelStyle);
		TextButton playersBoxLeftArrow = new TextButton("", websiteButtonStyle);
		TextButton playersBoxRightArrow = new TextButton("", websiteButtonStyle);
		
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
				if (playerCounter > 0) {
					playersBoxText.setText(Integer.toString(Integer.parseInt(playersBoxText.getText().toString()) - 1));
					playerCounter -= 1;
				}
			}
		});
		playersBox.add(playersBoxLeftArrow).pad(5f);
		playersBox.add(playersBoxText).pad(5f);
		playersBox.add(playersBoxRightArrow).pad(5f);
		


		Table aIBox = new Table();
		
		aIBox.setBackground(skin.getDrawable("help"));
		Label aIBoxText = new Label("0", labelStyle);
		TextButton aIBoxLeftArrow = new TextButton("", websiteButtonStyle);
		TextButton aIBoxRightArrow = new TextButton("", websiteButtonStyle);
		
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
				if (playerCounter > 0) {
					aIBoxText.setText(Integer.toString(Integer.parseInt(aIBoxText.getText().toString()) - 1));
					playerCounter -= 1;
				}
			}
		});
		aIBox.add(aIBoxLeftArrow);
		aIBox.add(aIBoxText);
		aIBox.add(aIBoxRightArrow);
		
		playOptionsArray.put("Start", start);		;
	
		playOptions.add(playOptionsHeaderArray.get("TopHeader")).colspan(2).padBottom(25.0f);
		playOptions.row();		
		playOptions.add(playOptionsHeaderArray.get("PlayerHeader")).padBottom(5.0f).padTop(5.0f);
		playOptions.add(playOptionsHeaderArray.get("AIHeader")).padBottom(5.0f).padTop(5.0f);
		playOptions.row();
		playOptions.add(playersBox).size(127.0f, 127.0f);
		playOptions.add(aIBox).size(127.0f, 127.0f);
		playOptions.row();
		playOptions.add(playOptionsArray.get("Start")).colspan(2);

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
