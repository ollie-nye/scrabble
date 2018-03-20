package player;

import data.Move.AIMove;
import data.Coordinate;
import data.Tile;
import data.Tuple;
import scrabble.Board;
import scrabble.Game;
import scrabble.Score;
import validation.Dictionary;

import java.io.Serializable;
import java.util.*;

/**
 * Contains methods specific to AIPlayer player type. Extends 'Player ' abstract class.
 *
 * @author Thomas Geraghty
 * @version 1.1
 */
public class AIPlayer extends Player implements Serializable {
    HashMap<Character, HashSet<String>> cache = new HashMap<>();


    /**
     * Constructor for AIPlayer, sets player username based on passed parameter 'name'.
     * Adds 7 letters from LetterBag to player's collection of letters.
     *
     * @param name Player username string
     * @see player.Player
     */
    public AIPlayer(String name) {
        super();
        setPlayerName(name);
    }

    public void play() {
        // first play of game logic
        if (board.isEmpty()) {
            char[] characters = calculateBestWord(findPossibleWords(new Coordinate(7, 7))).toCharArray();
            // for each character in the word
            int i = 0;
            int score = 0;
            ArrayList<Tuple<Character, Coordinate>> playedLetters = new ArrayList<>();
            for (char character : characters) {
                // play the tile from hand.
                for (Tile tile : super.getTiles()) {
                    if (tile != null && tile.getChar() == character) {
                        Game.getCurrentMove().addTile(tile, new Coordinate(7 + i, 7));
                        playedLetters.add(new Tuple<>(character,new Coordinate(7 + i, 7)));
                        break;
                    }
                }
                i++;
            }
            ((AIMove) Game.getCurrentMove()).setScore( calculateMoveScore(playedLetters));
        } else {
            // For each square in board, find the moves
            ArrayList<ArrayList<Tuple<Character, Coordinate>>> allMoves = new ArrayList<>();
            for (int y = 0; y < 15; y++) {
                for (int x = 0; x < 15; x++) {
                    Coordinate coordinate = new Coordinate(x, y);
                    Tile tile = board.getTile(coordinate);
                    if (tile != null) {
                        allMoves.addAll(findMoves(coordinate, cacheCheck(coordinate)));
                    }
                }
            }
            for(Tile tile : super.getTiles()) {
                System.out.print(tile + ", ");
            }

            Tuple<ArrayList<Tuple<Character, Coordinate>>, Integer> moveToPlay = calculateBestMove(allMoves);

            playMove(moveToPlay.getLeft());
            ((AIMove) Game.getCurrentMove()).setScore(moveToPlay.getRight());
        }

        cache.clear();
        Game.endTurn();
    }

    private void playMove(ArrayList<Tuple<Character, Coordinate>> move) {
        Tuple<Character, Coordinate> placedLetter = move.get(move.size() - 1);
        move.remove(placedLetter);
        Tile[] temp = super.getTiles().clone();
        for (Tuple<Character, Coordinate> letter : move) {
            for (Tile tile : temp) {
                if (tile.getChar() == letter.getLeft()) {
                    Game.getCurrentMove().addTile(tile, letter.getRight());
                    break;
                }
            }
        }
    }

    private ArrayList<ArrayList<Tuple<Character, Coordinate>>> findMoves(Coordinate coordinate, HashSet<String> words) {
        char letterOnBoard = board.getTile(coordinate).getChar();
        ArrayList<ArrayList<Tuple<Character, Coordinate>>> moves = new ArrayList<>();

        // for each word
        for (String word : words) {
            ArrayList<Character> prefix = new ArrayList<>();
            ArrayList<Character> suffix = new ArrayList<>();

            // find first instance of letter in word
            int boardLetterIndex = word.indexOf(letterOnBoard);
            char[] wordChars = word.toCharArray();

            if (boardLetterIndex != 0) {
                // prefix
                for (int i = 0; i < boardLetterIndex; i++) {
                    prefix.add(wordChars[i]);
                }
            }
            if (boardLetterIndex != wordChars.length - 1) {
                // suffix
                for (int i = boardLetterIndex + 1; i < wordChars.length; i++) {
                    suffix.add(wordChars[i]);
                }
            }

            ArrayList<Tuple<Character, Coordinate>> verticalMove = calculateVerticalMove(word, prefix, suffix, coordinate);
            ArrayList<Tuple<Character, Coordinate>> horizontalMove = calculateHorizontalMove(word, prefix, suffix, coordinate);

            if (verticalMove != null) {
                verticalMove.add(new Tuple<>(letterOnBoard, coordinate));
                moves.add(verticalMove);
            }
            if (horizontalMove != null) {
                horizontalMove.add(new Tuple<>(letterOnBoard, coordinate));
                moves.add(horizontalMove);
            }
        }
        return moves;
    }

    private ArrayList<Tuple<Character, Coordinate>> calculateVerticalMove(String word, ArrayList<Character> prefix, ArrayList<Character> suffix, Coordinate coordinate) {
        ArrayList<Tuple<Character, Coordinate>> charCoordinates = new ArrayList<>();
        Coordinate tempCoordinate;
        boolean possible = true;



        if ((prefix.size() > 0 && board.getTile(coordinate.getNear('U')) != null) ||
                (suffix.size() > 0 && board.getTile(coordinate.getNear('D')) != null)) {
            return null;
        }

        if((prefix.size() > 0 && (coordinate.getY() - prefix.size()) >= 0 && board.getTile(new Coordinate(coordinate.getX(), coordinate.getY() - prefix.size())) != null) ||
                (suffix.size() > 0 && (coordinate.getY() + suffix.size()) <= 14 && board.getTile(new Coordinate(coordinate.getX(), coordinate.getY() + suffix.size())) != null)) {
            return null;
        }

        String wordCheck = super.workCheck(word, prefix, suffix, coordinate, 'V');
        if(wordCheck == null) {
            return null;
        }

        if (prefix.size() > 0) {
            // check prefix
            tempCoordinate = new Coordinate(coordinate.getX(), coordinate.getY() - prefix.size());
            if ((tempCoordinate.getY() == 0 && board.getTile(tempCoordinate) == null)
                    || (tempCoordinate.getY() > 0 && board.getTile(tempCoordinate.getNear('D')) == null)) {
                // if free,
                int prefixLength = prefix.size();
                // check each tile is free above suffix last character. Decrement suffixLength with each empty tile.
                while (prefixLength > 0 && tempCoordinate.getY() > 0 && board.getTile(tempCoordinate) == null) {
                    if ((tempCoordinate.getX() < 14 && board.getTile(tempCoordinate.getNear('R')) == null)
                            && (tempCoordinate.getX() > 0 && board.getTile(tempCoordinate.getNear('L')) == null)) {

                        charCoordinates.add(new Tuple<>(prefix.get(prefix.size() - prefixLength), tempCoordinate));
                        prefixLength--;
                        tempCoordinate = tempCoordinate.getNear('D');
                    } else {
                        break;
                    }
                }
                // if suffixLength is more then 0, then not all spaces where free and word cannot be placed.
                if (prefixLength != 0) {
                    possible = false;
                }
            } else {
                possible = false;
            }
        }
        if (!possible) {
            return null;
        }

        if (suffix.size() > 0) {
            // check suffix
            tempCoordinate = coordinate.getNear('D');
            if ((tempCoordinate.getY() == 14 && board.getTile(tempCoordinate) == null)
                    || (tempCoordinate.getY() < 14 && board.getTile(tempCoordinate.getNear('D')) == null)) {
                int suffixLength = 0;
                // check each tile is free above suffix last character. Decrement suffixLength with each empty tile.
                while (suffixLength < suffix.size() && tempCoordinate.getY() < 14 && board.getTile(tempCoordinate) == null) {
                    if ((tempCoordinate.getX() < 14 && board.getTile(tempCoordinate.getNear('R')) == null)
                            && (tempCoordinate.getX() > 0 && board.getTile(tempCoordinate.getNear('L')) == null)) {

                        charCoordinates.add(new Tuple<>(suffix.get(suffixLength), tempCoordinate));
                        suffixLength++;
                        tempCoordinate = tempCoordinate.getNear('D');
                    } else {
                        break;
                    }
                }
                if (suffixLength != suffix.size()) {
                    possible = false;
                }
            } else {
                possible = false;
            }
            if (!possible) {
                return null;
            }
        }
        return charCoordinates;
    }

    private ArrayList<Tuple<Character, Coordinate>> calculateHorizontalMove(String word, ArrayList<Character> prefix, ArrayList<Character> suffix, Coordinate coordinate) {
        Coordinate tempCoordinate;
        boolean possible = true;
        ArrayList<Tuple<Character, Coordinate>> charCoordinates = new ArrayList<>();

        if ((prefix.size() > 0 && coordinate.getX() > 0 && board.getTile(coordinate.getNear('L')) != null) ||
                (suffix.size() > 0 && coordinate.getX() < 14 && board.getTile(coordinate.getNear('R')) != null)) {
            return null;
        }

        if((prefix.size() > 0 && (coordinate.getX() - prefix.size()) >= 0 && board.getTile(new Coordinate(coordinate.getX() - prefix.size(), coordinate.getY())) != null) ||
                (suffix.size() > 0 && (coordinate.getX() + suffix.size()) <= 14 && board.getTile(new Coordinate(coordinate.getX() + suffix.size(), coordinate.getY())) != null)) {
            return null;
        }

        String wordCheck = super.workCheck(word, prefix, suffix, coordinate, 'H');
        if(wordCheck == null) {
            return null;
        }


        // Horizontal fit
        if (prefix.size() > 0) {
            // check prefix
            tempCoordinate = new Coordinate(coordinate.getX() - prefix.size(), coordinate.getY());
            if ((tempCoordinate.getX() == 0 && board.getTile(tempCoordinate) == null) ||
                    (tempCoordinate.getX() > 0 && board.getTile(tempCoordinate.getNear('L')) == null)) {
                // if free,
                int prefixLength = prefix.size();
                // check each tile is free above suffix last character. Decrement suffixLength with each empty tile.
                while (prefixLength > 0 && tempCoordinate.getX() > 0 && board.getTile(tempCoordinate) == null) {
                    if ((tempCoordinate.getY() < 14 && board.getTile(tempCoordinate.getNear('D')) == null)
                            && (tempCoordinate.getY() > 0 && board.getTile(tempCoordinate.getNear('U')) == null)) {
                        charCoordinates.add(new Tuple<>(prefix.get(prefix.size() - prefixLength), tempCoordinate));

                        prefixLength--;
                        tempCoordinate = tempCoordinate.getNear('R');
                    } else {
                        break;
                    }
                }
                // if suffixLength is more then 0, then not all spaces where free and word cannot be placed.
                if (prefixLength != 0) {
                    possible = false;
                }
            } else {
                possible = false;
            }
        }
        if (!possible) {
            return null;
        }


        if (suffix.size() > 0) {
            // check suffix
            tempCoordinate = coordinate.getNear('R');
            if ((tempCoordinate.getX() == 14 && board.getTile(tempCoordinate) == null)
                    || (tempCoordinate.getX() < 14 && board.getTile(tempCoordinate.getNear('R')) == null)) {
                // if free,
                int suffixLength = 0;
                // check each tile is free above suffix last character. Decrement suffixLength with each empty tile.
                while (suffixLength < suffix.size() && tempCoordinate.getX() < 15 && board.getTile(tempCoordinate) == null) {
                    if ((tempCoordinate.getY() < 14 && board.getTile(tempCoordinate.getNear('D')) == null)
                            && (tempCoordinate.getY() > 0 && board.getTile(tempCoordinate.getNear('U')) == null)) {
                        charCoordinates.add(new Tuple<>(suffix.get(suffixLength), tempCoordinate));
                        suffixLength++;
                        tempCoordinate = tempCoordinate.getNear('R');
                    } else {
                        break;
                    }
                }
                // if suffixLength is more then 0, then not all spaces where free and word cannot be placed.
                if (suffixLength != suffix.size()) {
                    possible = false;
                }
            } else {
                possible = false;
            }
        }
        if (!possible) {
            return null;
        }
        return charCoordinates;
    }

    private HashSet<String> findPossibleWords(Coordinate coordinate) {
        Tile tile = board.getTile(coordinate);
        HashSet<String> possibleWords = new HashSet<>();
        char[] characters;
        char letter;

        // board position empty.
        if (tile != null) {
            // extend array by 1 to take into consideration already played tile
            letter = board.getTile(coordinate).getChar();
            characters = new char[super.getTiles().length + 1];
            characters[super.getTiles().length] = letter;
        } else {
            // no played tile so only use tiles in hand.
            letter = ' ';
            characters = new char[super.getTiles().length];
        }
        // fills characters array with the tile chars from players hand.
        for (int i = 0; i < super.getTiles().length; i++) {
            characters[i] = super.getTiles()[i].getChar();
        }
        // Loop over each word in the dictionary.
        for (String string : dictionary.getWords()) {
            char[] word = string.toCharArray();
            char[] tempChar = characters.clone();

            // for each char in current word
            for (int z = 0; z < word.length; z++) {
                int letterPosition = contains(tempChar, word[z]);

                // if it does contain letter, set to '.' , else break current char loop.
                if (letterPosition != -1) {
                    tempChar[letterPosition] = '.';

                    // for the final letter of the word, case for adding to possible words list.
                    if (z == word.length - 1) {
                        String newWord = new String(word);

                        // if letter isn't blank and is contained in the word, add to possible words
                        if (letter != ' ' && newWord.contains("" + letter)) {
                            possibleWords.add(newWord);
                        } else if (letter == ' ') {
                            possibleWords.add(newWord);
                        }
                    }
                } else {
                    break;
                }
            }
        }
        return possibleWords;
    }

    private Tuple<ArrayList<Tuple<Character, Coordinate>>, Integer> calculateBestMove(ArrayList<ArrayList<Tuple<Character, Coordinate>>> moves) {
        ArrayList<Tuple<Character, Coordinate>> bestMove = null;
        int wordScore = 0;
        for (ArrayList<Tuple<Character, Coordinate>> move : moves) {
            int tempScore = calculateMoveScore(move);
            if (tempScore > wordScore) {
                wordScore = tempScore;
                bestMove = move;
            }
        }

        Tuple<ArrayList<Tuple<Character, Coordinate>>, Integer> bestMovePossible = new Tuple(bestMove, wordScore);

        return bestMovePossible;
    }


    private int calculateMoveScore(ArrayList<Tuple<Character, Coordinate>> move) {
        int wordMultiplier = 1;
        int score = 0;
        for (Tuple<Character, Coordinate> letter : move) {
            int letterScore = Score.getLetterScore(letter.getLeft());
            int letterMultiplier = Score.getWordMultiplier(letter.getRight());
            wordMultiplier *= Score.getWordMultiplier(letter.getRight());
            score += letterScore * letterMultiplier;
        }
        return score * wordMultiplier;
    }


    private int contains(char[] word, char letter) {
        for (int i = 0; i < word.length; i++) {
            if (letter == word[i]) {
                return i;
            }
        }
        return -1;
    }

    private HashSet<String> cacheCheck(Coordinate coordinate) {
        char letter = board.getTile(coordinate).getChar();
        if (!cache.containsKey(letter)) {
            cache.put(letter, findPossibleWords(coordinate));
        }
        return cache.get(letter);
    }



    private int calculateScore(String word) {
        // TODO: This needs to take into account the extra multiplier stuff.
        int score = 0;
        for (Character letter : word.toCharArray()) {
            score += Score.getLetterScore(letter);
        }
        return score;
    }

    private String calculateBestWord(HashSet<String> words) {
        String bestWord = "";
        int wordScore = 0;
        for (String word : words) {
            int tempScore = calculateScore(word);
            if (tempScore > wordScore) {
                wordScore = tempScore;
                bestWord = word;
            }
        }
        return bestWord;
    }
}
