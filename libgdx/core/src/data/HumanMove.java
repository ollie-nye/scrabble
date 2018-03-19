package data;

import player.Player;
import scrabble.Board;
import validation.NewValidator;

import java.util.HashMap;

public class HumanMove extends Move {
    private NewValidator validator = new NewValidator(Board.getInstance());

    public HumanMove(Player player) {
        super(player);
    }

    /**
     * Add a Tile to Move (as in Tile has been played in this Move).
     *
     * @param   tile          Tile that's been played
     * @param   coordinate    Coordinate of played Tile
     */
    public void addTile(Tile tile, Coordinate coordinate) {
        Result placeResult = validator.validateMove(new Letter(tile, coordinate));
        if (placeResult.isLegal()) {
            Board.getInstance().place(tile, coordinate);
            playedTiles.put(tile, coordinate);
            player.removeTile(tile);
            moveScore += SCORE_CALCULATOR.calculateScore(tile, coordinate);
            wordMultiplier *= SCORE_CALCULATOR.getWordMultiplier(coordinate);
        } else {
            Board.getInstance().removeTile(coordinate);
        }
        Board.getInstance().resetPartial();
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
            if (playedTiles.size() == 1) { //Direction is no longer set
                this.validator.resetDirection();
            } else if (playedTiles.size() == 0) { //First place is no longer set
                this.validator = new NewValidator(Board.getInstance());
            }
        }
    }

    public Result getResult() {
        return validator.getLastResult();
    }
}
