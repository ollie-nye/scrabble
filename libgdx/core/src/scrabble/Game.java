package scrabble;

import player.*;

public class Game {

    private final LetterBag letterBag = new LetterBag();
    private final int maxPlayers = 4;
    private Player[] players;
    private Player currentPlayer;
    private int numberOfPlayers;

    public Game(int numberOfPlayers) {
        if(numberOfPlayers <= maxPlayers) {
            this.numberOfPlayers = numberOfPlayers;
        }
        players = new Player[numberOfPlayers];
    }

    public void addPlayer(String playerName, int playerType) {
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

    public LetterBag getLetterBag() {
        return letterBag;
    }

    public void play() {

    }
}
