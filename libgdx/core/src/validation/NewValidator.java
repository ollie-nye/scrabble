package validation;

import data.Coordinate;
import data.Letter;
import data.move.Move;
import data.Result;
import data.Tile;
import scrabble.Board;
import scrabble.Game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class NewValidator implements Serializable {

	private enum Direction {
		HORIZONTAL, VERTICAL
	}

	private Direction direction;
	private int location = 0;
	private Dawg dictionary = new Dawg();
	private Board board = null;
	private int playedTiles = 0;
	private Coordinate firstMove = null;
	private String firstMoveContent = "";
	private WordOperations ops;
	private ArrayList<String> words = null;
	private String[] turn = new String[15];
	private Result result = null;

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

			this.board.testPlace(letter); // Happens every turn
			if (playedTiles == 0) {
				// test row/column if not first move
				if (Game.getMoveList().size() > 1) { // not first move of the
														// game
					int x = letter.getLocation().getX();
					int y = letter.getLocation().getY();
					boolean commonDirection = false;
					for (int i = 0; i < 15; i++) {
						if (i == letter.getLocation().getX()) {
							continue;
						}
						if (Board.getInstance().getTile(new Coordinate(i, y)) != null) {
							commonDirection = true;
							break;
						}
					}
					if (!commonDirection) {
						for (int i = 0; i < 15; i++) {
							if (i == letter.getLocation().getY()) {
								continue;
							}
							if (Board.getInstance().getTile(new Coordinate(x, i)) != null) {
								commonDirection = true;
								break;
							}
						}
					}
					if (commonDirection) {
						firstMove = letter.getLocation();
						firstMoveContent = letter.getTile().getContent();
					} else {
						allowedMove = false;
					}
				} else {
					System.out.println("tell my nigga fuck feds");
					if(!(letter.getLocation().getX() == 7 && letter.getLocation().getY() == 7)) {
						allowedMove = false;
					}
					firstMove = letter.getLocation();
					firstMoveContent = letter.getTile().getContent();
				}
			} else if (playedTiles == 1) { // Second move
				// Check that at least one of the coordinates is common between
				// this and the last move, therefore determining direction
				if (letter.getLocation().getX() == firstMove.getX()) { // X is
																		// common,
																		// therefore
																		// vertical
																		// word
																		// direction
					possibleWords = testMove(letter);
					if (possibleWords > 0) {
						this.direction = Direction.VERTICAL;
					} else {
						allowedMove = false;
					}
				} else if (letter.getLocation().getY() == firstMove.getY()) { // Y
																				// is
																				// common,
																				// therefore
																				// horizontal
																				// word
																				// direction
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
			// this.board.removeTile(letter.getLocation());
			Game.getCurrentMove().removeTile(letter.getTile());
		}
		this.result = new Result(allowedMove, possibleWords, testEndTurnMove(letter));
		this.words = null;
		return this.result;
	}

	public boolean testConnectedWords(Move currentMove) {
		HashMap<Tile, Coordinate> word = currentMove.getPlayedTiles();
		boolean connected = false;
		ArrayList<Coordinate> coordinates = new ArrayList<>();

		if (Game.getMoveList().size() == 1) {
			return true;
		} else {
			boolean allowed = false;
			for (Entry<Tile, Coordinate> letter : word.entrySet()) {
				Tile nextTile = board.getTile(letter.getValue().getNear('U'));
				if (!Game.getCurrentMove().getPlayedTiles().containsKey(nextTile)) {
					if (nextTile != null) {
						allowed = true;
						break;
					}
				}
				if (letter.getValue().getY() < 14) {
					nextTile = board.getTile(letter.getValue().getNear('D'));
					if (!Game.getCurrentMove().getPlayedTiles().containsKey(nextTile)) {
						if (nextTile != null) {
							allowed = true;
							break;
						}
					}
				}
				nextTile = board.getTile(letter.getValue().getNear('L'));
				System.out.println("Tile left is " + (nextTile == null ? "empty" : nextTile.toString()));
				if (!Game.getCurrentMove().getPlayedTiles().containsKey(nextTile)) {
					if (nextTile != null) {
						allowed = true;
						break;
					}
				}

				if (letter.getValue().getX() < 14) {
					nextTile = board.getTile(letter.getValue().getNear('R'));
					if (!Game.getCurrentMove().getPlayedTiles().containsKey(nextTile)) {
						if (nextTile != null) {
							allowed = true;
							break;
						}
					}
				}

				continue;
			}
			if (allowed) {
				return true;
			}
		}
		return false;

		/*
		 * if(Game.getMoveList().size() == 1) { return true; } else { for
		 * (Entry<Tile, Coordinate> entry : word.entrySet()) { Coordinate
		 * coordinate = entry.getValue(); coordinates.add(coordinate); } for
		 * (Coordinate coordinate : coordinates) { for (Coordinate neighbour :
		 * getSurroundingCoordinates(coordinate)) { if
		 * (!coordinates.contains(neighbour)) { connected = true; break; } } if
		 * (connected) {break;} } return connected;
		 * 
		 * }
		 */
	}

	private ArrayList<Coordinate> getSurroundingCoordinates(Coordinate coordinate) {
		ArrayList<Coordinate> surroundings = new ArrayList<>();
		if (coordinate.getX() == 0) {
			if (getLetter(cRight(coordinate)).getTile() != null) {
				surroundings.add(cRight(coordinate));
			}
			if (getLetter(cAbove(coordinate)).getTile() != null) {
				surroundings.add(cAbove(coordinate));
			}
			if (getLetter(cBelow(coordinate)).getTile() != null) {
				surroundings.add(cBelow(coordinate));
			}
		} else if (coordinate.getX() == 15) {
			if (getLetter(cLeft(coordinate)).getTile() != null) {
				surroundings.add(cLeft(coordinate));
			}
			if (getLetter(cAbove(coordinate)).getTile() != null) {
				surroundings.add(cAbove(coordinate));
			}
			if (getLetter(cBelow(coordinate)).getTile() != null) {
				surroundings.add(cBelow(coordinate));
			}
		} else if (coordinate.getY() == 0) {
			if (getLetter(cLeft(coordinate)).getTile() != null) {
				surroundings.add(cLeft(coordinate));
			}
			if (getLetter(cRight(coordinate)).getTile() != null) {
				surroundings.add(cRight(coordinate));
			}
			if (getLetter(cBelow(coordinate)).getTile() != null) {
				surroundings.add(cBelow(coordinate));
			}
		} else if (coordinate.getY() == 15) {
			if (getLetter(cLeft(coordinate)).getTile() != null) {
				surroundings.add(cLeft(coordinate));
			}
			if (getLetter(cRight(coordinate)).getTile() != null) {
				surroundings.add(cRight(coordinate));
			}
			if (getLetter(cAbove(coordinate)).getTile() != null) {
				surroundings.add(cAbove(coordinate));
			}
		} else {
			if (getLetter(cLeft(coordinate)).getTile() != null) {
				surroundings.add(cLeft(coordinate));
			}
			if (getLetter(cRight(coordinate)).getTile() != null) {
				surroundings.add(cRight(coordinate));
			}
			if (getLetter(cAbove(coordinate)).getTile() != null) {
				surroundings.add(cAbove(coordinate));
			}
			if (getLetter(cBelow(coordinate)).getTile() != null) {
				surroundings.add(cBelow(coordinate));
			}
		}
		return surroundings;

	}

	private Letter getLetter(Coordinate coordinate) {
		return new Letter(Board.getInstance().getTile(coordinate), coordinate);
	}

	private Coordinate cLeft(Coordinate coordinate) {
		return new Coordinate(coordinate.getY() - 1, coordinate.getX());
	}

	private Coordinate cRight(Coordinate coordinate) {
		return new Coordinate(coordinate.getY() + 1, coordinate.getX());
	}

	private Coordinate cAbove(Coordinate coordinate) {
		return new Coordinate(coordinate.getY(), coordinate.getX() - 1);
	}

	private Coordinate cBelow(Coordinate coordinate) {
		return new Coordinate(coordinate.getY(), coordinate.getX() + 1);
	}

	public void resetDirection() {
		this.direction = null;
		this.playedTiles = 1;
	}

	private void getWords(Letter letter) {
		this.words = ops.identifyWords(letter);
	}

	public Result getLastResult() {
		return this.result;
	}

	private int testMove(Letter letter) {
		if (words == null) {
			getWords(letter);
		}
		int possibleWords = 0;
		for (String word : words) {
			possibleWords += 1;// this.dictionary.getPossibleWords(word);
		}
		return possibleWords;
	}

	private boolean testEndTurnMove(Letter letter) {
		if (words == null) {
			getWords(letter);
		}
		int completeWords = 0;
		for (String word : words) {
			completeWords += dictionary.getCompleteWords(word);
		}
		return (completeWords > 0);
	}
}
