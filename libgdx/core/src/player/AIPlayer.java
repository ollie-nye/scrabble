package player;

import data.Coordinate;
import data.Tile;
import data.Word;
import scrabble.Board;
import scrabble.Game;
import scrabble.Score;
import validation.Dictionary;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Contains methods specific to AIPlayer player type. Extends 'Player ' abstract class.
 *
 * @author Thomas Geraghty
 * @version 1.1
 */
public class AIPlayer extends Player {
    private Dictionary dictionary = new Dictionary();

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
        if (Board.getInstance().isEmpty()) {
            char[] characters = calculateBestWord(findPossibleWords(new Coordinate(7, 7))).toCharArray();
            // for each character in the word
            int i = 0;
            for (char character : characters) {
                // play the tile from hand.
                for (Tile tile : super.getTiles()) {
                    if (tile != null && tile.getChar() == character) {
                        Game.getCurrentMove().addTile(tile, new Coordinate(7 + i, 7));
                        break;
                    }
                }
                i++;
            }
        } else {
            // For each square in board, find the moves

            for (int y = 0; y < 15; y++) {
                for (int x = 0; x < 15; x++) {
                    Coordinate coordinate = new Coordinate(x, y);
                    Tile tile = Board.getInstance().getTile(coordinate);
                    if (tile != null) {
                        findMoves(coordinate, findPossibleWords(coordinate));
                    }
                }
            }
        }
        Game.endTurn();
    }

    private ArrayList<Word> findMoves(Coordinate coordinate, ArrayList<String> words) {
        char letterOnBoard = Board.getInstance().getTile(coordinate).getChar();
        ArrayList<Word> moves = new ArrayList<>();

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

            int verticalScore = calculateVerticalMove(word, prefix, suffix, coordinate);
            int horizontalScore = calculateHorizontalMove(word, prefix, suffix, coordinate);

            if(verticalScore > 0) {
                moves.add(new Word(word, verticalScore));
            }
            if(horizontalScore > 0) {
                moves.add(new Word(word, horizontalScore));
            }

            //TEST
            System.out.print("Word: " + word + " ");
            System.out.print("Character: " + letterOnBoard + " ");
            System.out.print("Prefix: " + prefix + " ");
            System.out.print("Suffix: " + suffix + " ");
            System.out.print("Horizontal score: " + calculateHorizontalMove(word, prefix, suffix, coordinate) + " ");
            System.out.println("Vertical score: " + calculateVerticalMove(word, prefix, suffix, coordinate) + " ");
            //isWordPlayable(word, prefix, suffix, coordinate);
        }
        return moves;
    }

    private Word calculateBestMove(ArrayList<Word> words) {
        Word bestWord = null;
        int wordScore = 0;
        for (Word word : words) {
            int tempScore = word.getScore();
            if (tempScore > wordScore) {
                wordScore = tempScore;
                bestWord = word;
            }
        }
        return bestWord;
    }

    private void playMove(Word word) {
    }

    private int calculateVerticalMove(String word, ArrayList<Character> prefix, ArrayList<Character> suffix, Coordinate coordinate) {
        Coordinate tempCoordinate;
        boolean prefixFit = true;
        boolean suffixFit = true;
        int wordMultiplier = 1;
        int score = 0;

        if (prefix.size() > 0) {
            // check prefix
            tempCoordinate = new Coordinate(coordinate.getX(), coordinate.getY() - prefix.size());
            if (Board.getInstance().getTile(tempCoordinate.getNear('D')) == null) {
                // if free,
                int prefixLength = prefix.size();
                // check each tile is free above suffix last character. Decrement suffixLength with each empty tile.
                while (prefixLength > 0 && Board.getInstance().getTile(tempCoordinate) == null) {
                    int letterScore = Score.getLetterScore(prefix.get(prefix.size() - prefixLength));
                    int letterMultiplier = Score.getWordMultiplier(tempCoordinate);
                    wordMultiplier *= Score.getWordMultiplier(tempCoordinate);
                    score += letterScore * letterMultiplier;

                    prefixLength--;
                    tempCoordinate = tempCoordinate.getNear('D');
                }
                // if suffixLength is more then 0, then not all spaces where free and word cannot be placed.
                if (prefixLength != 0) {
                    prefixFit = false;
                }
            } else {
                prefixFit = false;
            }
        }

        if (suffix.size() > 0) {
            // check suffix
            tempCoordinate = new Coordinate(coordinate.getX(), coordinate.getY() + suffix.size());
            if (Board.getInstance().getTile(tempCoordinate.getNear('U')) == null) {
                // if free,
                int suffixLength = suffix.size();
                // check each tile is free above suffix last character. Decrement suffixLength with each empty tile.
                while (suffixLength > 0 && Board.getInstance().getTile(tempCoordinate) == null) {
                    int letterScore = Score.getLetterScore(suffix.get(suffix.size() - suffixLength));
                    int letterMultiplier = Score.getWordMultiplier(tempCoordinate);
                    wordMultiplier *= Score.getWordMultiplier(tempCoordinate);
                    score += letterScore * letterMultiplier;

                    suffixLength--;
                    tempCoordinate = tempCoordinate.getNear('U');
                }
                // if suffixLength is more then 0, then not all spaces where free and word cannot be placed.
                if (suffixLength != 0) {
                    suffixFit = false;
                }
            } else {
                suffixFit = false;
            }
        }

        if (prefixFit || suffixFit) {
            return score * wordMultiplier;
        } else {
            return 0;
        }
    }

    private int calculateHorizontalMove(String word, ArrayList<Character> prefix, ArrayList<Character> suffix, Coordinate coordinate) {
        Coordinate tempCoordinate;
        boolean prefixFit = true;
        boolean suffixFit = true;
        int wordMultiplier = 1;
        int score = 0;

        // Horizontal fit
        if (prefix.size() > 0) {
            // check prefix
            tempCoordinate = new Coordinate(coordinate.getX() - prefix.size(), coordinate.getY());
            if (Board.getInstance().getTile(tempCoordinate.getNear('L')) == null) {
                // if free,
                int prefixLength = prefix.size();
                // check each tile is free above suffix last character. Decrement suffixLength with each empty tile.
                while (prefixLength > 0 && Board.getInstance().getTile(tempCoordinate) == null) {
                    int letterScore = Score.getLetterScore(prefix.get(prefix.size() - prefixLength));
                    int letterMultiplier = Score.getWordMultiplier(tempCoordinate);
                    wordMultiplier *= Score.getWordMultiplier(tempCoordinate);
                    score += letterScore * letterMultiplier;

                    prefixLength--;
                    tempCoordinate = tempCoordinate.getNear('L');
                }
                // if suffixLength is more then 0, then not all spaces where free and word cannot be placed.
                if (prefixLength != 0) {
                    prefixFit = false;
                }
            } else {
                prefixFit = false;
            }
        }

        if (suffix.size() > 0) {
            // check suffix
            tempCoordinate = new Coordinate(coordinate.getX() + suffix.size(), coordinate.getY());
            if (Board.getInstance().getTile(tempCoordinate.getNear('R')) == null) {
                // if free,
                int suffixLength = suffix.size();
                // check each tile is free above suffix last character. Decrement suffixLength with each empty tile.
                while (suffixLength > 0 && Board.getInstance().getTile(tempCoordinate) == null) {
                    int letterScore = Score.getLetterScore(suffix.get(suffix.size() - suffixLength));
                    int letterMultiplier = Score.getWordMultiplier(tempCoordinate);
                    wordMultiplier *= Score.getWordMultiplier(tempCoordinate);
                    score += letterScore * letterMultiplier;

                    suffixLength--;
                    tempCoordinate = tempCoordinate.getNear('R');
                }
                // if suffixLength is more then 0, then not all spaces where free and word cannot be placed.
                if (suffixLength != 0) {
                    suffixFit = false;
                }
            } else {
                suffixFit = false;
            }
        }
        if (prefixFit || suffixFit) {
            return score * wordMultiplier;
        } else {
            return 0;
        }
    }

    private int contains(char[] word, char letter) {
        for (int i = 0; i < word.length; i++) {
            if (letter == word[i]) {
                return i;
            }
        }
        return -1;
    }

    private ArrayList<String> findPossibleWords(Coordinate coordinate) {
        Tile tile = Board.getInstance().getTile(coordinate);
        ArrayList<String> possibleWords = new ArrayList<>();
        char[] characters;
        char letter;

        // board position empty.
        if (tile != null) {
            // extend array by 1 to take into consideration already played tile
            letter = Board.getInstance().getTile(coordinate).getChar();
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
        for (int i = 0; i < dictionary.getSize(); i++) {
            char[] word = dictionary.getWords().get(i).toCharArray();
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
        Collections.sort(possibleWords);
        return possibleWords;
    }

    private int calculateScore(String word) {
        // TODO: This needs to take into account the extra multiplier stuff.
        int score = 0;
        for (Character letter : word.toCharArray()) {
            score += Score.getLetterScore(letter);
        }
        return score;
    }

    private String calculateBestWord(ArrayList<String> words) {
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
