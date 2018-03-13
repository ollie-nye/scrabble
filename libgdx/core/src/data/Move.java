package data;

import player.Player;
import scrabble.Score;

import java.util.HashMap;

/**
 * @author Tom Geraghty
 * @version 1.1
 */
public class Move {
    private final static Score SCORE_CALCULATOR = new Score();
    private final HashMap<Tile, Coordinate> playedTiles = new HashMap<>();
    private Player player;
    private String playedWord;
    private int moveScore;
    private int wordMultiplier = 1;

    public Move(Player player) {
        this.player = player;
    }

    /**
     * Add a Tile to Move (as in Tile has been played in this Move).
     *
     * @param   tile          Tile that's been played
     * @param   coordinate    Coordinate of played Tile
     */
    public void addTile(Tile tile, Coordinate coordinate) {
        playedTiles.put(tile, coordinate);
        moveScore += SCORE_CALCULATOR.calculateScore(tile, coordinate);
        wordMultiplier *= SCORE_CALCULATOR.getWordMultiplier(coordinate);
    }

    /**
     * Removes Tile from Move (Tile has been returned to Player hand and has not been played)
     *
     * @param   tile          Tile to remove
     * @param   coordinate    Coordinate of Tile to remove.
     */
    public void removeTile(Tile tile, Coordinate coordinate) {
        playedTiles.remove(tile);
        moveScore -= SCORE_CALCULATOR.calculateScore(tile, coordinate);
    }

    /**
     * Returns all Tiles played in this Move.
     *
     * @return  tiles        Tiles played in this Move.
     */
    public HashMap<Tile, Coordinate> getPlayedTiles() {
        return playedTiles;
    }

    /**
     * Returns current score of Move.
     *
     * @return  score       Current score of Move.
     */
    public int getMoveScore() {
        return moveScore * wordMultiplier;
    }

    /**
     * Set the word played in this Move.
     *
     * @param   playedWord      The word played in this Move.
     */
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
