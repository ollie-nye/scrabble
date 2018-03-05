package scrabble;

import java.util.ArrayList;

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
     * Collection of letters with score
     */
    private ArrayList<String> letterSets  = new ArrayList<>();

    public Score() {
    	/*
        letterSets.add(0, "*");
        letterSets.add(1, "aeioulnrst");
        letterSets.add(2, "dg");
        letterSets.add(3, "bcmp");
        letterSets.add(4, "fhvwy");
        letterSets.add(5, "k");
        letterSets.add(8, "jx");
        letterSets.add(10, "qz");
		*/
    }

    /**
     * Takes a letter and position, and returns its
     * board value (i.e with double letter score).
     * @param x
     * @param y
     * @param letter
     * @return
     */
    public int calculateScore(Letter letter) {
    	switch (types[letter.getLocation().getX()][letter.getLocation().getY()]) {
    	case 'l': // letter multiplier
    		return letter.getLetter().getScore() * scores[letter.getLocation().getX()][letter.getLocation().getY()];
    	case 'n':
    		return letter.getLetter().getScore();
    	//TODO
    	case 'w': // word multipler - needs to be handled
    		return -1;
    	default:
    		return -1;
    	}
    }
}
