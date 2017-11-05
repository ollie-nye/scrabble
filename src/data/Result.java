package data;

/**
 * Return object for MoveValidation
 * @author Ollie Nye
 * @version 1.0
 */

public class Result {
	private boolean legalMove;
	private int completeWords;
	
	public Result(boolean legalMove, int completeWords) {
		this.legalMove = legalMove;
		this.completeWords = completeWords;
	}
	
	public boolean isLegal() {
		return this.legalMove;
	}
	
	public int words() {
		return this.completeWords;
	}
}
