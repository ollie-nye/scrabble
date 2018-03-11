package scrabble;

import player.AIPlayer;
import player.HumanPlayer;
import player.Player;

public class Game {

    private int numberOfPlayers;
    private Player[] players;
    private Player currentPlayer;

    public Game(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        players = new Player[numberOfPlayers];
    }

    public void addPlayer (String playerName, int playerType) {
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
                break;
            }
        }
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void play() {
        while(true) {

        }
    }
}
