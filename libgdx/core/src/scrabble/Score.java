package scrabble;

import data.Coordinate;
import data.Tile;

/**
 * @author Tom Geraghty
 * @version 1.1
 * */
public class Score {

    /**
     * Types of multiplier tiles on the board
     */
    private char[][] types = new char[][]{
            {'w','n','n','l','n','n','n','w','n','n','n','l','n','n','w'},
            {'n','w','n','n','n','l','n','n','n','l','n','n','n','w','n'},
            {'n','n','w','n','n','n','l','n','l','n','n','n','w','n','n'},
            {'l','n','n','w','n','n','n','l','n','n','n','w','n','n','l'},
            {'n','n','n','n','w','n','n','n','n','n','w','n','n','n','n'},
            {'n','l','n','n','n','l','n','n','n','l','n','n','n','l','n'},
            {'n','n','l','n','n','n','l','n','l','n','n','n','l','n','n'},
            {'w','n','n','l','n','n','n','w','n','n','n','l','n','n','w'},
            {'n','n','l','n','n','n','l','n','l','n','n','n','l','n','n'},
            {'n','l','n','n','n','l','n','n','n','l','n','n','n','l','n'},
            {'n','n','n','n','w','n','n','n','n','n','w','n','n','n','n'},
            {'l','n','n','w','n','n','n','l','n','n','n','w','n','n','l'},
            {'n','n','w','n','n','n','l','n','l','n','n','n','w','n','n'},
            {'n','w','n','n','n','l','n','n','n','l','n','n','n','w','n'},
            {'w','n','n','l','n','n','n','w','n','n','n','l','n','n','w'}
    };

    /**
     * Scores of multiplier tiles on the board
     */
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

    /**
     * Takes a letter and position, and returns its
     * board value (i.e with double letter score).
     * @param tile
     * @param coordinate
     * @return
     */
    public int calculateScore(Tile tile, Coordinate coordinate) {
    	switch (types[coordinate.getX()][coordinate.getY()]) {
    	case 'l': // letter multiplier
    		return tile.getScore() * scores[coordinate.getX()][coordinate.getY()];
    	case 'n': default:
    		return tile.getScore();
    	//TODO word multiplier - needs to be handled
    	case 'w':
			return tile.getScore();
    	}
    }
}
