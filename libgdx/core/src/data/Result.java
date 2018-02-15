package data;

/**
 * Return object for MoveValidation
 * @author Ollie Nye
 * @version 1.0
 */

public class Result {
	private boolean legalMove;
	private int possibleWords;
	private boolean isCompleteWord;
	
	public Result(boolean legalMove, int possibleWords, boolean isCompleteWord) {
		this.legalMove = legalMove;
		this.possibleWords = possibleWords;
		this.isCompleteWord = isCompleteWord;
	}
	
	public boolean isLegal() {
		return this.legalMove;
	}
	
	public int possibleWords() {
		return this.possibleWords;
	}

	public boolean isCompleteWord() {
		return this.isCompleteWord;
	}
}
