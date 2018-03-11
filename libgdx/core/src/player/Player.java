package player;

import scrabble.Tile;
import scrabble.LetterBag;
import scrabble.Board;

/**
 * Superclass containing shared methods related to player component of game.
 * @author Thomas Geraghty
 * @version 1.1
 */
public abstract class Player {

	private final Tile[] letterList = new Tile[7];
    private String playerName;
    private int totalScore = 0;
    private int currentMoveScore = 0;
    private String lastPlayedWord;


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
        totalScore = score;
    }

    /**
     * Returns player's score on call.
     * @return  score   Score represented as integer.
     * @see         #setScore(int)
     */
    public int getScore() {
       return totalScore;
    }

    public int getMoveScore() {
        return currentMoveScore;
    }
    public void setMoveScore(int score) {
        currentMoveScore = score;
    }
    
    public void setLastWord(String last){
    	 lastPlayedWord =  last;
    }
    
    /*
     * needed for the UI
     */ 
    public Tile getLetter(int x){
    	return letterList[x];
    }
    
    public String getLastWord(){
    	return lastPlayedWord;
    }

    /**
     *  Adds letter to players current collection of letters (letterList array).
     *  Adds letter in first position free in players letter collection.
     *  Gets chosen letter from LetterBag 'pick' method..
     *
     *  @see    LetterBag#pick()
     */
    public void addLetters() {
        for(int i = 0; i < letterList.length; i++) {
            if(letterList[i] == null) {
                letterList[i] = LetterBag.getInstance().pick();
                letterList[i].setPlayer(this);
            }
        }
    }

    public void returnletter(Tile tile) {
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

    public void selectedLetter(Tile tile, int x, int y) {
    }
}
