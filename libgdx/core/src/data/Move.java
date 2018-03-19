package data;

import player.Player;
import scrabble.Score;
import java.util.HashMap;

/**
 * Move object contains all played letters in a turn (move), the word played,
 * the current score of the turn and who played the turn.
 * @author Tom Geraghty
 * @version 1.2
 */
public abstract class Move {
    final static Score SCORE_CALCULATOR = new Score();
    final static HashMap<Tile, Coordinate> playedTiles = new HashMap<>();
    final Player player;
    String playedWord = "";
    int wordMultiplier = 1;
    int moveTime;
    int moveScore;


    public Move(Player player) {
        this.player = player;
        new Thread(new Timer()).start();
    }

    /* PLAYED TILES */

    public abstract void addTile(Tile tile, Coordinate coordinate);
    public abstract void removeTile(Tile tile);

    /**
     * Returns all Tiles played in this Move.
     *
     * @return  tiles        Tiles played in this Move.
     */
    public HashMap<Tile, Coordinate> getPlayedTiles() {
        return playedTiles;
    }


    /* PLAYED WORD */
    /**
     * Set the word played in this Move.
     *
     * @param   playedWord      The word played in this Move.
     */
    public void setPlayedWord(String playedWord) {
        this.playedWord = playedWord;
    }
    /**
     * Returns the word played in this Move
     *
     * @return  word               The played word
     */
    public String getPlayedWord() {
        return playedWord;
    }


    /* PLAYER */
    /**
     * Returns the Player of this Move.
     *
     * @return  player      Player who playing/played this Move.
     */
    public Player getPlayer() {
        return player;
    }


    /* SCORE */
    /**
     * Returns current score of Move.
     *
     * @return  score       Current score of Move.
     */
    public int getMoveScore() {
        return moveScore * wordMultiplier;
    }

    /* MOVE FUNCTIONS */
    public void endMove() {
        moveTime = Timer.getTime();
        this.playedWord = playedWord;
        player.setScore(player.getScore() + getMoveScore());
        player.addTiles();
    }
    public void invalidateMove() {
        moveScore = 0;
        playedWord = "";
        moveTime = Timer.getTime();

        HashMap<Tile, Coordinate> tempMap = new HashMap<>(playedTiles);
        for(Tile tile : tempMap.keySet()) {
            removeTile(tile);
        }
    }

    public int getMoveTime() {
        return moveTime;
    }
}
