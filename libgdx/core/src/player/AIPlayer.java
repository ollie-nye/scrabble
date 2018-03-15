package player;

import data.Coordinate;
import data.Tile;
import scrabble.Board;

/**
 * Contains methods specific to AIPlayer player type. Extends 'Player ' abstract class.
 * @author Thomas Geraghty
 * @version 1.1
 */
public class AIPlayer extends Player {

    /**
     * Constructor for AIPlayer, sets player username based on passed parameter 'name'.
     * Adds 7 letters from LetterBag to player's collection of letters.
     *
     * @param   name    Player username string
     * @see         player.Player
     */
	public AIPlayer(String name) {
		super();
		setPlayerName(name);
	}

	public void play() {
        Board.getInstance().place(new Tile("t", 7), new Coordinate(3,3));
        Board.getInstance().place(new Tile("o", 7), new Coordinate(4,3));
        Board.getInstance().place(new Tile("m", 7), new Coordinate(5,3));
    }
}
