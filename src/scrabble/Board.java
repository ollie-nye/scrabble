package scrabble;

import player.Player;

/**
 * Main class that holds the board and organises the game
 * @author Ollie Nye
 * @verion 1.0
 */

public class Board {
	private Player player;
	//private LetterScore letterScore; //TODO implement LetterScore
	
	// new BoardScorer(false, 2)
	// new BoardScorer(False, 3)
	// new BoardScorer(true, 2)
	// new BoardScorer(true, 3)
	
	private char[][] types = new char[][]{
		{'w', 'n', 'n', 'l', 'n', 'n', 'n', 'w', 'n', 'n', 'n', 'l', 'n', 'n', 'w'},
		{'n', 'w', 'n', 'n', 'n', 'l', 'n', 'n', 'n', 'l', 'n', 'n', 'n', 'w', 'n'},
		{'n', 'n', 'w', 'n', 'n', 'n', 'l', 'n', 'l', 'n', 'n', 'n', 'w', 'n', 'n'},
		{'l', 'n', 'n', 'w', 'n', 'n', 'n', 'l', 'n', 'n', 'n', 'w', 'n', 'n', 'l'},
		{'n', 'n', 'n', 'n', 'w', 'n', 'n', 'n', 'n', 'n', 'w', 'n', 'n', 'n', 'n'},
		{'n', 'l', 'n', 'n', 'n', 'l', 'n', 'n', 'n', 'l', 'n', 'n', 'n', 'l', 'n'},
		{'n', 'n', 'l', 'n', 'n', 'n', 'l', 'n', 'l', 'n', 'n', 'n', 'l', 'n', 'n'},
		{'w', 'n', 'n', 'l', 'n', 'n', 'n', 'w', 'n', 'n', 'n', 'l', 'n', 'n', 'w'},
		{'n', 'n', 'l', 'n', 'n', 'n', 'l', 'n', 'l', 'n', 'n', 'n', 'l', 'n', 'n'},
		{'n', 'l', 'n', 'n', 'n', 'l', 'n', 'n', 'n', 'l', 'n', 'n', 'n', 'l', 'n'},
		{'n', 'n', 'n', 'n', 'w', 'n', 'n', 'n', 'n', 'n', 'w', 'n', 'n', 'n', 'n'},
		{'l', 'n', 'n', 'w', 'n', 'n', 'n', 'l', 'n', 'n', 'n', 'w', 'n', 'n', 'l'},
		{'n', 'n', 'w', 'n', 'n', 'n', 'l', 'n', 'l', 'n', 'n', 'n', 'w', 'n', 'n'},
		{'n', 'w', 'n', 'n', 'n', 'l', 'n', 'n', 'n', 'l', 'n', 'n', 'n', 'w', 'n'},
		{'w', 'n', 'n', 'l', 'n', 'n', 'n', 'w', 'n', 'n', 'n', 'l', 'n', 'n', 'w'}
	};
	
	private int[][] scores = new int[][]{
		{'3', 'n', 'n', '2', 'n', 'n', 'n', '3', 'n', 'n', 'n', '2', 'n', 'n', '3'},
		{'n', '2', 'n', 'n', 'n', '3', 'n', 'n', 'n', '3', 'n', 'n', 'n', '2', 'n'},
		{'n', 'n', '2', 'n', 'n', 'n', '2', 'n', '2', 'n', 'n', 'n', '2', 'n', 'n'},
		{'2', 'n', 'n', '2', 'n', 'n', 'n', '2', 'n', 'n', 'n', '2', 'n', 'n', '2'},
		{'n', 'n', 'n', 'n', '2', 'n', 'n', 'n', 'n', 'n', '2', 'n', 'n', 'n', 'n'},
		{'n', '3', 'n', 'n', 'n', '3', 'n', 'n', 'n', '3', 'n', 'n', 'n', '3', 'n'},
		{'n', 'n', '2', 'n', 'n', 'n', '2', 'n', '2', 'n', 'n', 'n', '2', 'n', 'n'},
		{'3', 'n', 'n', '2', 'n', 'n', 'n', '2', 'n', 'n', 'n', '2', 'n', 'n', '3'},
		{'n', 'n', '2', 'n', 'n', 'n', '2', 'n', '2', 'n', 'n', 'n', '2', 'n', 'n'},
		{'n', '3', 'n', 'n', 'n', '3', 'n', 'n', 'n', '3', 'n', 'n', 'n', '3', 'n'},
		{'n', 'n', 'n', 'n', '2', 'n', 'n', 'n', 'n', 'n', '2', 'n', 'n', 'n', 'n'},
		{'2', 'n', 'n', '2', 'n', 'n', 'n', '2', 'n', 'n', 'n', '2', 'n', 'n', '2'},
		{'n', 'n', '2', 'n', 'n', 'n', '2', 'n', '2', 'n', 'n', 'n', '2', 'n', 'n'},
		{'n', '2', 'n', 'n', 'n', '3', 'n', 'n', 'n', '3', 'n', 'n', 'n', '2', 'n'},
		{'3', 'n', 'n', '2', 'n', 'n', 'n', '3', 'n', 'n', 'n', '2', 'n', 'n', '3'}
	};
	
	
	private BoardScorer[][] scoreBoard = new BoardScorer[][]{};
	
	
	public Board() {
		
		//Initialise board scoring
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				boolean isLetter;
				boolean isWord;
				int score;
				switch (types[i][j]) {
				case 'l':
					scoreBoard[i][j] = new BoardScorer(true, scores[i][j]);
				case 'w':
					scoreBoard[i][j] = new BoardScorer(false, scores[i][j]);
				default:
					scoreBoard[i][j] = null;
				}
			}
		}
	}
	
	
}
