package com.scrabble.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import data.Coordinate;
import player.AIPlayer;
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
    private final int playerNumber;

    /**
     * Calls super (ScrabbleButton), passing the Style to it.
     * Sets Button Coordinate to passed Coordinate
     *
     * @param style             Style of button
     * @param coordinate        Coordinate of button
     * @param playerNumber      Player who button belongs too (remove 1 from number)
     */
    public PlayerButton(ScrabbleButtonStyle style, Coordinate coordinate, int playerNumber) {
        super(style);

        this.playerNumber = playerNumber - 1;
        this.coordinate = coordinate;
    }

    /**
     * Draws button. Deals with clicks/presses and calls corresponding logic.
     *
     * @param batch
     * @param parentAlpha
     */
    @Override
	public void draw(Batch batch, float parentAlpha) {
        // Setting the text for tiles in a players hand
        if (Game.getPlayers().get(playerNumber).getTiles()[coordinate.getX()] == null) {
            setText("");
            score.setText("");
        } else {
            if (Game.getPlayers().get(playerNumber) == Game.getCurrentPlayer() && Game.getCurrentMove() != null) {
                setText(Game.getPlayers().get(playerNumber).getTiles()[coordinate.getX()].getContent());
                score.setText(Integer.toString(Game.getCurrentPlayer().getTiles()[coordinate.getX()].getScore()));
            } else {
                setText("");
                score.setText("");
            }
        }

        if(!(Game.getCurrentPlayer() instanceof AIPlayer || Game.getCurrentMove() == null)) {
            placeTile();
        }

        if (!isPressed()) {
            isPressed = false;
        }

        fontColour();
        super.draw(batch, parentAlpha);
    }

    public void placeTile() {
        // Placing the tile
        if (Game.getPlayers().get(playerNumber) == Game.getCurrentPlayer()) {
            if (isPressed() && !isPressed) {
                Board.getInstance().partialPlace(Game.getCurrentPlayer().getTiles()[coordinate.getX()]);
            }
            isPressed = true;
        }
    }
}
