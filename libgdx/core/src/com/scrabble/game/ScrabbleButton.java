package com.scrabble.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;

import player.PlayersContainer;
import scrabble.Board;
import scrabble.Scrabble;
public class ScrabbleButton extends ButtonBase {
	private final Label label;
	private ScrabbleButtonStyle style;
	private int boardOrPlayer;
	private int xCoor;
	private int yCoor;
	private boolean isPressed = false;	

	

	public ScrabbleButton (String text, ScrabbleButtonStyle style, int xCoor, int yCoor, int boardOrPlayer) {
		super();
		setStyle(style);
		this.style = style;
		label = new Label(" ", new LabelStyle(style.font, style.fontColor));
		label.setAlignment(Align.center);
		add(label).expand().fill();
		setSize(getPrefWidth(), getPrefHeight());		
		this.xCoor = xCoor;
		this.yCoor = yCoor;
		this.boardOrPlayer = boardOrPlayer;
	}

	public void setStyle (ButtonBaseStyle style) {
		if (style == null) throw new NullPointerException("style cannot be null");
		if (!(style instanceof ScrabbleButtonStyle)) throw new IllegalArgumentException("style must be a ScrabbleButtonStyle.");
		super.setStyle(style);
		this.style = (ScrabbleButtonStyle)style;
		if (label != null) {
			ScrabbleButtonStyle ScrabbleButtonStyle = (ScrabbleButtonStyle)style;
			LabelStyle labelStyle = label.getStyle();
			labelStyle.font = ScrabbleButtonStyle.font;
			labelStyle.fontColor = ScrabbleButtonStyle.fontColor;
			label.setStyle(labelStyle);
		}
	}

	public ScrabbleButtonStyle getStyle () {
		return style;
	}

	public void draw (Batch batch, float parentAlpha) {
	
		/*
		 * BOARD
		 * Setting the text for the tiles on the board
		 */
		if (boardOrPlayer == 0){
			
			if (Board.getInstance().getTile(xCoor, yCoor) == null){
				setText(" ");				
			}else{
				setText(Board.getInstance().getTile(xCoor, yCoor).getContent());
			}
			
		}
		
		/*
		 * PLAYER
		 * Setting the text for tiles in a players hand
		 */
		else if (boardOrPlayer >= 1){
			
			if (PlayersContainer.getInstance().getPlayer(boardOrPlayer-1).getLetter(xCoor) == null){
				setText(" ");				
			} else if (boardOrPlayer-1 == Scrabble.currentPlayer){
				setText(PlayersContainer.getInstance().getPlayer(boardOrPlayer-1).getLetter(xCoor).getContent());
			} else{
				setText(" ");
			}
			
			
		} 
		
		/*
		 * ERROR CATCH
		 */
		else {
			
		System.out.println("Error in getting letter for player tiles");
		
		}
		
		//placing the tiles
		if (isPressed() && isPressed == false){
			
			/*
			 * PLAYER
			 */
			if (boardOrPlayer > 0 && boardOrPlayer - 1 == Scrabble.currentPlayer){
				Board.getInstance().partialPlace(PlayersContainer.getInstance().getPlayer(boardOrPlayer-1).getLetter(xCoor));
			}
			
			/*
			 * BOARD
			 */
			else{				
				Board.getInstance().partialPlace(xCoor, yCoor);
			}
			isPressed = true;
			
		}
		
		if (isPressed() == false){			
			isPressed = false;			
		}
		
		Color fontColor = null;
		if (isDisabled() && style.disabledFontColor != null){
			fontColor = style.disabledFontColor;
		}
		else if (isPressed() && style.downFontColor != null){				
			fontColor = style.downFontColor;			
		}
		else if (isChecked() && style.checkedFontColor != null){				
			fontColor = (isOver() && style.checkedOverFontColor != null) ? style.checkedOverFontColor : style.checkedFontColor;
				}
		else if (isOver() && style.overFontColor != null){
			fontColor = style.overFontColor;
		}
		else{			
			fontColor = style.fontColor;
		}
		if (fontColor != null) label.getStyle().fontColor = fontColor;
			
		super.draw(batch, parentAlpha);
		
		
	}

	public Label getLabel () {
		return label;
	}
	

	public void setText (String c) {
		label.setText(c);
	}

	public CharSequence getText () {
		return label.getText();
	}

	/** The style for a text button, see {@link ScrabbleButton}.
	 * @author Nathan Sweet */
	static public class ScrabbleButtonStyle extends ButtonBaseStyle {
		public BitmapFont font;
		/** Optional. */
		public Color fontColor, downFontColor, overFontColor, checkedFontColor, checkedOverFontColor, disabledFontColor;

		public ScrabbleButtonStyle () {
		}

		public ScrabbleButtonStyle (Drawable up, Drawable down, Drawable checked, BitmapFont font) {
			super(up, down, checked);
			this.font = font;
		}

		public ScrabbleButtonStyle (ScrabbleButtonStyle style) {
			super(style);
			this.font = style.font;
			if (style.fontColor != null) this.fontColor = new Color(style.fontColor);
			if (style.downFontColor != null) this.downFontColor = new Color(style.downFontColor);
			if (style.overFontColor != null) this.overFontColor = new Color(style.overFontColor);
			if (style.checkedFontColor != null) this.checkedFontColor = new Color(style.checkedFontColor);
			if (style.checkedOverFontColor != null) this.checkedOverFontColor = new Color(style.checkedOverFontColor);
			if (style.disabledFontColor != null) this.disabledFontColor = new Color(style.disabledFontColor);
		}
	}
}