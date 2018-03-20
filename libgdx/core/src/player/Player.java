package player;

import java.io.Serializable;
import java.util.ArrayList;

import data.Coordinate;
import data.Tile;
import scrabble.Board;
import scrabble.Game;
import scrabble.LetterBag;
import validation.Dictionary;

/**
 * Superclass containing shared methods related to player component of game.
 *
 * @author Thomas Geraghty
 * @version 1.2
 */
public abstract class Player implements Serializable {

    private final Tile[] letterList = new Tile[7];
    private String playerName;
    private int score = 0;
    Board board = Board.getInstance();
    Dictionary dictionary = new Dictionary();
    private boolean finishedAllTurns = false;

    /* PLAYED NAME */

    /**
     * Sets player username to appear in game.
     *
     * @see #getPlayerName()
     */
    public void setPlayerName(String name) {
        if (!(name.equals(""))) {
            playerName = name;
        } else {
            playerName = "Player " + Game.getNumberOfPlayers();
        }
    }

    /**
     * Returns player username upon call
     *
     * @return name    Player username String
     * @see #setPlayerName(String)
     */
    public String getPlayerName() {
        return playerName;
    }

    /* SCORE */

    /**
     * Set player's score to passed parameter amount..
     *
     * @param score Score to set.
     * @see #getScore()
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Returns player's score on call.
     *
     * @return score   Score represented as integer.
     * @see #setScore(int)
     */
    public int getScore() {
        return score;
    }

    /* TILES IN HAND */

    /**
     * Adds letter to players current collection of letters (letterList array).
     * Adds letter in first position free in players letter collection.
     * Gets chosen letter from LetterBag 'pick' method..
     *
     * @see LetterBag#pick()
     */
    public void addTiles() {
        for (int i = 0; i < letterList.length; i++) {
            if (letterList[i] == null) {
                letterList[i] = Game.getLetterBag().pick();
            }
        }
    }

    /**
     * Returns a Tile to the Players hand.
     *
     * @param tile Tile to be returned.
     */
    public void returnTile(Tile tile) {
        for (int i = 0; i < letterList.length - 1; i++) {
            if (letterList[i] == null) {
                letterList[i] = tile;
                break;
            }
        }
    }

    /**
     * Sorts through array to find parameter tile then removes it from user array of tiles.
     *
     * @param tile Tile to remove
     */
    public void removeTile(Tile tile) {
        for (int i = 0; i < letterList.length; i++) {
            if (letterList[i] != null) {
                if (letterList[i] == tile) {
                    letterList[i] = null;
                    break;
                }
            }
        }
    }

    /**
     * Returns array of tiles that Player has.
     *
     * @return
     */
    public Tile[] getTiles() {
        //test

        /*
        Tile[] test = new Tile[] {
                new Tile('i', 1),
                new Tile('t', 1),
                new Tile('h', 4),
                new Tile('l', 1),
                new Tile('l', 1),
                new Tile('o', 1),
                new Tile('e', 1),
        };

       return test;
*/


       return letterList;
    }

    public void finishedAllTurns() {
        finishedAllTurns = true;
    }

    public String workCheck(String word, ArrayList<Character> prefix, ArrayList<Character> suffix, Coordinate coordinate, char direction) {
        Coordinate tempCoordinate;
        String tempWord = "";


        if (direction == 'V') {


            // prefix
            if (coordinate.getY() > 0) {

                tempCoordinate = new Coordinate(coordinate.getX(), coordinate.getY() - (prefix.size() + 1));
                if (tempCoordinate.getY() > 0 && board.getTile(tempCoordinate) != null) {
                    while (tempCoordinate.getY() > 0 && board.getTile(tempCoordinate) != null) {
                        tempWord += board.getTile(tempCoordinate).getContent();
                        tempCoordinate = tempCoordinate.getNear('U');
                    }
                    tempWord = new StringBuffer(tempWord).reverse().toString();
                }
            }

            tempWord += word;

            // suffix
            if (coordinate.getY() < 14) {
                tempCoordinate = new Coordinate(coordinate.getX(), coordinate.getY() + (suffix.size() + 1));
                if (tempCoordinate.getY() < 14 && board.getTile(tempCoordinate) != null) {
                    while (tempCoordinate.getY() < 14 && board.getTile(tempCoordinate) != null) {
                        tempWord += board.getTile(tempCoordinate).getContent();
                        tempCoordinate = tempCoordinate.getNear('D');
                    }
                }
            }
        }

        if (direction == 'H') {

            if(word.equals("eolith") && coordinate.getX() == 3 && coordinate.getY() == 7) {
                System.out.println("lol");
            }

            // prefix
            if (coordinate.getX() > 0) {
                tempCoordinate = new Coordinate(coordinate.getX() - (prefix.size() + 1), coordinate.getY());
                if (tempCoordinate.getX() > 0 && board.getTile(tempCoordinate) != null) {
                    while (tempCoordinate.getX() > 0 && board.getTile(tempCoordinate) != null) {
                        tempWord += board.getTile(tempCoordinate).getContent();
                        tempCoordinate = tempCoordinate.getNear('L');
                    }
                    tempWord = new StringBuffer(tempWord).reverse().toString();
                }
            }

            tempWord += word;

            // suffix
            if (coordinate.getX() < 14) {
                tempCoordinate = new Coordinate(coordinate.getX() + (suffix.size() + 1), coordinate.getY());
                if (tempCoordinate.getX() < 14 && board.getTile(tempCoordinate) != null) {
                    while (tempCoordinate.getX() < 14 && board.getTile(tempCoordinate) != null) {
                        tempWord += board.getTile(tempCoordinate).getContent();
                        tempCoordinate = tempCoordinate.getNear('R');
                    }
                }
            }
        }

        if (dictionary.getWords().contains(tempWord)) {
            return tempWord;
        } else {
            return null;
        }
    }


    public abstract void play();
}
