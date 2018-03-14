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
import scrabble.Game;
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
    private TextButtonStyle textButtonStyle, textButtonStyle2, textButtonStyle3, tempButtonStyle;
    private LabelStyle labelStyle;
    private BitmapFont font;
    private Skin skin;
    private Skin tempSkin;
    private TextureAtlas buttonAtlas;
    private ScrabbleButtonStyle scrabbleButtonStyle;
    private TextButton startTurn, endTurn, shuffleButton, endGame, testButton;
	private Sound[] tilePress1, tilePress2;
	private Sound hover;
	private Random random;
    private ScrabbleLauncher game;
    
    //for test purposes only
    private boolean deplete;
    
	// board and player tiles
	private Table[] tables = new Table[5];
	// player score representations
	private Label[] scoreLabels = new Label[Game.getNumberOfPlayers()];
	private Label[] playerNames = new Label[Game.getNumberOfPlayers()];
	// tracking players who have ended the game
	private boolean[] playersEnded = new boolean[Game.getNumberOfPlayers()];
			
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
                //fiddy
                game.getAssetManager().manager.get(assetManager.click11)
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
		    playerNames[i] = new Label(players.removeFirst(),new Label.LabelStyle(font,Color.WHITE));
		    playerNames[i].setPosition(1078, scoreLabelPositionY-11.0f);
		    playerNames[i].setWidth(100.0f);		
		    playerNames[i].setAlignment(Align.center);
		   
		    scoreLabelPositionY -= 25;
            stage.addActor(scoreLabels[i]);
            stage.addActor(playerNames[i]);
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
                Game.endTurn();
				if (Game.getCurrentMove() == null){
					//fiddy
					tilePress2[5].play();
				};
			};
		});
		stage.addActor(endTurn);

        shuffleButton = new TextButton("SHUFFLE", textButtonStyle2);
        shuffleButton.setPosition(1070.0f, 275.0f);
        shuffleButton.setSize(206.0f, 61.0f);

        shuffleButton.addListener( new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

            };
        });
        stage.addActor(shuffleButton);
        
        endGame = new TextButton("endGame", textButtonStyle);
        endGame.setPosition(1070.0f, 275.0f);
        endGame.setSize(206.0f, 61.0f);

        endGame.addListener( new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	Game.removePlayer(Game.getCurrentPlayer());
            	Game.endTurn();
            };
        });
        endGame.setVisible(false);
        stage.addActor(endGame);

        //this is for testing only
        testButton = new TextButton("Deplete Bag", textButtonStyle2);
        testButton.setPosition(1070.0f, 205.0f);
        testButton.setSize(206.0f, 61.0f);

        testButton.addListener( new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if (deplete != true){
            		Game.getLetterBag().pickABunch();
            	}
            	deplete =true;
            };
        });
        stage.addActor(testButton);
        
        
        //start turn, selected to pass turns over
		startTurn = new TextButton("", textButtonStyle3);
		startTurn.setPosition(1070.0f, 350.0f);
		startTurn.setSize(206.0f, 61.0f);
		
		startTurn.addListener( new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
			    Game.startTurn();
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
		
		tempButtonStyle = new TextButtonStyle();
		tempButtonStyle.up =  tempSkin.getDrawable("green");
		tempButtonStyle.down =  tempSkin.getDrawable("yellow");
		tempButtonStyle.over =  tempSkin.getDrawable("purple");
		tempButtonStyle.font = font;
		
		labelStyle = new LabelStyle();
		labelStyle.font = font;		
	}

	@Override
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

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		BoardBatch.begin();
		BoardBatch.draw(BoardBackground, 0, 0);
		BoardBatch.end();

		if (Game.getCurrentMove() == null){
			endTurn.setVisible(false);
			startTurn.setVisible(true);
		} else {
			startTurn.setVisible(false);
			endTurn.setVisible(true);
		}

		for(int i = 0; i < scoreLabels.length; i++) {
            scoreLabels[i].setText(Integer.toString(Game.getPlayers().get(i).getScore()));
        }
		
		if (Game.getLetterBag().isEmpty()){
			endGame.setVisible(true);
			shuffleButton.setVisible(false);
		}
		else{
			endGame.setVisible(false);
			shuffleButton.setVisible(true);
		}

		stage.draw();
		stage.act();
	}
	
	public Table confirmationBox(TextButtonStyle tempStyle, LabelStyle labelStyle, Drawable drawable, String labelText){
		
		
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
		table.setPosition(300.0f, 300.0f);
		
		table.setBackground(drawable);
		table.pack();
		table.setHeight(table.getHeight() - 60.0f);
		
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
	public void resize(int width, int height) { }
	@Override
	public void pause() {}
	@Override
	public void resume() {}
	@Override
	public void hide() {}
}
