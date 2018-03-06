package player;

/**
 * Contains methods specific to HumanPlayer player type. Extends 'Player ' abstract class.
 * @author Thomas Geraghty
 * @version 1.0
 */
public class HumanPlayer extends Player {

    /**
     * Constructor for HumanPlayer, sets player username based on passed parameter 'name'.
     * Adds 7 letters from LetterBag to player's collection of letters.
     *
     * @param   name    Player username string
     * @see         player.Player
     */
	public HumanPlayer(String name) {
        super();
		setPlayerName(name);

		for(int i = 0;  i <7; i++) {
		    addLetters();
        }
    }
}
