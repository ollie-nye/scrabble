package scrabble;

import data.Coordinate;
import data.Tile;

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

    /**
     * Scores of multiplier tiles on the board
     *
     * 0 - no multiplication
     * 2 - 2x multiplication
     * 3 - 3x multiplication
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
     * Takes a Tile and its Coordinate, and returns its
     * board value (i.e with double letter score).
     *
     * @param tile          Tile to score
     * @param coordinate    Position of tile
     * @return score        Board score of tile
     */
    public int calculateScore(Tile tile, Coordinate coordinate) {
        switch (types[coordinate.getX()][coordinate.getY()]) {
            case 'l':
                return tile.getScore() * scores[coordinate.getX()][coordinate.getY()];
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
        switch (types[coordinate.getX()][coordinate.getY()]) {
            case 'w':
                return scores[coordinate.getX()][coordinate.getY()];
            default:
                return 1;
        }
    }
}
