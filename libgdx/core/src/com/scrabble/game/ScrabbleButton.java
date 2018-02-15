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

/** i should really get round to annotating this shit*/
public class ScrabbleButton extends Button {
	private final Label label;
	private ScrabbleButtonStyle style;
	private static int iterator; 
	private static String letterToPlace;
	private int id;
	private int type;//0 for main board tile... other to be decided
	private int[] tilePos;
    private int xCoor;
    private int yCoor;
	

	public ScrabbleButton (String text, ScrabbleButtonStyle style, int x, int y) {
		super();
		setStyle(style);
		this.style = style;
		label = new Label(tempBoard.getInstance().getLetter(x, y), new LabelStyle(style.font, style.fontColor));
		label.setAlignment(Align.center);
		add(label).expand().fill();
		setSize(getPrefWidth(), getPrefHeight());
		iterator += 1;
		xCoor = x;
		yCoor = y;

		
		
		
	}

	public void setStyle (ButtonStyle style) {
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

	public void toPlace(String letter){
		letterToPlace = letter;
	}	
	
	public ScrabbleButtonStyle getStyle () {
		return style;
	}

	public void draw (Batch batch, float parentAlpha) {
		
		setText(tempBoard.getInstance().getLetter(xCoor,yCoor));
		
		if (isPressed()){
			tempBoard.getInstance().changeLetter(xCoor, yCoor);
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

	//definitely not copy and pasted
	static public class ScrabbleButtonStyle extends ButtonStyle {
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