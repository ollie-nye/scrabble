package data;

import java.io.Serializable;

/**
 * Communication object between classes carrying tile data
 * @author Ollie
 * @version 1
 */

public class Tile implements Serializable {
	private char letter;
	private int score;
	
	public Tile(char letter, int score) {
		this.letter = letter;
		this.score = score;
	}
	
	public String getContent() {
		return "" + letter;
	}

	public void setLetter(char letter) {
	    this.letter = letter;
    }

	public void setScore(int score) {
	    this.score = score;
    }

	public char getChar() {
		return letter;
	}
	
	public int getScore() {
		return score;
	}

	@Override
	public String toString() {
		return "" + letter;
	}
}
