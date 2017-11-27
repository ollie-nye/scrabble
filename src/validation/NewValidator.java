package validation;

import data.Result;
import scrabble.Tile;
import scrabble.Board;
import data.Coordinate;

public class NewValidator {

	private enum Direction { HORIZONTAL, VERTICAL }

	private Direction direction;

	private int location = 0;

	private Dictionary dictionary = new Dictionary();

	private Board board = null;

	private int playedTiles = 0;

	private Coordinate firstMove = null;

	private String firstMoveContent = "";



	private String[] turn = new String[15];

	public NewValidator(Board board) {
		this.board = board;
		playedTiles = 0;
	}

	public Result validateWord(Tile tile, int x, int y) {
		boolean allowedMove = true;
		boolean emptySpace = (this.board.getTile(x, y) == null);
		int possibleWords = 0;

		if (emptySpace) {
			if (playedTiles == 0) { // First move
				firstMove = new Coordinate(x, y);
				// No other tests for the first move of the turn
			} else if (playedTiles == 1) { // Second move
				// Check that at least one of the coordinates is common between this and the last move, therefore determining direction
				if (firstMove.getX() == x) { // X is common, therefore vertical word direction
					possibleWords = testMove(tile, y, firstMove.getY(), x);
					if (possibleWords > 0) {
						this.direction = Direction.VERTICAL;
					}
				} else if (firstMove.getY() == y) { // Y is common, therefore horizontal word direction
					possibleWords = testMove(tile, x, firstMove.getX(), y);
					if (possibleWords > 0) {
						this.direction = Direction.HORIZONTAL;
					}
				} else {
					allowedMove = false;
				}
			} else { // Any other move
				if (this.direction == Direction.VERTICAL) {
					if (x == location) {
						possibleWords = testMove(tile, y);
					} else {
						allowedMove = false;
					}
				} else if (this.direction == Direction.HORIZONTAL) {
					if (y == location) {
						possibleWords = testMove(tile, x);
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
		}
		return new Result(allowedMove, possibleWords, false);
	}

	private int testMove(Tile tile, int move) {
		return testMove(tile, move, -1, -1);
	}

	private int testMove(Tile tile, int fMove, int sMove, int location) {
		int possibleWords = 0;
		if (firstMove != null) { // 2nd move only, reset to null at the end of this method.
			String[] possibleTurn = new String[15]; // This turn is not sure to be legal, so cannot add both moves to turn array yet
			possibleTurn[fMove] = firstMoveContent;
			possibleTurn[sMove] = tile.getContent();
			possibleWords = testRegex(generateMoveRegex(possibleTurn)); // Test two given letters against the dictionary
			if (possibleWords > 0) {
				this.location = location;
				this.turn[sMove] = tile.getContent();
				this.turn[fMove] = firstMoveContent;
			}
		} else if (sMove == -1) { // Passed into this function from overflow method, guarantees this block to be called
			String[] possibleTurn = turn;
			possibleTurn[fMove] = tile.getContent();
			possibleWords = testRegex(generateMoveRegex(possibleTurn));
			if (possibleWords > 0) {
				this.turn[fMove] = possibleTurn[fMove];
			}
		}
		return possibleWords;
	}

	private int testRegex(String regex) {
		int possibleWords = this.dictionary.searchList(regex);
		return possibleWords;
	}

	private String generateMoveRegex(String[] possibleTurn) {
		String regex = "";
		return regex;
	}

	private String generateEndTurnRegex(String[] possibleTurn) {
		String regex = "";
		return regex;
	}

	private String getSpaceContent(int x, int y) {
		String result = null;
		return result;
	}
}
