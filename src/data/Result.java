package data;

/**
 * Return object for MoveValidation
 * @author Ollie Nye
 * @version 1.0
 */

public class Result {
	private boolean legalMove;
	private int possibleWords;
	
	public Result(boolean legalMove, int possibleWords) {
		this.legalMove = legalMove;
		this.possibleWords = possibleWords;
	}
	
	public boolean isLegal() {
		return this.legalMove;
	}
	
	public int possibleWords() {
		return this.possibleWords;
	}
}
