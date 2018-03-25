package data.move;

import data.Coordinate;
import data.Tile;
import player.Player;
import scrabble.Board;
import scrabble.Game;
import scrabble.Score;

import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;

/**
 * move object contains all played letters in a turn (move), the word played,
 * the current score of the turn and who played the turn.
 *
 * @author Tom Geraghty
 * @version 1.2
 */
public abstract class Move implements Serializable {
    final static Score SCORE_CALCULATOR = new Score();
    final HashMap<Tile, Coordinate> playedTiles = new HashMap<>();
    final Board board = Board.getInstance();
    final Player player;
    String playedWord = "";
    int wordMultiplier = 1;
    int moveTime;
    int moveScore;


    public Move(Player player) {
        this.player = player;
    }

    /* PLAYED TILES */

    public abstract void addTile(Tile tile, Coordinate coordinate);

    public abstract void removeTile(Tile tile);

    /**
     * Returns all Tiles played in this move.
     *
     * @return tiles        Tiles played in this move.
     */
    public HashMap<Tile, Coordinate> getPlayedTiles() {
        return playedTiles;
    }


    /* PLAYED WORD */

    /**
     * Set the word played in this move.
     *
     * @param playedWord The word played in this move.
     */
    public void setPlayedWord(String playedWord) {
        this.playedWord = playedWord;
    }

    /**
     * Returns the word played in this move
     *
     * @return word               The played word
     */
    public String getPlayedWord() {
        return playedWord;
    }


    /* PLAYER */

    /**
     * Returns the Player of this move.
     *
     * @return player      Player who playing/played this move.
     */
    public Player getPlayer() {
        return player;
    }


    /* SCORE */

    /**
     * Returns current score of move.
     *
     * @return score       Current score of move.
     */
    public int getMoveScore() {
        return moveScore * wordMultiplier;
    }

    /* MOVE FUNCTIONS */
    public void endMove() {
        int tempScore = 0;
        moveTime = Game.getTimer().getTime();
        this.playedWord = playedWord;
        for(Coordinate coordinate : playedTiles.values()) {
            tempScore += calculateScoreVertical(coordinate);
            tempScore += calculateScoreHorizontal(coordinate);
        }
        System.out.println("tempscore : " + tempScore);
        player.setScore(player.getScore() + getMoveScore() + tempScore);
        player.addTiles();
    }

    public void calculateWord() {
        if(Game.getMoveList().size() == 1) {
            return;
        }

        char direction = 'H';
        int directionCoord = -1;
        for (Entry<Tile, Coordinate> tile : playedTiles.entrySet()) {
            if (tile.getValue().getY() == directionCoord || directionCoord == -1) {
                directionCoord = tile.getValue().getY();
            } else {
                direction = 'V';
                break;
            }
        }

        ArrayList<Entry<Tile, Coordinate>> wordTiles = new ArrayList<>(playedTiles.entrySet());


        ArrayList<Character> suffixList = new ArrayList<>();
        ArrayList<Character> prefixList = new ArrayList<>();
        Character connectedTile = ' ';
        Coordinate startingCoordinate = null;
        Coordinate connectedCoordinate = null;
        boolean prefix = true;
        int min = 15;
        int max = 0;

        if (direction == 'H') {
            int y = wordTiles.get(0).getValue().getY();

            for (Entry<Tile, Coordinate> tile : wordTiles) {
                if (tile.getValue().getX() < min) {
                    min = tile.getValue().getX();
                }
                if (tile.getValue().getX() > max) {
                    max = tile.getValue().getX();
                }
            }

            for (int i = min; i <= max; i++) {
                Coordinate temp = new Coordinate(i, y);
                Tile tile = Board.getInstance().getTile(temp);
                if (!playedTiles.containsKey(tile)) {
                    connectedTile = tile.getChar();
                    connectedCoordinate = temp;
                    prefix = false;
                } else if (prefix) {
                    if (prefixList.isEmpty()) {
                        startingCoordinate = temp;
                    }
                    prefixList.add(tile.getChar());
                } else {
                    suffixList.add(tile.getChar());
                }
            }

        } else {
            int x = wordTiles.get(0).getValue().getX();

            for (Entry<Tile, Coordinate> tile : wordTiles) {
                if (tile.getValue().getY() < min) {
                    min = tile.getValue().getY();
                }
                if (tile.getValue().getY() > max) {
                    max = tile.getValue().getY();
                }
            }

            for (int i = min; i <= max; i++) {
                Coordinate temp = new Coordinate(x, i);
                Tile tile = Board.getInstance().getTile(temp);
                if (!playedTiles.containsKey(tile)) {
                    connectedTile = tile.getChar();
                    connectedCoordinate = temp;
                    prefix = false;
                } else if (prefix) {
                    if (prefixList.isEmpty()) {
                        startingCoordinate = temp;
                    }
                    prefixList.add(tile.getChar());
                } else {
                    suffixList.add(tile.getChar());
                }
            }
        }

        String word = "";
        for (Character letter : prefixList) {
            word += letter;
        }
        word += connectedTile;
        for (Character letter : suffixList) {
            word += letter;
        }

        if (connectedTile == ' ') {
            prefixList.clear();
            connectedCoordinate = startingCoordinate;
        }

        char[] fullWord = Player.workCheck(word, prefixList, suffixList, connectedCoordinate, direction).toCharArray();
        ArrayList<Character> scoredWord = new ArrayList<>();
        for(char letter : fullWord) {
            scoredWord.add(letter);
        }

        for (char letter : prefixList) {
            if(scoredWord.contains(letter)) {
                scoredWord.remove(letter);
            }
        }

        for (char letter : suffixList) {
            if(scoredWord.contains(letter)) {
                scoredWord.remove(letter);
            }
        }

        for(Character character : scoredWord) {
            System.out.print(character);
        }
    }

    public int calculateScoreVertical(Coordinate coordinate) {
        Coordinate tempCoordinate = new Coordinate(coordinate.getX(), coordinate.getY() - 1);
        int tempScore = 0;

        while (tempCoordinate.getY() >= 0 && board.getTile(tempCoordinate) != null) {
            if(!playedTiles.containsKey(board.getTile(tempCoordinate))) {
                tempScore += board.getTile(tempCoordinate).getScore();
            }
            tempCoordinate = tempCoordinate.getNear('U');
        }

        tempCoordinate = new Coordinate(coordinate.getX(), coordinate.getY() + 1);

        while (tempCoordinate.getY() <= 14 && board.getTile(tempCoordinate) != null) {
            if(!playedTiles.containsKey(board.getTile(tempCoordinate))) {
                tempScore += board.getTile(tempCoordinate).getScore();
            }
            tempCoordinate = tempCoordinate.getNear('D');
        }

        return tempScore;
    }

    public int calculateScoreHorizontal(Coordinate coordinate) {
        Coordinate tempCoordinate = coordinate.getNear('L');;
        int tempScore = 0;


        while (tempCoordinate.getX() >= 0 && board.getTile(tempCoordinate) != null) {
            if(!playedTiles.containsKey(board.getTile(tempCoordinate))) {
                tempScore += board.getTile(tempCoordinate).getScore();
            }
            tempCoordinate = tempCoordinate.getNear('L');
        }

        tempCoordinate = new Coordinate(coordinate.getX() + 1, coordinate.getY());

        while (tempCoordinate.getX() <= 14 && board.getTile(tempCoordinate) != null) {
            if (!playedTiles.containsKey(board.getTile(tempCoordinate))) {
                tempScore += board.getTile(tempCoordinate).getScore();
            }
            tempCoordinate = tempCoordinate.getNear('R');
        }

        return tempScore;
    }

    public void calculateScore(String word) {

    }

    public void invalidateMove() {
        moveScore = 0;
        playedWord = "";
        moveTime = Game.getTimer().getTime();

        HashMap<Tile, Coordinate> tempMap = new HashMap<>(playedTiles);
        for (Tile tile : tempMap.keySet()) {
            removeTile(tile);
        }
    }

    public int getMoveTime() {
        return moveTime;
    }
}
