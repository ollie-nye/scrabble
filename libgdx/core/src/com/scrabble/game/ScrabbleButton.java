package com.scrabble.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;

public class ScrabbleButton extends ButtonBase {

	private final Label label;
	private ScrabbleButtonStyle style;

	public ScrabbleButton (ScrabbleButtonStyle style) {
		super();
		setStyle(style);
		this.style = style;
		label = new Label(" ", new LabelStyle(style.font, style.fontColor));
		label.setAlignment(Align.center);
		add(label).expand().fill();
		setSize(getPrefWidth(), getPrefHeight());
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
        super.draw(batch, parentAlpha);
	}

	public void fontColour() {
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