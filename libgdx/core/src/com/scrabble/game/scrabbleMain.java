package com.scrabble.game;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton.ImageTextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.mygdx.game.ScrabbleButton;
import com.mygdx.game.ScrabbleButton.ScrabbleButtonStyle;

public class MyGdxGame extends Game {
	Stage stage;
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
	tempBoard board = new tempBoard();

	private Table table;
	private Table table2;
	private Table table3;
	private Table table4;
	private Table table5;
	private int[] ignore = new int[2];
	
	private tempBoard temp = tempBoard.getInstance();
	private ScrabbleButton[][] ahh = new ScrabbleButton[100][100];
	
	char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	public static char[] textHolder = new char[7];
	int buttonCount = 0;

	@Override
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

		table.setSize(stage.getWidth(), stage.getHeight());

		// sets up buttons for board
	
		
	

		int k = 0;
		stage.addActor(table);
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				k += 1;
				
				ignore[0] = i;
				ignore[1] = j;
				

			
				ScrabbleButton libgdxsucks = new ScrabbleButton(" ", scrabbleButtonStyle, i, j);	
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

			ScrabbleButton libgdxsucks = new ScrabbleButton(" ", scrabbleButtonStyle, i, 1);	
			libgdxsucks.setSize(36.4f, 36.4f);				
			stage.addActor(libgdxsucks);			
			table2.add(libgdxsucks).size(36.4f, 36.4f);
			
		}
		stage.addActor(table2);

		table3.setSize(table.getWidth(), table.getHeight() + 650);
		for (int i = 0; i < 7; i++) {
			ScrabbleButton libgdxsucks = new ScrabbleButton(" ", scrabbleButtonStyle, i, 1);	
			libgdxsucks.setSize(36.4f, 36.4f);				
			stage.addActor(libgdxsucks);			
			table3.add(libgdxsucks).size(36.4f, 36.4f);
		}
		stage.addActor(table3);

		table4.setSize(table.getWidth() + 800, table.getHeight());
		for (int i = 0; i < 7; i++) {
			ScrabbleButton libgdxsucks = new ScrabbleButton(" ", scrabbleButtonStyle, i, 1);	
			libgdxsucks.setSize(36.4f, 36.4f);				
			stage.addActor(libgdxsucks);			
			table4.add(libgdxsucks).size(36.4f, 36.4f).row();
		}
		stage.addActor(table4);

		table5.setSize(table.getWidth() - 800, table.getHeight());
		for (int i = 0; i < 7; i++) {
			ScrabbleButton libgdxsucks = new ScrabbleButton(" ", scrabbleButtonStyle, i, 1);	
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
		scrabbleButtonStyle.font = font;
		
		
		textButtonStyle2 = new TextButtonStyle();
		imageTextButtonStyle = new ImageTextButtonStyle();
		imageTextButtonStyle.font = font;
	

	}
	
	
	public void render() {      
        super.render();
        stage.draw();
        stage.act();
	}
	
}