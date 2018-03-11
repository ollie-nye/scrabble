package scrabble;

import player.AIPlayer;
import player.HumanPlayer;
import player.Player;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class Game {

    public static final LetterBag LETTER_BAG = new LetterBag();
    private static final int MAX_PLAYERS = 4;
    private static final Queue<Player> PLAYER_ORDER = new ArrayBlockingQueue<>(MAX_PLAYERS);
    private static Player[] players;
    private static Player currentPlayer;
    private static int numberOfPlayers;

    public Game(int numberOfPlayers) {
        if(numberOfPlayers <= MAX_PLAYERS) {
            this.numberOfPlayers = numberOfPlayers;
        }
        players = new Player[numberOfPlayers];
    }

    public static void addPlayer(String playerName, int playerType) {
        for(int i = 0; i < players.length; i++) {
            if(players[i] == null) {
                switch(playerType) {
                    case 1: default:
                        players[i] = new HumanPlayer(playerName);
                        break;
                    case 2:
                        players[i] = new AIPlayer(playerName);
                        break;
                }
                PLAYER_ORDER.add(players[i]);
                break;
            }
        }
    }
    public static void removePlayer(Player player) {
        for(int i = 0; i < players.length; i++) {
            if(players[i] == player) {
                players[i] = null;
            }
        }
    }
    public static Player[] getPlayers() {
        return players;
    }
    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static void start() {
        for(Player player : players) {
            player.addLetters();
        }
        nextTurn();
    }
    public static void nextTurn() {
        currentPlayer = PLAYER_ORDER.poll();
        PLAYER_ORDER.add(currentPlayer);
    }
}
