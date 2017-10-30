package validation;

/**
 * Return object for MoveValidation
 * @author Ollie Nye
 * @version 1.0
 */

public class Result {
	private boolean legalMove;
	private boolean completeWord;
	
	public Result(boolean legalMove, boolean completeWord) {
		this.legalMove = legalMove;
		this.completeWord = completeWord;
	}
	
	public boolean isLegal() {
		return this.legalMove;
	}
	
	public boolean isWord() {
		return this.completeWord;
	}
}
