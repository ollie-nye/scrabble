package scrabble;

import data.Move;
import player.AIPlayer;
import player.HumanPlayer;
import player.Player;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

 /**
  * @author Tom Geraghty
  * @version 1.0
  * */
public class Game {

    private static final LetterBag LETTER_BAG = new LetterBag();
    private static final int MAX_PLAYERS = 4;
    private static final ArrayBlockingQueue<Player> PLAYER_ORDER = new ArrayBlockingQueue<>(MAX_PLAYERS);
    private static final ArrayList<Player> PLAYER_LIST = new ArrayList<>();
    private static final ArrayList<Move> MOVE_LIST = new ArrayList<>();
    private static Move currentMove;
    private static Player currentPlayer;
    private static int numberOfPlayers;


    /* PLAYER FUNCTIONS */
    public static void addPlayer(String playerName, int playerType) {
        if(PLAYER_LIST.size() < MAX_PLAYERS) {
            Player player;
            switch(playerType) {
                case 1: default:
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
    public static void removePlayer(Player player) {
        PLAYER_LIST.remove(player);
    }


    /* GAME FUNCTIONS */
    public static void start() {
        int i = 1;
        for(Player player : PLAYER_LIST) {
            player.addTiles();
            System.out.println("Player: " + i + player);
            i++;
        }
        startTurn();
    }
    public static void endTurn() {
        currentPlayer.setScore(currentPlayer.getScore() + currentMove.getMoveScore());
        currentPlayer.addTiles();
    }
    public static void startTurn() {
        currentPlayer = PLAYER_ORDER.poll();
        PLAYER_ORDER.add(currentPlayer);

        currentMove = new Move(currentPlayer);
        MOVE_LIST.add(currentMove);
    }


    /* GAME INSTANCE FUNCTIONS */
    public static Player getCurrentPlayer() {
        return currentPlayer;
    }
    public static ArrayList<Player> getPlayers() {
        return PLAYER_LIST;
    }
    public static int getNumberOfPlayers() {
        return numberOfPlayers;
    }
    public static LetterBag getLetterBag() {
        return LETTER_BAG;
    }
    public static Move getCurrentMove() {
        return currentMove;
    }
}
