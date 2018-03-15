package com.scrabble.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import data.Coordinate;
import player.AIPlayer;
import player.HumanPlayer;
import player.Player;
import scrabble.Board;
import scrabble.Game;

/**
 * Subclass of ScrabbleButton, is used for buttons / tiles in Players hand.
 * @author Tom Geraghty
 * @version 1.0
 */
public class PlayerButton extends ScrabbleButton {
    private final Coordinate coordinate;
    private boolean isPressed = false;
    private final Player player;

    /**
     * Calls super (ScrabbleButton), passing the Style to it.
     * Sets Button Coordinate to passed Coordinate
     *
     * @param style             Style of button
     * @param coordinate        Coordinate of button
     */
    public PlayerButton(ScrabbleButtonStyle style, Coordinate coordinate, Player player) {
        super(style);
        this.coordinate = coordinate;
        this.player = player;
    }

    /**
     * Draws button. Deals with clicks/presses and calls corresponding logic.
     *
     * @param batch
     * @param parentAlpha
     */
    @Override
	public void draw(Batch batch, float parentAlpha) {
        if(Game.getCurrentMove() != null) {
            drawTileContent();
            if (isPressed() && !isPressed) {
                placeTile();
            }
        } else {
            setText("");
            score.setText("");
        }
        if (!isPressed()) {
            isPressed = false;
        }
        fontColour();
        super.draw(batch, parentAlpha);
    }

    private void placeTile() {
        // Placing the tile
        if (player == Game.getCurrentPlayer() && player instanceof HumanPlayer) {
            Board.getInstance().partialPlace(Game.getCurrentPlayer().getTiles()[coordinate.getX()]);
            isPressed = true;
        } else {
            Board.getInstance().resetPartial();
        }
    }

    private void drawTileContent() {
        if(player == Game.getCurrentPlayer()) {
            if (player.getTiles()[coordinate.getX()] == null) {
                setText("");
                score.setText("");
            } else {
                setText(player.getTiles()[coordinate.getX()].getContent());
                score.setText(Integer.toString(player.getTiles()[coordinate.getX()].getScore()));
            }
        }
    }
}
