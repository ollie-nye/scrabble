package com.scrabble.game;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
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
	ImageTextButtonStyle imageTextButtonStyle;
	ScrabbleButtonStyle scrabbleButtonStyle;

	ScrabbleButton button1;
	TextButton button2;
	TextButton button3;
	TextButton button4;
	TextButton button5;
	TextButton button6;
	TextButton button7;
	TextButton button8;
	TextButton testTextButton;
	Scrabble scrabble = new Scrabble();

	private Table table;
	private Table table2;
	private Table table3;
	private Table table4;
	private Table table5;
	private int[] ignore = new int[2];
	
	
	private ScrabbleButton[][] ahh = new ScrabbleButton[100][100];
	
	char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	public static char[] textHolder = new char[7];
	int buttonCount = 0;
	
	public scrabbleMain(ScrabbleLauncher game) {
		BoardBackground = new Texture("BoardBackground.png");
		BoardBatch = new SpriteBatch();
		
		this.create();
		
	}
	
	public void create() {
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		setupButtonConfig();
		System.out.println(tempBoard.getInstance().getLetter(1, 1));
		textHolder[0] = 'a';
		textHolder[1] = 'b';

		stage = new Stage();
		table = new Table();
		
		table2 = new Table();
		table3 = new Table();
		
		table4 = new Table();
		table5 = new Table();

		table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// sets up buttons for board
	
		
	

		int k = 0;
		stage.addActor(table);
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				k += 1;
				
				ignore[0] = i;
				ignore[1] = j;
				

			
				ScrabbleButton libgdxsucks = new ScrabbleButton(" ", scrabbleButtonStyle, i, j, 0);	
				libgdxsucks.setSize(36.4f, 36.4f);				
				stage.addActor(libgdxsucks);
				table.add(libgdxsucks).size(36.4f, 36.4f).pad(2.0f);
			
				
				if (k % 15 == 0) {

					table.row();

				}
			}
			
		}
		
	
		
		ignore[0] = 30;
		ignore[1] = 30;

		table2.setSize(table.getWidth(), table.getHeight() - 650);
		for (int i = 0; i < 7; i++) {

			ScrabbleButton libgdxsucks = new ScrabbleButton(" ", scrabbleButtonStyle, i, 1, 1);	
			libgdxsucks.setSize(36.4f, 36.4f);				
			stage.addActor(libgdxsucks);			
			table2.add(libgdxsucks).size(36.4f, 36.4f);
			
		}
		stage.addActor(table2);

		table3.setSize(table.getWidth(), table.getHeight() + 650);
		for (int i = 0; i < 7; i++) {
			ScrabbleButton libgdxsucks = new ScrabbleButton(" ", scrabbleButtonStyle, i, 1, 2);	
			libgdxsucks.setSize(36.4f, 36.4f);				
			stage.addActor(libgdxsucks);			
			table3.add(libgdxsucks).size(36.4f, 36.4f);
		}
		stage.addActor(table3);

		table4.setSize(table.getWidth() + 700, table.getHeight());
		for (int i = 0; i < 7; i++) {
			ScrabbleButton libgdxsucks = new ScrabbleButton(" ", scrabbleButtonStyle, i, 1, 3);	
			libgdxsucks.setSize(36.4f, 36.4f);				
			stage.addActor(libgdxsucks);			
			table4.add(libgdxsucks).size(36.4f, 36.4f).row();
		}
		stage.addActor(table4);

		table5.setSize(table.getWidth() - 700, table.getHeight());
		for (int i = 0; i < 7; i++) {
			ScrabbleButton libgdxsucks = new ScrabbleButton(" ", scrabbleButtonStyle, i, 1, 4);	
			libgdxsucks.setSize(36.4f, 36.4f);				
			stage.addActor(libgdxsucks);			
			table5.add(libgdxsucks).size(36.4f, 36.4f).row();;
			
		}
		stage.addActor(table5);
		

		Gdx.input.setInputProcessor(stage);
	}

	private void setupButtonConfig() {
		// sets up graphics of tiles
		font = new BitmapFont();
		skin = new Skin();
		buttonAtlas = new TextureAtlas(Gdx.files.internal("TexturesTemp.pack"));
		skin.addRegions(buttonAtlas);
		scrabbleButtonStyle = new ScrabbleButtonStyle();		
		scrabbleButtonStyle.up = skin.getDrawable("green");
		scrabbleButtonStyle.checked = skin.getDrawable("orange");
		scrabbleButtonStyle.down = skin.getDrawable("blue");
		scrabbleButtonStyle.over = skin.getDrawable("purple");
		scrabbleButtonStyle.font = font;
		
		
		textButtonStyle2 = new TextButtonStyle();
		imageTextButtonStyle = new ImageTextButtonStyle();
		imageTextButtonStyle.font = font;
	

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
