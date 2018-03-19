package screens.screens;

import assetmanager.assetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Queue;
import com.scrabble.game.BoardButton;
import com.scrabble.game.PlayerButton;
import com.scrabble.game.ScrabbleButton;
import com.scrabble.game.ScrabbleButton.ScrabbleButtonStyle;
import data.Coordinate;
import data.Timer;
import scrabble.Game;
import scrabble.Score;
import screens.ScrabbleLauncher;
import java.util.Random;

/**
 * @author Ben Miller, Asid Khan, Tom Geraghty
 * @version 1.0
 */
public class GameScreen implements Screen {

	public static boolean passingOverTurn = false;
	private Stage stage;
	private Texture BoardBackground;
	private SpriteBatch BoardBatch;
	private TextButtonStyle textButtonStyle, textButtonStyle2, textButtonStyle3, tempButtonStyle, plainButtonStyle, shuffleButtonStyle;
	private LabelStyle labelStyle;
	private BitmapFont font;
	private Skin skin;
	private Skin tempSkin;
	private TextureAtlas buttonAtlas;
	private ScrabbleButtonStyle scrabbleButtonStyle;
	private TextButton startTurn, endTurn, shuffleButton, endGame, testButton;
	private Sound[] tilePress1, tilePress2;
	private Sound turnChange, hover, timeCountdown, timeUp;
	private Random random;
	private ScrabbleLauncher game;
	private Table endLabel, endPlayerTurn;
	private Label timer;
	private ScrabbleButtonStyle redButtonStyle, blueButtonStyle, orangeButtonStyle, greenButtonStyle, brownButtonStyle;
	// for test purposes only
	private boolean deplete;
	// board and player tiles
	private Table[] tables = new Table[5];
	// player score representations
	private Label[] scoreLabels = new Label[Game.getNumberOfPlayers()];
	private Label[] playerNames = new Label[Game.getNumberOfPlayers()];
	// tracking players who have ended the game
	private int playersEnded = Game.getNumberOfPlayers();

	public GameScreen(ScrabbleLauncher game, Queue<String> players) {
		this.game = game;
		hover = game.getAssetManager().manager.get(assetManager.mainClick);
		BoardBackground = game.getAssetManager().manager.get(assetManager.boardBackground);
		BoardBatch = new SpriteBatch();
		random = new Random();
		tempSkin = new Skin();
	
		buttonAtlas = game.getAssetManager().manager.get(assetManager.texturesTemp);
		tempSkin.addRegions(buttonAtlas);
		this.create(players);
	}

	public void create(Queue<String> players) {
		ScrabbleButton scrabbleButton;
		tilePress1 = new Sound[] { game.getAssetManager().manager.get(assetManager.click1)	};
		tilePress2 = new Sound[] { game.getAssetManager().manager.get(assetManager.click6)	};
		turnChange = game.getAssetManager().manager.get(assetManager.click11);
		timeCountdown = game.getAssetManager().manager.get(assetManager.countdownTimer);
		timeUp = game.getAssetManager().manager.get(assetManager.timesUp);
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		setupButtonConfig();

		tables[0] = new Table();
		tables[0].setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		int scoreLabelPositionY = 630;

		// Timer
		timer = new Label("", new Label.LabelStyle(font,Color.BLACK));
		timer.setFontScale(0.8f);
        timer.setPosition(130, 668);
        timer.setAlignment(Align.center);
        stage.addActor(timer);
        timer.setVisible(true);

        for(int i = 0; i < Game.getNumberOfPlayers(); i++) {
			tables[i + 1] = new Table();
			scoreLabels[i] = new Label("",new Label.LabelStyle(font,Color.WHITE));
			scoreLabels[i].setFontScale(0.7f);
			scoreLabels[i].setPosition(1205, scoreLabelPositionY);
			playerNames[i] = new Label(Game.getPlayers().get(i).getPlayerName() ,new Label.LabelStyle(font,Color.WHITE));
			playerNames[i].setPosition(1078, scoreLabelPositionY-11.0f);
			playerNames[i].setFontScale(0.7f);
			playerNames[i].setWidth(100.0f);
			playerNames[i].setAlignment(Align.left);

			scoreLabelPositionY -= 25;
			stage.addActor(scoreLabels[i]);
			stage.addActor(playerNames[i]);
		}

		// sets up buttons for board
		int k = 0;
		stage.addActor(tables[0]);

        Score multipliers = new Score();
        for(int y = 0; y < 15; y++) {
            for(int x = 0; x < 15; x++) {
                char multiplierType = multipliers.getMultiplierType()[x][y];
                int multiplierValue = multipliers.getMultiplierScore()[x][y];
                scrabbleButton = new BoardButton(scrabbleButtonStyle, new Coordinate(x, y));
                scrabbleButton.setSize(36.4f, 36.4f);

                switch (multiplierType) {
                    case 'w':
                        switch (multiplierValue) {
                            case 2:
                                scrabbleButton.setStyle(blueButtonStyle);
                                break;
                            case 3:
                                scrabbleButton.setStyle(redButtonStyle);
                                break;
                        }
                        break;
                    case 'l':
                        switch (multiplierValue) {
                            case 2:
                                scrabbleButton.setStyle(orangeButtonStyle);
                                break;
                            case 3:
                                scrabbleButton.setStyle(greenButtonStyle);
                                break;
                        }
                        break;
                }

                if(x == 7 && y == 7) {
                    scrabbleButton.setStyle(brownButtonStyle);
                }

                stage.addActor(scrabbleButton);
                tables[0].add(scrabbleButton).size(36.4f, 36.4f).pad(2.0f);
                scrabbleButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        tilePress1[random.nextInt(tilePress1.length)].play(game.getSoundVol());
                    };
                });
                k += 1;
                if (k % 15 == 0) {
                    tables[0].row();
                }
            }
        }

        /**
				//scrabbleButton = new BoardButton(scrabbleButtonStyle, new Coordinate(i, j));
				//scrabbleButton.setSize(36.4f, 36.4f);
				/* visual representation of multipliers 
				* red = x3 word 
				* blue = x2 word
				* orange = x2 letter
				* green = x3 letter
				* yellow = starting position
		*/

		switch (Game.getNumberOfPlayers()) {
		case 4:
			tables[4].setSize(tables[0].getWidth() - 700, tables[0].getHeight());
			setupPlayerLetters(4, true);
		case 3:
			tables[3].setSize(tables[0].getWidth(), tables[0].getHeight() - 650);
			setupPlayerLetters(3, false);
		default:
			tables[1].setSize(tables[0].getWidth(), tables[0].getHeight() + 650);
			setupPlayerLetters(1, false);
			tables[2].setSize(tables[0].getWidth() + 700, tables[0].getHeight());
			setupPlayerLetters(2, true);
			break;
		case 2:
			tables[1].setSize(tables[0].getWidth(), tables[0].getHeight() + 650);
			setupPlayerLetters(1, false);
			tables[2].setSize(tables[0].getWidth(), tables[0].getHeight() - 650);
			setupPlayerLetters(2, false);
			break;
		}
//from here
		
		// end turn button creation
		endTurn = new TextButton("", textButtonStyle2);
		endTurn.setPosition(1070.0f, 350.0f);
		endTurn.setSize(155.0f, 55.0f);

		endTurn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
				Game.endTurn();
				turnChange.play(game.getSoundVol());
				if (Game.getCurrentMove() == null) {
									
				}
				
				;
			};
		});
		stage.addActor(endTurn);

		shuffleButton = new TextButton("", shuffleButtonStyle);
		shuffleButton.setPosition(1070.0f, 275.0f);
		shuffleButton.setSize(155.0f, 55.0f);

		shuffleButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

			};
		});
		stage.addActor(shuffleButton);

		endGame = new TextButton("endGame", plainButtonStyle);
		endGame.setPosition(1070.0f, 275.0f);
		endGame.setSize(155.0f, 65.0f);

		endGame.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				endPlayerTurn.setVisible(true);
				

			};
		});
		endGame.setVisible(false);
		stage.addActor(endGame);
		endPlayerTurn = playerFinishedConfirmationBox(plainButtonStyle, labelStyle, tempSkin.getDrawable("purple"), "Are you sure you have can't play another move?");
		
		stage.addActor(endPlayerTurn);
		endPlayerTurn.setVisible(false);

		// this is for testing only
		testButton = new TextButton("Deplete Bag", plainButtonStyle);
		testButton.setPosition(1070.0f, 205.0f);
		testButton.setSize(206.0f, 61.0f);

		testButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (deplete != true) {
					Game.getLetterBag().pickABunch();
				}
				System.out.println("bitch word");
				deplete = true;
			};
		});
		stage.addActor(testButton);

		// start turn, selected to pass turns over
		startTurn = new TextButton("", textButtonStyle3);
		startTurn.setPosition(1070.0f, 350.0f);
		startTurn.setSize(155.0f, 55.0f);

		startTurn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Game.startTurn();
			};
		});
		stage.addActor(startTurn);

		// main menu button
		TextButton menu = new TextButton("", textButtonStyle);
		menu.setPosition(0f, 0f);
		menu.setSize(65.0f, 65.0f);

		menu.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {

				stage.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.run(new Runnable() {

					@Override
					public void run() {

						hover.play(game.getSoundVol());
						stage.dispose();
						Game.getTimer().pauseTimer();
                        game.setScreen(new MainMenu(game));

					}
				})));
			}
		});;
		stage.addActor(menu);
		Gdx.input.setInputProcessor(stage);

		endLabel = new Table();
		Label endGameLabel = new Label("Game has finished", labelStyle);
		endGameLabel.setAlignment(Align.center);
		TextButton resultsScreen = new TextButton("View Results", plainButtonStyle);
		resultsScreen.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				hover.play(game.getSoundVol());
				stage.dispose();

			};
		});
		endLabel.add(endGameLabel).size(300.0f, 150.0f);
		endLabel.row();
		endLabel.add(resultsScreen).height(70.0f);
		endLabel.pack();
		endLabel.setPosition((1280.0f-endLabel.getWidth())*0.5f, (720.0f-endLabel.getHeight())*0.5f);
		endLabel.setBackground(tempSkin.getDrawable("lightblue"));
		stage.addActor(endLabel);

	}

	private void setupButtonConfig() {
		// sets up graphics of tiles
		font = game.getAssetManager().manager.get(assetManager.PlayTime);
		skin = new Skin();
		buttonAtlas = game.getAssetManager().manager.get(assetManager.gameButtonPack);
		;
		skin.addRegions(buttonAtlas);

		// skins for the buttons
		scrabbleButtonStyle = new ScrabbleButtonStyle();
		scrabbleButtonStyle.up = skin.getDrawable("boardButton");
		scrabbleButtonStyle.checked = skin.getDrawable("boardButtonPressed");
		scrabbleButtonStyle.down = skin.getDrawable("boardButtonHover");
		scrabbleButtonStyle.over = skin.getDrawable("boardButtonHover");
		scrabbleButtonStyle.font = font;
		
		blueButtonStyle = new ScrabbleButtonStyle();
		blueButtonStyle.up = skin.getDrawable("greyButton");
		blueButtonStyle.checked = skin.getDrawable("greyButtonPressed");
		blueButtonStyle.down = skin.getDrawable("greyButtonHover");
		blueButtonStyle.over = skin.getDrawable("greyButtonHover");
		blueButtonStyle.font = font;
		
		greenButtonStyle = new ScrabbleButtonStyle();
		greenButtonStyle.up = skin.getDrawable("greenButton");
		greenButtonStyle.checked = skin.getDrawable("greenButtonPressed");
		greenButtonStyle.down = skin.getDrawable("greenButtonHover");
		greenButtonStyle.over = skin.getDrawable("greenButtonHover");
		greenButtonStyle.font = font;
		
		orangeButtonStyle = new ScrabbleButtonStyle();
		orangeButtonStyle.up = skin.getDrawable("orangeButton");
		orangeButtonStyle.checked = skin.getDrawable("orangeButtonPressed");
		orangeButtonStyle.down = skin.getDrawable("orangeButtonHover");
		orangeButtonStyle.over = skin.getDrawable("orangeButtonHover");
		orangeButtonStyle.font = font;
		
		redButtonStyle = new ScrabbleButtonStyle();
		redButtonStyle.up = skin.getDrawable("redButton");
		redButtonStyle.checked = skin.getDrawable("redButtonPressed");
		redButtonStyle.down = skin.getDrawable("redButtonHover");
		redButtonStyle.over = skin.getDrawable("redButtonHover");
		redButtonStyle.font = font;
		
		brownButtonStyle = new ScrabbleButtonStyle();
		brownButtonStyle.up = skin.getDrawable("brownButton");
		brownButtonStyle.checked = skin.getDrawable("brownButtonPressed");
		brownButtonStyle.down = skin.getDrawable("brownButtonHover");
		brownButtonStyle.over = skin.getDrawable("brownButtonHover");
		brownButtonStyle.font = font;
		// shuffle button
		shuffleButtonStyle = new TextButtonStyle();
		shuffleButtonStyle.up = skin.getDrawable("shuffleButton");
		shuffleButtonStyle.down = skin.getDrawable("shuffleButtonPressed");
		shuffleButtonStyle.over = skin.getDrawable("shuffleButtonPressed");
		shuffleButtonStyle.font = font;
		
		
		plainButtonStyle = new TextButtonStyle();
		plainButtonStyle.up = skin.getDrawable("plainButton");
		plainButtonStyle.down = skin.getDrawable("plainButtonPressed");
		plainButtonStyle.font = font;

		// for the menu button
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("homeButton");
		textButtonStyle.over = skin.getDrawable("homeButtonPressed");
		textButtonStyle.font = font;

		// for the end turn button
		textButtonStyle2 = new TextButtonStyle();
		textButtonStyle2.up = skin.getDrawable("endButton");
		textButtonStyle2.over = skin.getDrawable("endButtonPressed");
		textButtonStyle2.font = font;

		textButtonStyle3 = new TextButtonStyle();
		textButtonStyle3.up = skin.getDrawable("startButton");
		textButtonStyle3.over = skin.getDrawable("startButtonPressed");
		textButtonStyle3.font = font;

		tempButtonStyle = new TextButtonStyle();
		tempButtonStyle.up = tempSkin.getDrawable("green");
		tempButtonStyle.down = tempSkin.getDrawable("yellow");
		tempButtonStyle.over = tempSkin.getDrawable("purple");
		tempButtonStyle.font = font;

		labelStyle = new LabelStyle();
		labelStyle.font = font;
		labelStyle.background = tempSkin.getDrawable("purple");
	}

	@Override
	public void show() {
		stage.getRoot().getColor().a = 0;
		stage.getRoot().addAction(Actions.fadeIn(0.5f));
	}

	private void setupPlayerLetters(int player, boolean vertical) {
		ScrabbleButton scrabbleButton;
		for (int i = 0; i < 7; i++) {
			scrabbleButton = new PlayerButton(scrabbleButtonStyle, new Coordinate(i, 1), Game.getPlayers().get(player - 1));
			scrabbleButton.setSize(36.4f, 36.4f);
			stage.addActor(scrabbleButton);
			if (vertical) {
				tables[player].add(scrabbleButton).size(36.4f, 36.4f).row();
			} else {
				tables[player].add(scrabbleButton).size(36.4f, 36.4f);
			}
			scrabbleButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					tilePress2[random.nextInt(tilePress1.length)].play(game.getSoundVol());
				};
			});
		}
		stage.addActor(tables[player]);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		BoardBatch.begin();
		BoardBatch.draw(BoardBackground, 0, 0);
		BoardBatch.end();

		if (Game.getCurrentMove() == null) {
			endTurn.setVisible(false);
			startTurn.setVisible(true);
		} else {
			startTurn.setVisible(false);
			endTurn.setVisible(true);
		}

		for (int i = 0; i < scoreLabels.length; i++) {
			scoreLabels[i].setText(Integer.toString(Game.getPlayers().get(i).getScore()));
		}

		timer.setText(Timer.timeFormatter(Game.getTimer().getTimeLeft()));
		if(Game.getTimer().getTimeLeft() < 10000) {
            float scalar = 0.8f + ((float) (Game.getTimer().getTimeLeft() % 1000) / 1000) / 5f;
		    if(scalar > 0.8) {
                timer.setFontScale(scalar);
            }
            if((Game.getTimer().getTimeLeft() % 2000) > 1000 ||Game.getTimer().getTimeLeft() < 5000) {
                timer.setStyle(new Label.LabelStyle(font,Color.RED));
            } else {
                timer.setStyle(new Label.LabelStyle(font,Color.BLACK));
            }
        } else {
            timer.setStyle(new Label.LabelStyle(font,Color.BLACK));
        }

		if (Game.getLetterBag().isEmpty()) {
			endGame.setVisible(true);
			shuffleButton.setVisible(false);
		} else {
			endGame.setVisible(false);
			shuffleButton.setVisible(true);
		}
		if (playersEnded == 0) {
			endLabel.setVisible(true);
		} else {
			endLabel.setVisible(false);
		}
		stage.draw();
		stage.act();
	}

	public Table confirmationBox(TextButtonStyle tempStyle, LabelStyle labelStyle, Drawable drawable,
			String labelText) {

		Table table = new Table();
		table.setWidth(360.0f);

		Label label = new Label(labelText, labelStyle);
		label.setWrap(true);
		label.setAlignment(Align.center);

		TextButton yes = new TextButton("yes", tempStyle);
		TextButton no = new TextButton("no", tempStyle);

		table.add(label).pad(10.0f).colspan(2).fill().width(280.0f);
		table.row();
		table.add(yes).pad(10.0f).size(130.0f, 40.0f);
		table.add(no).pad(10.0f).size(130.0f, 40.0f);

		table.setBackground(drawable);
		table.pack();
		table.setHeight(table.getHeight() - 60.0f);
		table.setPosition((1280.0f-table.getWidth())*0.5f, (720.0f-table.getHeight())*0.5f);
		return table;
	}
	
	public Table playerFinishedConfirmationBox(TextButtonStyle tempStyle, LabelStyle labelStyle, Drawable drawable,
			String labelText) {

		Table table = new Table();
		table.setWidth(360.0f);

		Label label = new Label(labelText, labelStyle);
		label.setWrap(true);
		label.setAlignment(Align.center);
		if (this.playersEnded == 0){
			label.setText("Are you sure you want to end your turn and finish the game?"); 
		}
		

		TextButton yes = new TextButton("yes", tempStyle);
		yes.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				tilePress2[random.nextInt(tilePress1.length)].play(game.getSoundVol());
				Game.getCurrentPlayer().finishedAllTurns();
				playersEnded -= 1;
				System.out.println("hello" + playersEnded);
				if (playersEnded != 0) {
					Game.endTurn();
					endPlayerTurn.setVisible(false);
				}				
				if (playersEnded == 0){
					game.setScreen(new ResultsScreen(game));
				}
				
			};
		});
		TextButton no = new TextButton("no", tempStyle);
		no.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				tilePress2[random.nextInt(tilePress1.length)].play(game.getSoundVol());
				endPlayerTurn.setVisible(false);
			};
		});

		table.add(label).pad(10.0f).colspan(2).fill().width(280.0f);
		table.row();
		table.add(yes).pad(10.0f).size(130.0f, 40.0f);
		table.add(no).pad(10.0f).size(130.0f, 40.0f);
	

		table.setBackground(drawable);
		table.pack();
		table.setHeight(table.getHeight() - 60.0f);
		table.setPosition((1280.0f-table.getWidth())*0.5f, (720.0f-table.getHeight())*0.5f);
		
		return table;
	}


	@Override
	public void dispose() {
		stage.dispose();
		BoardBackground.dispose();
		BoardBatch.dispose();
		hover.dispose();
	}

	/* EMPTY IMPLEMENTATIONS */
	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}
}
