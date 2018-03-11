package com.scrabble.game;

import assetmanager.assetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.scrabble.game.ScrabbleButton.ScrabbleButtonStyle;
import player.PlayersContainer;
import scrabble.Scrabble;
import screens.ScrabbleLauncher;
import screens.Screens.MainMenu;
import java.util.Random;


public class scrabbleMain implements Screen {
	
	Stage stage;
	Texture BoardBackground;
	SpriteBatch BoardBatch;
	TextButtonStyle textButtonStyle;
	TextButtonStyle textButtonStyle2;
	TextButtonStyle textButtonStyle3;
	BitmapFont font;
	Skin skin;
	TextureAtlas buttonAtlas;
	ScrabbleButtonStyle scrabbleButtonStyle;
	public static boolean passingOverTurn = false;
	boolean oneClickOnlyBoolean = true;
	Button startTurnButton;
	TextButton endTurn;
	TextButton startTurn;
	TextButton scores1;
	TextButton scores2;
	TextButton scores3;
	TextButton scores4;
	TextButton scoreThisTurn;
	OrthographicCamera camera;
	
	private Sound[] tilePress1;
	private Sound[] tilePress2;
	private Sound hover;
	private Random random;
	

	Scrabble scrabble = new Scrabble();
	private final int players = Scrabble.maxPlayers;

	// the board
	private Table table;
	// the 4 players
	private Table table2;
	private Table table3;
	private Table table4;
	private Table table5;

	int buttonCount = 0;
	ScrabbleLauncher game;
	private Label scoreLabel1;
	private Label scoreLabel2;
	private Label scoreLabel3;
	private Label scoreLabel4;

	public scrabbleMain(ScrabbleLauncher game) {
		this.game = game;
		hover = game.getAssetManager().manager.get(assetManager.mainClick);
		BoardBackground = game.getAssetManager().manager.get(assetManager.boardBackground);
		BoardBatch = new SpriteBatch();
		random = new Random();
		this.create();		
		

	}

	public void create() {
		
		

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
		

		stage = new Stage();

		table = new Table();

		table2 = new Table();
		table3 = new Table();
		table4 = new Table();
		table5 = new Table();

		table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		ScrabbleButton scrabbleButton;
		
		scoreLabel1 = new Label("",new Label.LabelStyle(font,Color.WHITE));
		scoreLabel1.setPosition(1205, 630);
        stage.addActor(scoreLabel1);		
        
        scoreLabel2 = new Label("",new Label.LabelStyle(font,Color.WHITE));
		scoreLabel2.setPosition(1205, 605);
        stage.addActor(scoreLabel2);	
        
        scoreLabel3 = new Label("",new Label.LabelStyle(font,Color.WHITE));
		scoreLabel3.setPosition(1205, 580);
        stage.addActor(scoreLabel3);	
        
        scoreLabel4 = new Label("",new Label.LabelStyle(font,Color.WHITE));
		scoreLabel4.setPosition(1205, 555);
        stage.addActor(scoreLabel4);	

		// sets up buttons for board
		int k = 0;
		stage.addActor(table);
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {

				scrabbleButton = new ScrabbleButton(" ", scrabbleButtonStyle, j, i, 0);
				scrabbleButton.setSize(36.4f, 36.4f);
				stage.addActor(scrabbleButton);
				table.add(scrabbleButton).size(36.4f, 36.4f).pad(2.0f);
				scrabbleButton.addListener( new ClickListener(){
					@Override
					public void clicked(InputEvent event, float x, float y){
						tilePress1[random.nextInt(tilePress1.length)].play(game.getSoundVol());
					};
				});

				k += 1;
				if (k % 15 == 0) {
					table.row();
				}

			}

		}

		table2.setSize(table.getWidth(), table.getHeight() - 650);
		for (int i = 0; i < 7; i++) {

			scrabbleButton = new ScrabbleButton(" ", scrabbleButtonStyle, i, 1, 3);
			scrabbleButton.setSize(36.4f, 36.4f);
			stage.addActor(scrabbleButton);
			table2.add(scrabbleButton).size(36.4f, 36.4f);
			scrabbleButton.addListener( new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y){
					tilePress2[random.nextInt(tilePress1.length)].play(game.getSoundVol());
				};
			});

		}
		stage.addActor(table2);

		table3.setSize(table.getWidth(), table.getHeight() + 650);
		for (int i = 0; i < 7; i++) {

			scrabbleButton = new ScrabbleButton(" ", scrabbleButtonStyle, i, 1, 1);
			scrabbleButton.setSize(36.4f, 36.4f);
			stage.addActor(scrabbleButton);
			table3.add(scrabbleButton).size(36.4f, 36.4f);
			scrabbleButton.addListener( new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y){
					tilePress2[random.nextInt(tilePress1.length)].play(game.getSoundVol());
				};
			});

		}
		stage.addActor(table3);

		table4.setSize(table.getWidth() + 700, table.getHeight());
		for (int i = 0; i < 7; i++) {
			
			scrabbleButton = new ScrabbleButton(" ", scrabbleButtonStyle, i, 1, 2);
			scrabbleButton.setSize(36.4f, 36.4f);
			stage.addActor(scrabbleButton);
			table4.add(scrabbleButton).size(36.4f, 36.4f).row();
			scrabbleButton.addListener( new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y){
					tilePress2[random.nextInt(tilePress1.length)].play(game.getSoundVol());
				};
			});

			
		}
		stage.addActor(table4);

		table5.setSize(table.getWidth() - 700, table.getHeight());
		for (int i = 0; i < 7; i++) {
			scrabbleButton = new ScrabbleButton(" ", scrabbleButtonStyle, i, 1, 4);
			scrabbleButton.setSize(36.4f, 36.4f);
			stage.addActor(scrabbleButton);
			table5.add(scrabbleButton).size(36.4f, 36.4f).row();
			scrabbleButton.addListener( new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y){
					tilePress2[random.nextInt(tilePress1.length)].play(game.getSoundVol());
				};
			});


		}
		
		stage.addActor(table5);
	
	
		
		//end turn button creation
		endTurn = new TextButton("", textButtonStyle2);
		endTurn.setPosition(1070.0f, 350.0f);
		endTurn.setSize(206.0f, 61.0f);
		
		endTurn.addListener( new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (Scrabble.incrementTurn() == true){			
					passingOverTurn = true;
				};
			};
		});
		stage.addActor(endTurn);
		
	
		//start turn, selected to pass turns over
		startTurn = new TextButton("", textButtonStyle3);
		startTurn.setPosition(1070.0f, 350.0f);
		startTurn.setSize(206.0f, 61.0f);
		
		startTurn.addListener( new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
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

	@Override
	public void show() {
		stage.getRoot().getColor().a = 0;
		stage.getRoot().addAction(Actions.fadeIn(0.5f));

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		BoardBatch.begin();
		BoardBatch.draw(BoardBackground, 0, 0);
		BoardBatch.end();
		if (passingOverTurn == true){
			endTurn.setVisible(false);
			startTurn.setVisible(true);
		}
		if (passingOverTurn == false){
			startTurn.setVisible(false);
			endTurn.setVisible(true);
		}
		
		scoreLabel1.setText(Integer.toString(PlayersContainer.getInstance().getPlayer(0).getScore()));
		scoreLabel2.setText(Integer.toString(PlayersContainer.getInstance().getPlayer(1).getScore()));
		scoreLabel3.setText(Integer.toString(PlayersContainer.getInstance().getPlayer(2).getScore()));
		scoreLabel4.setText(Integer.toString(PlayersContainer.getInstance().getPlayer(3).getScore()));
		
	
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
		

	}

	@Override
	public void dispose() {
		stage.dispose();
		BoardBackground.dispose();
		BoardBatch.dispose();
		hover.dispose();	
	}

}
