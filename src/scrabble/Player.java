package scrabble;
import java.util.Random;

/**
 * Superclass containing shared methods related to player component of game.
 * @author Thomas Geraghty
 * @version 1.1
 */
abstract class  Player {

	private String[] letterList = new String[7];
    private Random rnd = new Random();
    private String playerName;
    private int score = 0;


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
        for(int i = 0; i < letterList.length; i++) {
            if(letterList[i]  == null) {
                letterList[i] = LetterBag.getInstance().pick();
                break;
            }
        }
    }

	public void removeLetter() {
    }


    private void getLetter() {}


    private void playLetter(String letter, int x, int y) {
       // BoardArray[x][y] = letter;     - NOT YET IMPLEMENTED
    }
    private void selectedLetter(String letter, int x, int y) {}
}
