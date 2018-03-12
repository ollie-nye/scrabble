package data;

import player.Player;
import scrabble.Score;

import java.util.HashMap;

/**
 * @author Tom Geraghty
 * @version 1.0
 */
public class Move {
    private final static Score SCORE_CALCULATOR = new Score();
    private final HashMap<Tile, Coordinate> playedTiles = new HashMap<>();
    private Player player;
    private String playedWord;
    private int moveScore;

    public Move(Player player) {
        this.player = player;
    }

    public void addTile(Tile tile, Coordinate coordinate) {
        playedTiles.put(tile, coordinate);
        moveScore += SCORE_CALCULATOR.calculateScore(new Letter(tile, coordinate));
    }

    public void removeTile(Tile tile, Coordinate coordinate) {
        playedTiles.remove(coordinate);
        moveScore -= SCORE_CALCULATOR.calculateScore(new Letter(tile, coordinate));
    }

    public HashMap<Tile, Coordinate> getPlayedTiles() {
        return playedTiles;
    }

    public int getMoveScore() {
        return moveScore;
    }

    public void setPlayedWord(String playedWord) {
        this.playedWord = playedWord;
    }

    public String getPlayedWord() {
        return playedWord;
    }

    public Player getPlayer() {
        return player;
    }
}
