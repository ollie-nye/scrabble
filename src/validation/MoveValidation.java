package validation;

/**
 * Validator for each move made by a player, AI and human alike
 * @author Ollie Nye
 * @version 1.0
 */

public class MoveValidation {
	//initial tile
	//find direction
	//test all tiles for 'possible' move & complete word
	//return a MoveResult object to caller
	
	public MoveValidation() {}
	
	public Result validateMove(int x, int y) {
		return new Result(false, false);
	}
}
