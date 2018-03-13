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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.scrabble.game.BoardButton;
import com.scrabble.game.PlayerButton;
import com.scrabble.game.ScrabbleButton;
import com.scrabble.game.ScrabbleButton.ScrabbleButtonStyle;
import data.Coordinate;
import scrabble.Game;
import scrabble.Scrabble;
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
    private TextButtonStyle textButtonStyle, textButtonStyle2, textButtonStyle3;
    private BitmapFont font;
    private Skin skin;
    private TextureAtlas buttonAtlas;
    private ScrabbleButtonStyle scrabbleButtonStyle;
    private TextButton startTurn, endTurn, shuffleButton;
	private Sound[] tilePress1, tilePress2;
	private Sound hover;
	private Random random;
    private ScrabbleLauncher game;

	// board and player tiles
	private Table[] tables = new Table[5];
	// player score representations
	private Label[] scoreLabels = new Label[Game.getNumberOfPlayers()];

	public GameScreen(ScrabbleLauncher game) {
		this.game = game;
		hover = game.getAssetManager().manager.get(assetManager.mainClick);
		BoardBackground = game.getAssetManager().manager.get(assetManager.boardBackground);
		BoardBatch = new SpriteBatch();
		random = new Random();
		this.create();
	}

	public void create() {
        ScrabbleButton scrabbleButton;
		tilePress1 = new Sound[]{
				game.getAssetManager().manager.get(assetManager.click1),
				game.getAssetManager().manager.get(assetManager.click2),
				game.getAssetManager().manager.get(assetManager.click3),
				game.getAssetManager().manager.get(assetManager.click4),
				game.getAssetManager().manager.get(assetManager.click5)
		};
		tilePress2 = new Sound[]{
				game.getAssetManager().manager.get(assetManager.click6),
				game.getAssetManager().manager.get(assetManager.click7),
				game.getAssetManager().manager.get(assetManager.click8),
				game.getAssetManager().manager.get(assetManager.click9),
				game.getAssetManager().manager.get(assetManager.click10),
		};
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		setupButtonConfig();

		tables[0] = new Table();
        tables[0].setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		int scoreLabelPositionY = 630;

		for(int i = 0; i < Game.getNumberOfPlayers(); i++) {
            tables[i + 1] = new Table();
		    scoreLabels[i] = new Label("",new Label.LabelStyle(font,Color.WHITE));
		    scoreLabels[i].setPosition(1205, scoreLabelPositionY);
		    scoreLabelPositionY -= 25;
            stage.addActor(scoreLabels[i]);
        }


		// sets up buttons for board
		int k = 0;
		stage.addActor(tables[0]);
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				scrabbleButton = new BoardButton(scrabbleButtonStyle, new Coordinate(i, j));
				scrabbleButton.setSize(36.4f, 36.4f);
				stage.addActor(scrabbleButton);
                tables[0].add(scrabbleButton).size(36.4f, 36.4f).pad(2.0f);
				scrabbleButton.addListener( new ClickListener(){
					@Override
					public void clicked(InputEvent event, float x, float y){
						tilePress1[random.nextInt(tilePress1.length)].play(game.getSoundVol());
					};
				});
				k += 1;
				if (k % 15 == 0) {
                    tables[0].row();
				}
			}
		}



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
	
		
		//end turn button creation
		endTurn = new TextButton("", textButtonStyle2);
		endTurn.setPosition(1070.0f, 350.0f);
		endTurn.setSize(206.0f, 61.0f);
		
		endTurn.addListener( new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (Scrabble.incrementTurn() == true){
				    Game.endTurn();
					passingOverTurn = true;
				};
			};
		});
		stage.addActor(endTurn);

        shuffleButton = new TextButton("SHUFFLE", textButtonStyle2);
        shuffleButton.setPosition(1070.0f, 275.0f);
        shuffleButton.setSize(206.0f, 61.0f);

        endTurn.addListener( new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

            };
        });
        stage.addActor(shuffleButton);

        //start turn, selected to pass turns over
		startTurn = new TextButton("", textButtonStyle3);
		startTurn.setPosition(1070.0f, 350.0f);
		startTurn.setSize(206.0f, 61.0f);
		
		startTurn.addListener( new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
			    Game.startTurn();
				passingOverTurn = false;
			};
		});
		stage.addActor(startTurn);
		
		// main menu button
		TextButton menu = new TextButton("", textButtonStyle);
		menu.setPosition(0f,0f);
		menu.setSize(65.0f, 65.0f);
		
		menu.addListener( new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				hover.play(game.getSoundVol());
				stage.dispose();
				game.setScreen(new MainMenu(game));
			};
		});	
		stage.addActor(menu);
		Gdx.input.setInputProcessor(stage);
	}

	private void setupButtonConfig() {
		// sets up graphics of tiles
		font = game.getAssetManager().manager.get(assetManager.PlayTime);
		skin = new Skin();
		buttonAtlas = game.getAssetManager().manager.get(assetManager.gameButtonPack);;
		skin.addRegions(buttonAtlas);
		
		// skins for the buttons
		scrabbleButtonStyle = new ScrabbleButtonStyle();
		scrabbleButtonStyle.up = skin.getDrawable("boardButton");
		scrabbleButtonStyle.checked = skin.getDrawable("boardButtonPressed");
		scrabbleButtonStyle.down = skin.getDrawable("boardButtonHover");
		scrabbleButtonStyle.over = skin.getDrawable("boardButtonHover");
		scrabbleButtonStyle.font = font;
		
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
	}

	public void show() {
		stage.getRoot().getColor().a = 0;
		stage.getRoot().addAction(Actions.fadeIn(0.5f));
	}

	private void setupPlayerLetters(int player, boolean vertical) {
        ScrabbleButton scrabbleButton;
        for (int i = 0; i < 7; i++) {
            scrabbleButton = new PlayerButton(scrabbleButtonStyle, new Coordinate(i, 1), player);
            scrabbleButton.setSize(36.4f, 36.4f);
            stage.addActor(scrabbleButton);
            if(vertical) {
                tables[player].add(scrabbleButton).size(36.4f, 36.4f).row();
            } else {
                tables[player].add(scrabbleButton).size(36.4f, 36.4f);
            }
            scrabbleButton.addListener( new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y){
                    tilePress2[random.nextInt(tilePress1.length)].play(game.getSoundVol());
                };
            });
        }
        stage.addActor(tables[player]);
    }

	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		BoardBatch.begin();
		BoardBatch.draw(BoardBackground, 0, 0);
		BoardBatch.end();

		if (passingOverTurn){
			endTurn.setVisible(false);
			startTurn.setVisible(true);
		} else {
			startTurn.setVisible(false);
			endTurn.setVisible(true);
		}

		for(int i = 0; i < scoreLabels.length; i++) {
            scoreLabels[i].setText(Integer.toString(Game.getPlayers().get(i).getScore()));
        }

		stage.draw();
		stage.act();
	}

    public void dispose() {
        stage.dispose();
        BoardBackground.dispose();
        BoardBatch.dispose();
        hover.dispose();
    }

    /* EMPTY IMPLEMENTATIONS */
	public void resize(int width, int height) { }
	public void pause() {}
	public void resume() {}
	public void hide() {}
}
