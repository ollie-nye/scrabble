package com.scrabble.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import data.Coordinate;
import scrabble.Board;

/**
 * @author Tom Geraghty
 * @version 1.0
 */
public class BoardButton extends ScrabbleButton {
    private Coordinate coordinate;
    private boolean isPressed = false;

    public BoardButton(ScrabbleButtonStyle style, Coordinate coordinate) {
        super(style);
        this.coordinate = coordinate;
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        // BOARD - Setting the text for the tiles on the board
        if (Board.getInstance().getTile(coordinate) == null){
            setText(" ");
        } else {
            setText(Board.getInstance().getTile(coordinate).getContent());
        }

        //placing the tile
        if (isPressed() && !isPressed) {
            if(Board.getInstance().getTile(coordinate) == null) {
                Board.getInstance().partialPlace(coordinate.getX(), coordinate.getY());
            } else {
                Board.getInstance().removeTile(coordinate);
            }
            isPressed = true;
        }

        if (!isPressed()){
            isPressed = false;
        }

        fontColour();
        super.draw(batch, parentAlpha);
    }
}
