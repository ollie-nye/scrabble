package scrabble;

import data.Coordinate;
import data.Tile;
import javafx.util.Pair;

import java.util.HashMap;

/**
 * @author Tom Geraghty
 * @version 1.2
 */
public class Score {

    /**
     * Types of multiplier tiles on the board
     *
     * n - standard place
     * l - letter multiplier
     * w - word multiplier
     */
    private char[][] multiplierType = new char[][]{
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

    /**
     * Scores of multiplier tiles on the board
     *
     * 0 - no multiplication
     * 2 - 2x multiplication
     * 3 - 3x multiplication
     */
    private int[][] multiplierScore = new int[][]{
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

    private final static HashMap<Character, Integer> letterScores = new HashMap<>();

    public Score() {
        letterScores.put('a', 1);
        letterScores.put('b', 3);
        letterScores.put('c', 3);
        letterScores.put('d', 2);
        letterScores.put('e', 1);
        letterScores.put('f', 4);
        letterScores.put('g', 2);
        letterScores.put('h', 4);
        letterScores.put('i', 1);
        letterScores.put('j', 8);
        letterScores.put('k', 5);
        letterScores.put('l', 1);
        letterScores.put('m', 3);
        letterScores.put('n', 1);
        letterScores.put('o', 1);
        letterScores.put('p', 3);
        letterScores.put('q', 10);
        letterScores.put('r', 1);
        letterScores.put('s', 1);
        letterScores.put('t', 1);
        letterScores.put('u', 1);
        letterScores.put('v', 4);
        letterScores.put('w', 4);
        letterScores.put('x', 8);
        letterScores.put('y', 4);
        letterScores.put('z', 10);
    }

    /**
     * Takes a Tile and its Coordinate, and returns its
     * board value (i.e with double letter score).
     *
     * @param tile          Tile to score
     * @param coordinate    Position of tile
     * @return score        Board score of tile
     */
    public int calculateScore(Tile tile, Coordinate coordinate) {
        switch (multiplierType[coordinate.getX()][coordinate.getY()]) {
            case 'l':
                return tile.getScore() * multiplierScore[coordinate.getX()][coordinate.getY()];
            case 'n': default:
                return tile.getScore();
        }
    }

    /**
     * Returns the multiplier (if any) at the Coordinate.
     * Returns 1 if no multiplier, returns up 2/3 if multiplier.
     *
     * @param coordinate       Coordinate to check for multiplier
     * @return multiplier      Multiplier value
     */
    public int getWordMultiplier(Coordinate coordinate) {
        switch (multiplierType[coordinate.getX()][coordinate.getY()]) {
            case 'w':
                return multiplierScore[coordinate.getX()][coordinate.getY()];
            default:
                return 1;
        }
    }

    public char[][] getMultiplierType() {
        return multiplierType;
    }

    public int[][] getMultiplierScore() {
        return multiplierScore;
    }

    public static HashMap<Character, Integer> getLetterScores() {
        return letterScores;
    }

    public static int getLetterScore(char letter) {
        return letterScores.get(letter);
    }
}
