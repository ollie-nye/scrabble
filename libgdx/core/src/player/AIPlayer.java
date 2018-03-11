package player;

/**
 * Contains methods specific to AIPlayer player type. Extends 'Player ' abstract class.
 * @author Thomas Geraghty
 * @version 1.0
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
}
