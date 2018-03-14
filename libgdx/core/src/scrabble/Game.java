package scrabble;

import data.Move;
import data.Result;
import player.AIPlayer;
import player.HumanPlayer;
import player.Player;
import validation.NewValidator;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Game object contains information related to current game session. Created
 * whenever a new game is started.
 *
 * @author Tom Geraghty
 * @version 1.0
 */
public class Game {

    private static final LetterBag LETTER_BAG = new LetterBag();
    private static final int MAX_PLAYERS = 4;
    private static final ArrayBlockingQueue<Player> PLAYER_ORDER = new ArrayBlockingQueue<>(MAX_PLAYERS);
    private static final ArrayList<Player> PLAYER_LIST = new ArrayList<>();
    private static final ArrayList<Move> MOVE_LIST = new ArrayList<>();
    private static Move currentMove;
    private static Player currentPlayer;
    private static int numberOfPlayers;
    private static NewValidator validator = new NewValidator(Board.getInstance());


    /* PLAYER FUNCTIONS */
    /**
     * Add Players to the Game, using the Player name and Player type.
     *
     * @param playerName Player chosen name
     * @param playerType Player type (1 - Human, 2 - CPU)
     */
    public static void addPlayer(String playerName, int playerType) {
        if (PLAYER_LIST.size() < MAX_PLAYERS) {
            Player player;
            switch (playerType) {
                case 1:
                default:
                    player = new HumanPlayer(playerName);
                    PLAYER_LIST.add(player);
                    break;
                case 2:
                    player = new AIPlayer(playerName);
                    PLAYER_LIST.add(player);
                    break;
            }
            numberOfPlayers++;
            PLAYER_ORDER.add(player);
        }
    }
    /**
     * Removes Player from game.
     *
     * @param player Player to remove
     */
    public static void removePlayer(Player player) {
        PLAYER_LIST.remove(player);
        PLAYER_ORDER.remove(player);
    }
    /**
     * Gets the current Player (Player making move currently) in this game.
     * @return  player      Player making current Move.
     */
    public static Player getCurrentPlayer() {
        return currentPlayer;
    }
    /**
     * Returns all Players in Game.
     * @return  players     All current Players
     */
    public static ArrayList<Player> getPlayers() {
        return PLAYER_LIST;
    }
    /**
     * Returns the amount of Players in the Game.
     * @return  amount      Number of players in Game.
     */
    public static int getNumberOfPlayers() {
        return numberOfPlayers;
    }


    /* GAME FUNCTIONS */
    /**
     * Starts game. Adds Tiles to all Players hands. Calls startTurn
     */
    public static void start() {
        for (Player player : PLAYER_LIST) {
            player.addTiles();
        }
        startTurn();
    }
    /**
     * Gets next Player and sets it to the current Player. Creates a new Move.
     */
    public static void startTurn() {
        currentPlayer = PLAYER_ORDER.poll();
        PLAYER_ORDER.add(currentPlayer);

        currentMove = new Move(currentPlayer);
        MOVE_LIST.add(currentMove);
    }
    /**
     * Ends current turn, increments Player score by the score of the Move.
     */
    public static void endTurn() {
        Result lastResult = Board.getInstance().getLastResult();
        if (currentMove.getPlayedTiles().size() > 0 && lastResult.isCompleteWord()) {
        	if (validator.testConnectedWords(currentMove)) { //connected to other words {}
        		Board.getInstance().validatorReset();
                currentPlayer.setScore(currentPlayer.getScore() + currentMove.getMoveScore());
                currentMove = null;
                currentPlayer.addTiles();
        	}
        } else if(lastResult == null && currentMove.getPlayedTiles().size() == 0){
            JOptionPane.showMessageDialog(null, "Do you want to end?", "Error", JOptionPane.INFORMATION_MESSAGE);
            Board.getInstance().validatorReset();
            currentPlayer.setScore(currentPlayer.getScore() + currentMove.getMoveScore());
            currentMove = null;
            currentPlayer.addTiles();
        } else {
            JOptionPane.showMessageDialog(null, "This is not a valid word!", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    /**
     * Returns the in progress Move.
     * @return  move    In progress Move.
     */
    public static Move getCurrentMove() {
        return currentMove;
    }


    /* GAME PIECES */
    /**
     * Returns this Game's LetterBag.
     * @return  letterbag       This Game's LetterBag.
     */
    public static LetterBag getLetterBag() {
        return LETTER_BAG;
    }
    
    public static ArrayList<Move> getMoveList() {
    	return MOVE_LIST;
    }
    
    public static boolean isFirstTurn() {
    	return (MOVE_LIST.size() == 1);
    }
}
