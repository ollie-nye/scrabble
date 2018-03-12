package scrabble;

import data.Letter;

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
     * @param letter
     * @return
     */
    public int calculateScore(Letter letter) {
    	switch (types[letter.getLocation().getX()][letter.getLocation().getY()]) {
    	case 'l': // letter multiplier
    		return letter.getTile().getScore() * scores[letter.getLocation().getX()][letter.getLocation().getY()];
    	case 'n': default:
    		return letter.getTile().getScore();
    	//TODO word multiplier - needs to be handled
    	case 'w':
			return letter.getTile().getScore();
    	}
    }
}
