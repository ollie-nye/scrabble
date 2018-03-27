package data.move;

import data.Coordinate;
import data.Tile;
import player.Player;
import scrabble.Board;
import scrabble.Game;
import scrabble.Score;

import java.io.Serializable;
import java.util.*;

/**
 * move object contains all played letters in a turn (move), the word played,
 * the current score of the turn and who played the turn.
 *
 * @author Tom Geraghty
 * @version 1.2
 */
public abstract class Move implements Serializable {
    final static Score SCORE_CALCULATOR = new Score();
    final HashMap<Tile, Coordinate> playedTiles = new HashMap<>();
    final Board board = Board.getInstance();
    final Player player;
    String playedWord = "";
    int wordMultiplier = 1;
    int moveTime;
    int moveScore;


    public Move(Player player) {
        this.player = player;
    }

    /* PLAYED TILES */

    public abstract void addTile(Tile tile, Coordinate coordinate);

    public abstract void removeTile(Tile tile);

    /**
     * Returns all Tiles played in this move.
     *
     * @return tiles        Tiles played in this move.
     */
    public HashMap<Tile, Coordinate> getPlayedTiles() {
        return playedTiles;
    }


    /* PLAYED WORD */

    /**
     * Set the word played in this move.
     *
     * @param playedWord The word played in this move.
     */
    public void setPlayedWord(String playedWord) {
        this.playedWord = playedWord;
    }

    /**
     * Returns the word played in this move
     *
     * @return word               The played word
     */
    public String getPlayedWord() {
        return playedWord;
    }


    /* PLAYER */

    /**
     * Returns the Player of this move.
     *
     * @return player      Player who playing/played this move.
     */
    public Player getPlayer() {
        return player;
    }


    /* SCORE */

    /**
     * Returns current score of move.
     *
     * @return score       Current score of move.
     */
    public int getMoveScore() {
        return moveScore * wordMultiplier;
    }

    /* MOVE FUNCTIONS */
    public void endMove() {
        moveTime = Game.getTimer().getTime();
        for (Coordinate coordinate : playedTiles.values()) {
            moveScore += calculateConnectedTileScores(coordinate);
        }
        player.setScore(player.getScore() + getMoveScore());
        player.addTiles();
    }

    private int calculateConnectedTileScores(Coordinate coordinate) {
        Coordinate tempCoordinate = coordinate.getNear('U');
        int tempScore = 0;

        if (!playedTiles.containsKey(board.getTile(tempCoordinate)) && tempCoordinate.getY() >= 0 && board.getTile(tempCoordinate) != null) {
            tempScore += board.getTile(tempCoordinate).getScore();
        }

        tempCoordinate = coordinate.getNear('D');

        if (!playedTiles.containsKey(board.getTile(tempCoordinate)) && tempCoordinate.getY() <= 14 && board.getTile(tempCoordinate) != null) {
            tempScore += board.getTile(tempCoordinate).getScore();
        }

        tempCoordinate = coordinate.getNear('L');

        if (!playedTiles.containsKey(board.getTile(tempCoordinate)) && tempCoordinate.getX() >= 0 && board.getTile(tempCoordinate) != null) {
            tempScore += board.getTile(tempCoordinate).getScore();
        }

        tempCoordinate = coordinate.getNear('R');

        if (!playedTiles.containsKey(board.getTile(tempCoordinate)) && tempCoordinate.getX() <= 14 && board.getTile(tempCoordinate) != null) {
            tempScore += board.getTile(tempCoordinate).getScore();
        }

        return tempScore;
    }

    public void invalidateMove() {
        moveScore = 0;
        playedWord = "";
        moveTime = Game.getTimer().getTime();

        HashMap<Tile, Coordinate> tempMap = new HashMap<>(playedTiles);
        for (Tile tile : tempMap.keySet()) {
            removeTile(tile);
        }
    }

    public int getMoveTime() {
        return moveTime;
    }
}
