package scrabble;

import data.BoardScorer;
import data.Result;
import validation.Validator;
/**
 * Main class that holds the board and organises the game
 * @author Ollie Nye
 * @verion 1.1
 */

public class Board {
	
	private static Board instance = null;
	
	private Validator validator = new Validator();
	
	public static Board getInstance() {
		if (instance == null) {
			instance = new Board();
		}
		return instance;
	}
	
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
		{3, 0, 0, 2, 0, 0, 0, 3, 0, 0, 0, 2, 0, 0, 3},
		{0, 2, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 2, 0},
		{0, 0, 2, 0, 0, 0, 2, 0, 2, 0, 0, 0, 2, 0, 0},
		{2, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 2},
		{0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0},
		{0, 3, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 3, 0},
		{0, 0, 2, 0, 0, 0, 2, 0, 2, 0, 0, 0, 2, 0, 0},
		{3, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 3},
		{0, 0, 2, 0, 0, 0, 2, 0, 2, 0, 0, 0, 2, 0, 0},
		{0, 3, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 3, 0},
		{0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0},
		{2, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 2},
		{0, 0, 2, 0, 0, 0, 2, 0, 2, 0, 0, 0, 2, 0, 0},
		{0, 2, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 2, 0},
		{3, 0, 0, 2, 0, 0, 0, 3, 0, 0, 0, 2, 0, 0, 3}
	};
	
	
	private BoardScorer[][] scoreBoard = new BoardScorer[15][15];
	private Tile[][] letters = new Tile[15][15];
	
	private Board() {
		
		//Initialise board scoring
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				boolean isLetter;
				boolean isWord;
				int score;
				switch (types[i][j]) {
				case 'l':
					scoreBoard[i][j] = new BoardScorer(true, scores[i][j]);
					break;
				case 'w':
					scoreBoard[i][j] = new BoardScorer(false, scores[i][j]);
					break;
				default:
					scoreBoard[i][j] = null;
					break;
				}
			}
		}
		
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				letters[i][j] = null;
			}
		}
	}
	
	public Tile getTile(int x, int y) {
		if (x >= 0 && x < 15 && y >= 0 && y < 15) {
			return letters[x][y];
		}
		return null;
	}
	
	public BoardScorer getScore(int x, int y) {
		if (x >= 0 && x < 15 && y >= 0 && y < 15) {
			return scoreBoard[x][y];
		}
		return null;
	}
	
	public Result place(Tile tile, int x, int y) {
		Result res = validator.validateMove(tile, x, y);
		if (res.isLegal()) {
			this.letters[x][y] = tile;
		}
		return res;
	}
	
	public void partialPlace(Tile tile) {
		
	}
	
	public void partialPlace(int x, int y) {
		
	}
	
	public static void main(String[] args) {
		Board brd = Board.getInstance();
		Result res = brd.place(LetterBag.getInstance().pick(), 5, 7);
		System.out.println(res.isLegal() + " - " + res.possibleWords());
		res = brd.place(LetterBag.getInstance().pick(), 5, 9);
		System.out.println(res.isLegal() + " - " + res.possibleWords());
		res = brd.place(LetterBag.getInstance().pick(), 5, 9);
		System.out.println(res.isLegal() + " - " + res.possibleWords());
	}
	
}
