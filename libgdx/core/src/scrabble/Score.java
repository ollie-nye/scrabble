package scrabble;

import java.util.ArrayList;

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
        letterSets.add(0, "*");
        letterSets.add(1, "aeioulnrst");
        letterSets.add(2, "dg");
        letterSets.add(3, "bcmp");
        letterSets.add(4, "fhvwy");
        letterSets.add(5, "k");
        letterSets.add(6, "jx");
        letterSets.add(7, "qz");
    }

    /**
     * Takes a letter and returns its score, if letter not found
     * in collection, return 0.
     * @param letter
     * @return
     */
    private int getScoreOfLetter(String letter) {
        for (String characterSet : letterSets) {
            if (characterSet.contains(letter)) {
                return letterSets.indexOf(characterSet);
            }
        }
        return 0;
    }


    /**
     * Takes a letter and position, and returns its
     * board value (i.e with double letter score).
     * @param x
     * @param y
     * @param letter
     * @return
     */
    public int calculateScore(int x, int y, String letter) {
        if(types[x][y] == 'l') {
            return getScoreOfLetter(letter) * scores[x][y];
        } else {
            return getScoreOfLetter(letter);
        }
    }
}
