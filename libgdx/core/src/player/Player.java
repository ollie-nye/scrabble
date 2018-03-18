package player;

import data.Tile;
import scrabble.Game;
import scrabble.LetterBag;

/**
 * Superclass containing shared methods related to player component of game.
 * @author Thomas Geraghty
 * @version 1.2
 */
public abstract class Player {

	private final Tile[] letterList = new Tile[7];
    private String playerName;
    private int score = 0;
    private boolean finishedAllTurns = false;

    /* PLAYED NAME */
    /**
     * Sets player username to appear in game.
     * @see     #getPlayerName()
     */
    public void setPlayerName(String name) {
        if(!(name.equals(""))) {
            playerName = name;
        } else {
            playerName = "Player " + Game.getNumberOfPlayers();
        }
    }
    /**
     * Returns player username upon call
     * @return  name    Player username String
     * @see     #setPlayerName(String)
     */
    public String getPlayerName() {
        return playerName;
    }

    /* SCORE */
    /**
     * Set player's score to passed parameter amount..
     * @param   score   Score to set.
     * @see         #getScore()
     */
    public void setScore(int score) {
        this.score = score;
    }
    /**
     * Returns player's score on call.
     * @return  score   Score represented as integer.
     * @see         #setScore(int)
     */
    public int getScore() {
       return score;
    }

    /* TILES IN HAND */
    /**
     *  Adds letter to players current collection of letters (letterList array).
     *  Adds letter in first position free in players letter collection.
     *  Gets chosen letter from LetterBag 'pick' method..
     *
     *  @see    LetterBag#pick()
     */
    public void addTiles() {
        for(int i = 0; i < letterList.length; i++) {
            if(letterList[i] == null) {
                letterList[i] = Game.getLetterBag().pick();
            }
        }
    }
    /**
     * Returns a Tile to the Players hand.
     *
     * @param   tile    Tile to be returned.
     */
    public void returnTile(Tile tile) {
        for(int i = 0; i < letterList.length - 1; i++) {
            if (letterList[i] == null) {
                letterList[i] = tile;
                break;
            }
        }
    }
    /**
     * Sorts through array to find parameter tile then removes it from user array of tiles.
     * @param tile      Tile to remove
     */
    public void removeTile(Tile tile) {
        for(int i = 0; i < letterList.length; i++) {
	        if (letterList[i] != null) {
            	if(letterList[i] == tile) {
                	letterList[i] = null;
                	break;
	    	    }
            }
        }
    }

    /**
     * Returns array of tiles that Player has.
     * @return
     */
    public Tile[] getTiles() {
        return letterList;
    }
    
    public boolean allTurnsFinished(){
    	return finishedAllTurns;
    }
    
    public void finishedAllTurns(){
    	finishedAllTurns = true;
    }

    public abstract void play();
}
