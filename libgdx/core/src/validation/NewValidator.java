package validation;

import data.Result;
import scrabble.Tile;
import scrabble.Board;

import java.util.ArrayList;

import data.Coordinate;

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

	public Result validateMove(Tile tile, int x, int y) {
		boolean allowedMove = true;
		boolean emptySpace = (this.board.getTile(x, y) == null);
		int possibleWords = 0;
		Letter letter = new Letter(tile, new Coordinate(x, y));
		

		if (emptySpace) {
			
			this.board.testPlace(tile, x, y); //Happens every turn
			if (playedTiles == 0) {
				firstMove = new Coordinate(x, y);
				firstMoveContent = tile.getContent();
			} else if (playedTiles == 1) { // Second move
				// Check that at least one of the coordinates is common between this and the last move, therefore determining direction
				if (x == firstMove.getX()) { // X is common, therefore vertical word direction
					possibleWords = testMove(letter);
					if (possibleWords > 0) {
						this.direction = Direction.VERTICAL;
					} else {
						allowedMove = false;
					}
				} else if (y == firstMove.getY()) { // Y is common, therefore horizontal word direction
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
					if (x == firstMove.getX()) {
						possibleWords = testMove(letter);
					} else {
						allowedMove = false;
					}
				} else if (this.direction == Direction.HORIZONTAL) {
					if (y == firstMove.getY()) {
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
			this.board.removeTile(x, y);
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

	private String getSpaceContent(int x, int y) {
		String result = null;
		Tile tile = this.board.getTile(x, y);
		if (tile != null) {
			result = tile.getContent();
		}
		return result;
	}
}
