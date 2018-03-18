package data;

import player.Player;
import scrabble.Board;
import scrabble.Score;
import validation.NewValidator;
import java.util.HashMap;

/**
 * Move object contains all played letters in a turn (move), the word played,
 * the current score of the turn and who played the turn.
 * @author Tom Geraghty
 * @version 1.2
 */
public class Move {
    private final static Score SCORE_CALCULATOR = new Score();
    private final HashMap<Tile, Coordinate> playedTiles = new HashMap<>();
    private NewValidator validator = new NewValidator(Board.getInstance());
    private final Player player;
    private String playedWord = "";
    private int wordMultiplier = 1;
    private int moveTime;
    private int moveScore;


    public Move(Player player) {
        this.player = player;
        new Thread(new Timer()).start();
    }

    /* PLAYED TILES */
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
    public Result getResult() {
        return validator.getLastResult();
    }
}
