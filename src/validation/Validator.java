package validation;

import java.util.HashMap;
import data.Coordinate;
import data.Result;
import scrabble.Board;
import scrabble.Tile;

/**
 * Validator for each move made by a player, AI and human alike
 * @author Ollie Nye
 * @version 1.1
 */

public class Validator {
	//initial tile
	//find direction
	//test all tiles for 'possible' move & complete word
	//return a MoveResult object to caller
	
	private String currentPlay = "               "; //15 characters, maximum word length
	private HashMap<Tile, Coordinate> playedTiles = new HashMap<>();
	
	public Validator() {}
	
	public Result validateMove(Tile tile, int x, int y) {
		boolean spaceEmpty;
		Tile prospectiveTile = Board.getInstance().getTile(x, y);
		spaceEmpty = (prospectiveTile == null) ? true : false;
		
		if (spaceEmpty) {
			//possibly allowed move
			if (playedTiles.size() == 0) { //first move of the turn
				playedTiles.put(tile, new Coordinate(x, y));
			}
			
			//needs method to determine where in the word a letter is placed. First letter, assume first of the word. Have to move the word along if another letter is placed 'before' the last played letter in the word.
		}
		
		
		
		
		
		
		return new Result(false, false);
	}
}
