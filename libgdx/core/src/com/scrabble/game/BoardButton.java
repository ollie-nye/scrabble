package com.scrabble.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import data.Coordinate;
import scrabble.Board;

/**
 * Subclass of ScrabbleButton, is used for buttons / tiles om the board.
 * @author Ben Miller
 * @version 1.0
 */
public class BoardButton extends ScrabbleButton {
    private Coordinate coordinate;
    private boolean isPressed = false;

    /**
     * Calls super (ScrabbleButton), passing the Style to it.
     * Sets Button Coordinate to passed Coordinate
     *
     * @param style             Style of button
     * @param coordinate        Coordinate of button
     */
    public BoardButton(ScrabbleButtonStyle style, Coordinate coordinate) {
        super(style);
        this.coordinate = coordinate;
    }

    /**
     * Draws button. Deals with clicks/presses and calls corresponding logic.
     *
     * @param batch
     * @param parentAlpha
     */
    @Override
	public void draw (Batch batch, float parentAlpha) {
        // Setting the text for the tiles on the board
        if (Board.getInstance().getTile(coordinate) == null){
            setText("");
            score.setText("");
        } else {
            setText(Board.getInstance().getTile(coordinate).getContent());
            score.setText(Integer.toString(Board.getInstance().getTile(coordinate).getScore()));
        }

        placeTile();

        if (!isPressed()){
            isPressed = false;
        }
        fontColour();
        super.draw(batch, parentAlpha);
    }

    public void placeTile() {
        if (isPressed() && !isPressed) {
            if(Board.getInstance().getTile(coordinate) == null) {
                Board.getInstance().partialPlace(coordinate.getX(), coordinate.getY());
            } else {
                Board.getInstance().removeTile(coordinate);
            }
            isPressed = true;
        }
    }
}
