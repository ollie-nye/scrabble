package scrabble;

import data.*;
import data.Move.AIMove;
import data.Move.HumanMove;
import data.Move.Move;
import player.AIPlayer;
import player.HumanPlayer;
import player.Player;
import validation.NewValidator;
import javax.swing.JOptionPane;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Game object contains information related to current game session. Created
 * whenever a new game is started.
 *
 * @author Tom Geraghty
 * @version 1.0
 */
public class Game implements Serializable{

    private static final LetterBag LETTER_BAG = new LetterBag();
    private static final int MAX_PLAYERS = 4;
    private static final ArrayBlockingQueue<Player> PLAYER_ORDER = new ArrayBlockingQueue<>(MAX_PLAYERS);
    private static final ArrayList<Player> PLAYER_LIST = new ArrayList<>();
    private static final ArrayList<Move> MOVE_LIST = new ArrayList<>();
    private static Timer timer = new Timer();
    private static Move currentMove;
    private static Player currentPlayer;
    private static int turmTime = 60000;
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
        return PLAYER_LIST.size();
    }


    /* GAME FUNCTIONS */
    /**
     * Starts game. Adds Tiles to all Players hands. Calls startTurn
     */
    public static void start() {
        LETTER_BAG.fill();
        for (Player player : PLAYER_LIST) {
            player.addTiles();
        }
        aiTest();
    }
    /**
     * Gets next Player and sets it to the current Player. Creates a new Move.
     */
    public static void startTurn() {
        new Thread(timer).start();
        currentPlayer = PLAYER_ORDER.poll();
        PLAYER_ORDER.add(currentPlayer);

        if (currentPlayer instanceof HumanPlayer) {
            currentMove = new HumanMove(currentPlayer);
        } else if (currentPlayer instanceof AIPlayer) {
            currentMove = new AIMove(currentPlayer);
        }

        MOVE_LIST.add(currentMove);
        timer.startTimer();

        if(currentPlayer instanceof AIPlayer) {
            currentPlayer.play();
        }
    }
    /**
     * Ends current turn, increments Player score by the score of the Move.
     */
    public static void endTurn() {
        if(currentMove instanceof AIMove) {
            Board.getInstance().resetPartial();
            currentMove.endMove();
            currentMove = null;
            currentPlayer = null;
            timer = new Timer();
        } else if (timer.getTimeLeft() > 0) {
            if (currentMove instanceof HumanMove) {
                if (currentMove.getPlayedTiles().size() > 0 && ((HumanMove) currentMove).getResult().isCompleteWord()) {
                    if (validator.testConnectedWords(currentMove)) {
                        Board.getInstance().resetPartial();
                        currentMove.endMove();
                        currentMove = null;
                        currentPlayer = null;
                        timer = new Timer();
                    } else {
                        JOptionPane.showMessageDialog(null, "Words must be connected!", "Error", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    Board.getInstance().resetPartial();
                    currentMove.endMove();
                    currentMove = null;
                    currentPlayer = null;
                    timer = new Timer();
                }
            } else if (currentMove.getPlayedTiles().isEmpty() && timer.getTimeLeft() > 0) {
                Board.getInstance().resetPartial();
                currentMove.endMove();
                currentMove = null;
                currentPlayer = null;
                timer = new Timer();
            }
        } else {
            Board.getInstance().resetPartial();
            currentMove.invalidateMove();
            currentMove = null;
            currentPlayer = null;
            timer = new Timer();
        }
        System.out.println(timer.getTime());
    }

    public static void reset() {
        currentPlayer = null;
        currentMove = null;
        Board.getInstance().clearBoard();
        timer = new Timer();
        LETTER_BAG.empty();
        PLAYER_ORDER.clear();
        PLAYER_LIST.clear();
        MOVE_LIST.clear();
    }

    /**
     * Returns the in progress Move.
     * @return  move    In progress Move.
     */
    public static Move getCurrentMove() {
        return currentMove;
    }

    public static int getTurmTime() {
        return turmTime;
    }


    /* GAME PIECES */
    /**
     * Returns this Game's LetterBag.
     * @return  letterbag       This Game's LetterBag.
     */
    public static LetterBag getLetterBag() {
        return LETTER_BAG;
    }
    public static Timer getTimer() {
        return timer;
    }
    public static ArrayList<Move> getMoveList() {
    	return MOVE_LIST;
    }      
    public static ArrayBlockingQueue<Player> getPlayersOrder(){
    	return PLAYER_ORDER;
    }
    public static void setCurrentPlayer(Player player){
    	currentPlayer = player;
    }
    public static void addPlayerCheatForSaves(Player player){
    	PLAYER_LIST.add(player);
    }
    public static void addPlayerOrderCheatForSaves(Player player){
    	PLAYER_ORDER.add(player);        
    } 
    public static void addMove(Move move){
    	MOVE_LIST.add(move);
    }


	

    //TEST
    private static void aiTest() {
        //Board.getInstance().place(new Tile('l', 1), new Coordinate(4,7));
        //Board.getInstance().place(new Tile('o', 1), new Coordinate(5,7));
        //Board.getInstance().place(new Tile('c', 1), new Coordinate(6,7));

        Board.getInstance().place(new Tile('k', 5), new Coordinate(7,7));
        Board.getInstance().place(new Tile('e', 1), new Coordinate(7,8));
        Board.getInstance().place(new Tile('y', 4), new Coordinate(7,9));

        /*
        Board.getInstance().place(new Tile('e', 1), new Coordinate(7,8));
        Board.getInstance().place(new Tile('x', 8), new Coordinate(7,9));

        Board.getInstance().place(new Tile('d', 2), new Coordinate(7,4));
        Board.getInstance().place(new Tile('h', 4), new Coordinate(7,5));
        Board.getInstance().place(new Tile('a', 1), new Coordinate(7,6));

        Board.getInstance().place(new Tile('e', 1), new Coordinate(8,4));
        Board.getInstance().place(new Tile('l', 1), new Coordinate(9,4));
        Board.getInstance().place(new Tile('f', 4), new Coordinate(10,4));
        Board.getInstance().place(new Tile('t', 1), new Coordinate(11,4));
        Board.getInstance().place(new Tile('s', 1), new Coordinate(12,4));
        */
        //Board.getInstance().place(new Tile('j', 1), new Coordinate(5,9));
        //Board.getInstance().place(new Tile('a', 1), new Coordinate(6,9));
    }
}
