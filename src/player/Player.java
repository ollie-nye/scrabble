package player;

import java.util.Random;
import scrabble.Tile;
import scrabble.LetterBag;
import scrabble.Board;

/**
 * Superclass containing shared methods related to player component of game.
 * @author Thomas Geraghty
 * @version 1.1
 */
public abstract class  Player {

	private Tile[] letterList = new Tile[7];
    private Random rnd = new Random();
    private String playerName;
    private int score = 0;
    
    
    public Player() {
    	for (int i = 0; i < letterList.length; i++) {
    		letterList[i] = LetterBag.getInstance().pick();
    	}
    }


    /**
     * Sets player username to appear in game.
     * @see     #getPlayerName()
     */
    public void setPlayerName(String name) {
        playerName = name;
    }
    
    /**
     * Returns player username upon call
     * @return  name    Player username String
     * @see     #setPlayerName(String)
     */
    public String getPlayerName() {
        return playerName;
    }

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

    /**
     *  Adds letter to players current collection of letters (letterList array).
     *  Adds letter in first position free in players letter collection.
     *  Gets chosen letter from LetterBag 'pick' method..
     *
     *  @see    LetterBag#pick()
     */
    public void addLetter() {
        for(int i = 0; i < letterList.length - 1; i++) {
            if(letterList[i] == null) {
                letterList[i] = LetterBag.getInstance().pick();
            }
        }
    }

    /**
     * Sorts through array to find parameter tile then removes it from user array of tiles.
     * @param tile      Tile to remove
     */
    public void removeLetter(Tile tile) {
        for(int i = 0; i < letterList.length; i++) {
	    if (letterList[i] != null) {
            	if(letterList[i].getContent().equals(tile.getContent())) {
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
    public Tile[] getLetterList() {
        return letterList;
    }

    public void getLetter() {
    }

    /**
     * Plays chosen tile to board.
     * Removes tile from players selection of tiles.
     * @param tile  Tile to play
     * @param x     Horizontal position
     * @param y     Vertical position
     */
    public void playLetter(Tile tile, int x, int y) {
        Board.getInstance().place(tile, x, y);
        removeLetter(tile);
    }

    public void selectedLetter(Tile tile, int x, int y) {
    }
}
