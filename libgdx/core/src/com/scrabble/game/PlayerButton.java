package com.scrabble.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import data.Coordinate;
import scrabble.Board;
import scrabble.Game;
import screens.screens.GameScreen;

/**
 * @author Tom Geraghty
 * @version 1.0
 */
public class PlayerButton extends ScrabbleButton {
    private Coordinate coordinate;
    private boolean isPressed = false;
    private int playerNumber;

    public PlayerButton(ScrabbleButtonStyle style, Coordinate coordinate, int playerNumber) {
        super(style);

        this.playerNumber = playerNumber - 1;
        this.coordinate = coordinate;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // PLAYER - Setting the text for tiles in a players hand
        if (Game.getPlayers().get(playerNumber).getLetters()[coordinate.getX()] == null) {
            setText("");
            score.setText("");
        } else {
            if (Game.getPlayers().get(playerNumber) == Game.getCurrentPlayer() && !GameScreen.passingOverTurn) {
                setText(Game.getPlayers().get(playerNumber).getLetters()[coordinate.getX()].getContent());
                score.setText(Integer.toString(Game.getCurrentPlayer().getLetters()[coordinate.getX()].getScore()));
            } else {
                setText("");
                score.setText("");
            }
        }

        //placing the tile
        if (Game.getPlayers().get(playerNumber) == Game.getCurrentPlayer()) {
            if (isPressed() && !isPressed) {
                Board.getInstance().partialPlace(Game.getCurrentPlayer().getLetters()[coordinate.getX()]);
            }
            isPressed = true;
        }

        if (!isPressed()) {
            isPressed = false;
        }

        fontColour();
        super.draw(batch, parentAlpha);
    }
}
