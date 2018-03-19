package data;

import player.Player;
import scrabble.Board;

import java.util.HashMap;

public class AIMove extends Move {
    public AIMove(Player player) {
        super(player);
    }

    /**
     * Add a Tile to Move (as in Tile has been played in this Move).
     *
     * @param   tile          Tile that's been played
     * @param   coordinate    Coordinate of played Tile
     */
    public void addTile(Tile tile, Coordinate coordinate) {
        Board.getInstance().place(tile, coordinate);
        playedTiles.put(tile, coordinate);
        player.removeTile(tile);
    }

    /**
     * Removes Tile from Move (Tile has been returned to Player hand and has not been played)
     *
     * @param   tile          Tile to remove
     */
    public void removeTile(Tile tile) {
        if(playedTiles.containsKey(tile)) {
            moveScore -= SCORE_CALCULATOR.calculateScore(tile, playedTiles.get(tile));
            Board.getInstance().removeTile(playedTiles.get(tile));
            player.returnTile(tile);
            playedTiles.remove(tile);
        }
    }

    public void setScore(int score) {
        moveScore = score;
    }
}
