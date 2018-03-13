package com.scrabble.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Game;
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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton.ImageTextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.scrabble.game.ScrabbleButton;
import com.scrabble.game.ScrabbleButton.ScrabbleButtonStyle;

import assetmanager.assetManager;
import player.PlayersContainer;
import scrabble.Board;
import scrabble.Scrabble;
import screens.ScrabbleLauncher;
import screens.Screens.MainMenu;


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
	
	private int aI;
	private int player;
	

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
	
	private ArrayList<String> soBored;

	public scrabbleMain(ScrabbleLauncher game, int player, int aI, ArrayList<String> soBored) {
		this.game = game;
		hover = game.getAssetManager().manager.get(assetManager.mainClick);
		BoardBackground = game.getAssetManager().manager.get(assetManager.boardBackground);
		BoardBatch = new SpriteBatch();
		random = new Random();
		this.create();		
		this.player = player;
		this.aI = aI;
		this.soBored = soBored;

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
		ScrabbleButton libgdxsucks;
		
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

				libgdxsucks = new ScrabbleButton(" ", scrabbleButtonStyle, j, i, 0);
				libgdxsucks.setSize(36.4f, 36.4f);
				stage.addActor(libgdxsucks);
				table.add(libgdxsucks).size(36.4f, 36.4f).pad(2.0f);
				libgdxsucks.addListener( new ClickListener(){
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

			libgdxsucks = new ScrabbleButton(" ", scrabbleButtonStyle, i, 1, 3);
			libgdxsucks.setSize(36.4f, 36.4f);
			stage.addActor(libgdxsucks);
			table2.add(libgdxsucks).size(36.4f, 36.4f);
			libgdxsucks.addListener( new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y){
					tilePress2[random.nextInt(tilePress1.length)].play(game.getSoundVol());
				};
			});

		}
		stage.addActor(table2);

		table3.setSize(table.getWidth(), table.getHeight() + 650);
		for (int i = 0; i < 7; i++) {

			libgdxsucks = new ScrabbleButton(" ", scrabbleButtonStyle, i, 1, 1);
			libgdxsucks.setSize(36.4f, 36.4f);
			stage.addActor(libgdxsucks);
			table3.add(libgdxsucks).size(36.4f, 36.4f);
			libgdxsucks.addListener( new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y){
					tilePress2[random.nextInt(tilePress1.length)].play(game.getSoundVol());
				};
			});

		}
		stage.addActor(table3);

		table4.setSize(table.getWidth() + 700, table.getHeight());
		for (int i = 0; i < 7; i++) {
			
			libgdxsucks = new ScrabbleButton(" ", scrabbleButtonStyle, i, 1, 2);
			libgdxsucks.setSize(36.4f, 36.4f);
			stage.addActor(libgdxsucks);
			table4.add(libgdxsucks).size(36.4f, 36.4f).row();
			libgdxsucks.addListener( new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y){
					tilePress2[random.nextInt(tilePress1.length)].play(game.getSoundVol());
				};
			});

			
		}
		stage.addActor(table4);

		table5.setSize(table.getWidth() - 700, table.getHeight());
		for (int i = 0; i < 7; i++) {
			libgdxsucks = new ScrabbleButton(" ", scrabbleButtonStyle, i, 1, 4);
			libgdxsucks.setSize(36.4f, 36.4f);
			stage.addActor(libgdxsucks);
			table5.add(libgdxsucks).size(36.4f, 36.4f).row();
			libgdxsucks.addListener( new ClickListener(){
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
		
/**		scores1 = new TextButton("P1 score" + Integer.toString(PlayersContainer.getInstance().getPlayer(0).getScore()), textButtonStyle2);
  		scores1.setPosition(1070.0f,110.0f);
     	scores1.setSize(206.0f, 40.0f);
		
		scores1.addListener( new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				hover.play(game.getSoundVol());
			};
		});	
		stage.addActor(scores1);
		
		scores2 = new TextButton("P2 score" + Integer.toString(PlayersContainer.getInstance().getPlayer(1).getScore()), textButtonStyle);
		scores2.setPosition(1070.0f,80.0f);
		scores2.setSize(206.0f, 40.0f);
		
		scores2.addListener( new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				hover.play(game.getSoundVol());
				
			};
		});	
		stage.addActor(scores2);
		
		scores3 = new TextButton("P3 score" + Integer.toString(PlayersContainer.getInstance().getPlayer(2).getScore()), textButtonStyle);
		scores3.setPosition(1070.0f,50.0f);
		scores3.setSize(206.0f, 40.0f);
		
		scores3.addListener( new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				hover.play(game.getSoundVol());
				
			};
		});	
		stage.addActor(scores3);
		
		scores4 = new TextButton("P4 score" + Integer.toString(PlayersContainer.getInstance().getPlayer(3).getScore()), textButtonStyle);
		scores4.setPosition(1070.0f,20.0f);
		scores4.setSize(206.0f, 40.0f);
		
		scores4.addListener( new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				hover.play(game.getSoundVol());
				
			};
		});	
		stage.addActor(scores4);
		
		TextButton 	scoreThisTurn = new TextButton("score this turn not implemented", textButtonStyle);
		scoreThisTurn.setPosition(0.0f,330.0f);
		scoreThisTurn.setSize(206.0f, 60.0f);
		
		scoreThisTurn.addListener( new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				hover.play(game.getSoundVol());
				
			};
		});	
		stage.addActor(scoreThisTurn);
*/		
		
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
