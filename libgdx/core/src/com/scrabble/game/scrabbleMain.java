package com.scrabble.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.scrabble.game.ScrabbleButton;
import com.scrabble.game.ScrabbleButton.ScrabbleButtonStyle;

import scrabble.Scrabble;
import screens.ScrabbleLauncher;

public class scrabbleMain implements Screen {
	Stage stage;
	Texture BoardBackground;
	SpriteBatch BoardBatch;
	TextButtonStyle textButtonStyle;
	TextButtonStyle textButtonStyle2;
	BitmapFont font;
	Skin skin;
	TextureAtlas buttonAtlas;
	TextButtonStyle TextButtonStyle;
	ScrabbleButtonStyle scrabbleButtonStyle;
	boolean startTurn = true;
	
	OrthographicCamera camera;
	
	private Sound[] tilePress1;
	private Sound[] tilePress2;
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

	public scrabbleMain(ScrabbleLauncher game) {
		BoardBackground = new Texture("graphics/BoardScreen/BoardBackground.png");
		BoardBatch = new SpriteBatch();
		random = new Random();
		this.create();		

	}

	public void create() {

		tilePress1 = new Sound[]{
				Gdx.audio.newSound(Gdx.files.internal("Spells1_a_2edit.mp3")),
				Gdx.audio.newSound(Gdx.files.internal("Spells1_b.mp3")),
				Gdx.audio.newSound(Gdx.files.internal("Spells1_d.mp3")),
				Gdx.audio.newSound(Gdx.files.internal("Spells1_e.mp3")),
				Gdx.audio.newSound(Gdx.files.internal("Spells1_f.mp3"))
				
		};
		tilePress2 = new Sound[]{
				Gdx.audio.newSound(Gdx.files.internal("Spells2_a.mp3")),
				Gdx.audio.newSound(Gdx.files.internal("Spells2_b.mp3")),
				Gdx.audio.newSound(Gdx.files.internal("Spells2_c.mp3")),
				Gdx.audio.newSound(Gdx.files.internal("Spells2_f.mp3")),
				Gdx.audio.newSound(Gdx.files.internal("Spells2_g.mp3")),
				
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
		
		

		// sets up buttons for board
		int k = 0;
		stage.addActor(table);
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {

				libgdxsucks = new ScrabbleButton(" ", scrabbleButtonStyle, i, j, 0);
				libgdxsucks.setSize(36.4f, 36.4f);
				stage.addActor(libgdxsucks);
				table.add(libgdxsucks).size(36.4f, 36.4f).pad(2.0f);
				libgdxsucks.addListener( new ClickListener(){
					@Override
					public void clicked(InputEvent event, float x, float y){
						tilePress1[random.nextInt(tilePress1.length)].play(0.2f);
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
					tilePress2[random.nextInt(tilePress1.length)].play(0.2f);
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
					tilePress2[random.nextInt(tilePress1.length)].play(0.2f);
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
					tilePress2[random.nextInt(tilePress1.length)].play(0.2f);
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
					tilePress2[random.nextInt(tilePress1.length)].play(0.2f);
				};
			});


		}
		
		stage.addActor(table5);
	
	
		
		//end turn button creation
		TextButton endTurn = new TextButton("End Turn", textButtonStyle);
		endTurn.setPosition(0.0f,0f);
		endTurn.setSize(80.0f, 36.4f);
		
		endTurn.addListener( new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				int currentPlayer = Scrabble.currentPlayer;
				Scrabble.incrementTurn();
				if(currentPlayer == Scrabble.currentPlayer){
					startTurn = true;
				};
			};
		});
		stage.addActor(endTurn);
		//start turn, selected to pass turns over
		TextButton startTurn = new TextButton("Start Turn", textButtonStyle);
		startTurn.setPosition(0.0f,0f);
		startTurn.setSize(80.0f, 36.4f);
		
		startTurn.addListener( new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Scrabble.incrementTurn();
			};
		});
		stage.addActor(endTurn);
		
		TextButton menu = new TextButton("Menu", textButtonStyle);
		menu.setPosition(1180.0f, 663.6f);
		menu.setSize(80.0f, 36.4f);
		
		menu.addListener( new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				//add a menu here
			};
		});	
		stage.addActor(menu);
		
		
		
		Gdx.input.setInputProcessor(stage);
	}

	private void setupButtonConfig() {
		// sets up graphics of tiles
		font = new BitmapFont();
		skin = new Skin();
		buttonAtlas = new TextureAtlas(Gdx.files.internal("graphics/BoardScreen/TexturesTemp.pack"));
		skin.addRegions(buttonAtlas);
		
		// skins for the buttons
		scrabbleButtonStyle = new ScrabbleButtonStyle();
		scrabbleButtonStyle.up = skin.getDrawable("green");
		scrabbleButtonStyle.checked = skin.getDrawable("orange");
		scrabbleButtonStyle.down = skin.getDrawable("blue");
		scrabbleButtonStyle.over = skin.getDrawable("purple");
		scrabbleButtonStyle.font = font;
		
		// for the end turn + menu buttons
		textButtonStyle = new TextButtonStyle();	
		textButtonStyle.up = skin.getDrawable("lightblue");
		textButtonStyle.over = skin.getDrawable("yellow");
		textButtonStyle.font = font;
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
		// TODO Auto-generated method stub

	}

}
