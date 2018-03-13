package com.scrabble.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;

/*
 * an adapted version of the button class
 */
public class ButtonBase extends Table implements Disableable {
	private ButtonBaseStyle style;
	boolean isChecked, isDisabled;
	ButtonGroup buttonGroup;
	private ClickListener clickListener;
	private boolean programmaticChangeEvents = true;
	private static boolean anyButtonSelected = false;
	private boolean firstPress = false;

	
	/*
	 * setting up the sounds
	 */
	
	
	
	

	public ButtonBase (Skin skin) {
		super(skin);
		initialize();
		setStyle(skin.get(ButtonBaseStyle.class));
		setSize(getPrefWidth(), getPrefHeight());
	}

	public ButtonBase (Skin skin, String styleName) {
		super(skin);
		initialize();
		setStyle(skin.get(styleName, ButtonBaseStyle.class));
		setSize(getPrefWidth(), getPrefHeight());
	}

	public ButtonBase (Actor child, Skin skin, String styleName) {
		this(child, skin.get(styleName, ButtonBaseStyle.class));
		setSkin(skin);
	}

	public ButtonBase(Actor child, ButtonBaseStyle style) {
		initialize();
		add(child);
		setStyle(style);
		setSize(getPrefWidth(), getPrefHeight());
	}

	public ButtonBase(ButtonBaseStyle style) {
		initialize();
		setStyle(style);
		setSize(getPrefWidth(), getPrefHeight());
	}
	
	private void selectButtons(){
		anyButtonSelected = true;
	}
	
	private void deselectButtons(){
		anyButtonSelected = false;
		toggle();
	}

	/** Creates a button without setting the style or size. At least a style must be set before using this button. */
	public ButtonBase() {
		initialize();
	}

	private void initialize () {
		setTouchable(Touchable.enabled);
		addListener(clickListener = new ClickListener() {
			public void clicked (InputEvent event, float x, float y) {
				if (isDisabled()) return;
				setChecked(!isChecked, true);
			}
		});
	}

	public ButtonBase(Drawable up) {
		this(new ButtonBaseStyle(up, null, null));
	}

	public ButtonBase(Drawable up, Drawable down) {
		this(new ButtonBaseStyle(up, down, null));
	}

	public ButtonBase(Drawable up, Drawable down, Drawable checked) {
		this(new ButtonBaseStyle(up, down, checked));
	}

	public ButtonBase (Actor child, Skin skin) {
		this(child, skin.get(ButtonBaseStyle.class));
	}

	public void setChecked (boolean isChecked) {
		setChecked(isChecked, programmaticChangeEvents);
	}

	void setChecked (boolean isChecked, boolean fireEvent) {
		if (this.isChecked == isChecked) return;
		this.isChecked = isChecked;
		if (fireEvent) {
			ChangeEvent changeEvent = Pools.obtain(ChangeEvent.class);
			if (fire(changeEvent)) this.isChecked = !isChecked;
			Pools.free(changeEvent);
		}
	}

	/** Toggles the checked state. This method changes the checked state, which fires a {@link ChangeEvent} (if programmatic change
	 * events are enabled), so can be used to simulate a button click. */
	public void toggle () {
		setChecked(!isChecked);
	}

	public boolean isChecked () {
		return isChecked;
	}

	public boolean isPressed () {
		return clickListener.isVisualPressed();
	}

	public boolean isOver () {
		return clickListener.isOver();
	}

	public ClickListener getClickListener () {
		return clickListener;
	}

	public boolean isDisabled () {
		return isDisabled;
	}

	/** When true, the button will not toggle {@link #isChecked()} when clicked and will not fire a {@link ChangeEvent}. */
	public void setDisabled (boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	/** If false, {@link #setChecked(boolean)} and {@link #toggle()} will not fire {@link ChangeEvent}, event will be fired only
	 * when user clicked the button */
	public void setProgrammaticChangeEvents (boolean programmaticChangeEvents) {
		this.programmaticChangeEvents = programmaticChangeEvents;
	}

	public void setStyle (ButtonBaseStyle style) {
		if (style == null) throw new IllegalArgumentException("style cannot be null.");
		this.style = style;

		Drawable background = null;
		if (isPressed() && !isDisabled()) {
			background = style.down == null ? style.up : style.down;
		} else {
			if (isDisabled() && style.disabled != null)
				background = style.disabled;
			else if (isChecked && style.checked != null)
				background = (isOver() && style.checkedOver != null) ? style.checkedOver : style.checked;
			else if (isOver() && style.over != null)
				background = style.over;
			else
				background = style.up;
		}
		setBackground(background);
	}

	/** Returns the button's style. Modifying the returned style may not have an effect until is
	 * called. */
	public ButtonBaseStyle getStyle () {
		return style;
	}

	/** @return May be null. */
	public ButtonGroup getButtonGroup () {
		return buttonGroup;
	}

	public void draw (Batch batch, float parentAlpha) {
		validate();

		boolean isDisabled = isDisabled();
		boolean isPressed = isPressed();
		boolean isChecked = isChecked();
		boolean isOver = isOver();
		

		Drawable background = null;
		if(isPressed && firstPress == false && anyButtonSelected == false){
			firstPress = true;
			selectButtons();
		}
		else if(isPressed == false && firstPress == true){
			firstPress = false;
		}
		else if(isPressed && firstPress == false && anyButtonSelected == true){
			firstPress = true;
			deselectButtons();			


			
		}
		
		if(isChecked && anyButtonSelected == false){
			toggle();
		}		

		if (isDisabled && style.disabled != null)
			background = style.disabled;
			
		else if (isPressed && style.down != null){
			background = style.down;
		}
		else if (isPressed && style.down != null){
			background = style.down;
			deselectButtons();
		}
		else if (isChecked && style.checked != null){
			background = (style.checkedOver != null && isOver) ? style.checkedOver : style.checked;	
		}
		else if (isOver && style.over != null)
			background = style.over;
		else if (style.up != null) //
			background = style.up;
		setBackground(background);

		float offsetX = 0f, offsetY = 0;
		if (isPressed && !isDisabled) {
			offsetX = style.pressedOffsetX;
			offsetY = style.pressedOffsetY;
		} else if (isChecked && !isDisabled) {
			offsetX = style.checkedOffsetX;
			offsetY = style.checkedOffsetY;
		} else {
			offsetX = style.unpressedOffsetX;
			offsetY = style.unpressedOffsetY;
		}

		Array<Actor> children = getChildren();
		for (int i = 0; i < children.size; i++)
			children.get(i).moveBy(offsetX, offsetY);
		super.draw(batch, parentAlpha);
		for (int i = 0; i < children.size; i++)
			children.get(i).moveBy(-offsetX, -offsetY);

		Stage stage = getStage();
		if (stage != null && stage.getActionsRequestRendering() && isPressed != clickListener.isPressed())
			Gdx.graphics.requestRendering();
	}

	public float getPrefWidth () {
		float width = super.getPrefWidth();
		if (style.up != null) width = Math.max(width, style.up.getMinWidth());
		if (style.down != null) width = Math.max(width, style.down.getMinWidth());
		if (style.checked != null) width = Math.max(width, style.checked.getMinWidth());
		return width;
	}

	public float getPrefHeight () {
		float height = super.getPrefHeight();
		if (style.up != null) height = Math.max(height, style.up.getMinHeight());
		if (style.down != null) height = Math.max(height, style.down.getMinHeight());
		if (style.checked != null) height = Math.max(height, style.checked.getMinHeight());
		return height;
	}

	public float getMinWidth () {
		return getPrefWidth();
	}

	public float getMinHeight () {
		return getPrefHeight();
	}

	/** The style for a button, see .
	 * @author mzechner */
	static public class ButtonBaseStyle {
		/** Optional. */
		public Drawable up, down, over, checked, checkedOver, disabled;
		/** Optional. */
		public float pressedOffsetX, pressedOffsetY, unpressedOffsetX, unpressedOffsetY, checkedOffsetX, checkedOffsetY;

		public ButtonBaseStyle () {
		}

		public ButtonBaseStyle (Drawable up, Drawable down, Drawable checked) {
			this.up = up;
			this.down = down;
			this.checked = checked;
		}

		public ButtonBaseStyle (ButtonBaseStyle style) {
			this.up = style.up;
			this.down = style.down;
			this.over = style.over;
			this.checked = style.checked;
			this.checkedOver = style.checkedOver;
			this.disabled = style.disabled;
			this.pressedOffsetX = style.pressedOffsetX;
			this.pressedOffsetY = style.pressedOffsetY;
			this.unpressedOffsetX = style.unpressedOffsetX;
			this.unpressedOffsetY = style.unpressedOffsetY;
			this.checkedOffsetX = style.checkedOffsetX;
			this.checkedOffsetY = style.checkedOffsetY;
		}
	}

}
