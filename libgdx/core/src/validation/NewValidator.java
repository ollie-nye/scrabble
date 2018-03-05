package validation;

import data.Result;
import scrabble.Tile;
import scrabble.Board;

import java.util.ArrayList;

import data.Coordinate;
import data.Letter;

public class NewValidator {

	private enum Direction { HORIZONTAL, VERTICAL }

	private Direction direction;

	private int location = 0;

	private Dawg dictionary = new Dawg();

	private Board board = null;

	private int playedTiles = 0;

	private Coordinate firstMove = null;

	private String firstMoveContent = "";
	
	private WordOperations ops;



	private String[] turn = new String[15];

	public NewValidator(Board board) {
		this.board = board;
		ops = new WordOperations(this.board);
		playedTiles = 0;
	}

	public Result validateMove(Letter letter) {
		boolean allowedMove = true;
		boolean emptySpace = (this.board.getTile(letter.getLocation()) == null);
		int possibleWords = 0;
		

		if (emptySpace) {
			
			this.board.testPlace(letter); //Happens every turn
			if (playedTiles == 0) {
				firstMove = letter.getLocation();
				firstMoveContent = letter.getLetter().getContent();
			} else if (playedTiles == 1) { // Second move
				// Check that at least one of the coordinates is common between this and the last move, therefore determining direction
				if (letter.getLocation().getX() == firstMove.getX()) { // X is common, therefore vertical word direction
					possibleWords = testMove(letter);
					if (possibleWords > 0) {
						this.direction = Direction.VERTICAL;
					} else {
						allowedMove = false;
					}
				} else if (letter.getLocation().getY() == firstMove.getY()) { // Y is common, therefore horizontal word direction
					possibleWords = testMove(letter);
					if (possibleWords > 0) {
						this.direction = Direction.HORIZONTAL;
					} else {
						allowedMove = false;
					}
				} else {
					allowedMove = false;
				}
			} else { // Any other move
				if (this.direction == Direction.VERTICAL) {
					if (letter.getLocation().getX() == firstMove.getX()) {
						possibleWords = testMove(letter);
					} else {
						allowedMove = false;
					}
				} else if (this.direction == Direction.HORIZONTAL) {
					if (letter.getLocation().getY() == firstMove.getY()) {
						possibleWords = testMove(letter);
					} else {
						allowedMove = false;
					}
				} else {
					allowedMove = false;
				}
			}
		} else {
			allowedMove = false;
		}

		if (allowedMove) {
			playedTiles++;
			possibleWords = 0;
		} else {
			this.board.removeTile(letter.getLocation());
		}
		return new Result(allowedMove, possibleWords, false);
	}

	private int testMove(Letter letter) {
		int possibleWords = 0;
		ArrayList<ArrayList<Letter>> turnProgress = ops.identifyWords(letter);
		ArrayList<String> words = new ArrayList<>();
		
		for (ArrayList<Letter> word : turnProgress) {
			String wrd = "";
			for (Letter ltr : word) {
				wrd += ltr.getLetter().getContent();
			}
			words.add(wrd);
		}
		for (String word : words) {
			possibleWords += this.dictionary.getPossibleWords(word);
		}
		return possibleWords;
	}
}
