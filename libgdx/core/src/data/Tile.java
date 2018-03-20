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
	private final int ID;
	private static int boo;
	public Tile(char letter, int score) {
		this.letter = letter;
		this.score = score;
		if (boo>0){
			boo+= 1;
		}
		else {
			boo = 1;
		}
		ID = boo;
		System.out.println(ID);
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
	public int getID(){
		return ID;
	}

	public char getChar() {
		return letter;
	}
	
	public int getScore() {
		return score;
	}

	
}
