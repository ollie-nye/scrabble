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
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Game object contains information related to current game session. Created
 * whenever a new game is started.
 *
 * @author Tom Geraghty
 * @version 1.0
 */
public class Game implements Serializable {

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
    private static final HashMap<Coordinate, Tile> SHUFFLE_TILES = new HashMap<>();
    private static Tile lastTile;


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
     *
     * @return player      Player making current Move.
     */
    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Returns all Players in Game.
     *
     * @return players     All current Players
     */
    public static ArrayList<Player> getPlayers() {
        return PLAYER_LIST;
    }

    /**
     * Returns the amount of Players in the Game.
     *
     * @return amount      Number of players in Game.
     */
    public static int getNumberOfPlayers() {
        return PLAYER_LIST.size();
    }


    public static Set<Coordinate> getShuffles() {
        return SHUFFLE_TILES.keySet();
    }

    public static Tile getShuffle(Coordinate c) {
        return SHUFFLE_TILES.get(c);
    }

    public static int getNumberOfShuffles() {
        return SHUFFLE_TILES.size();
    }

    public static void addShuffles(Coordinate c, Tile e) {
        SHUFFLE_TILES.put(c, e);
        lastTile = e;
    }

    public static void resetShuffles() {
        SHUFFLE_TILES.clear();
        lastTile = null;
    }

    public static Tile lastShuffled() {
        return lastTile;
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
        //aiTest();
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

        if (currentPlayer instanceof AIPlayer) {
            currentPlayer.play();
        }
    }

    /**
     * Ends current turn, increments Player score by the score of the Move.
     */
    public static void endTurn() {
        SHUFFLE_TILES.clear();
        if (currentMove instanceof AIMove) {
            Board.getInstance().resetPartial();
            currentMove.endMove();
            currentMove = null;
            currentPlayer = null;
            timer = new Timer();
        } else if (timer.getTimeLeft() > 0) {
            if (currentMove instanceof HumanMove) {

                if (currentMove.getPlayedTiles().size() > 0) {
                    Result lastResult = ((HumanMove) currentMove).getResult();
                    if (lastResult.isCompleteWord()) {
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
                        JOptionPane.showMessageDialog(null, "This is not a valid word!", "Error", JOptionPane.INFORMATION_MESSAGE);
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
            MOVE_LIST.remove(currentMove);
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
     *
     * @return move    In progress Move.
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
     *
     * @return letterbag       This Game's LetterBag.
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

    public static ArrayBlockingQueue<Player> getPlayersOrder() {
        return PLAYER_ORDER;
    }

    public static void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }

    public static void addPlayerCheatForSaves(Player player) {
        PLAYER_LIST.add(player);
    }

    public static void addPlayerOrderCheatForSaves(Player player) {
        PLAYER_ORDER.add(player);
    }

    public static void addMove(Move move) {
        MOVE_LIST.add(move);
    }

    public static void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream(new File("save.ser"));
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(PLAYER_LIST);
            out.writeObject(PLAYER_ORDER);
            out.writeObject(currentPlayer);
            out.writeObject(Board.getInstance().returnBoard());
            out.writeObject(LETTER_BAG);
            out.writeObject(MOVE_LIST);

            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in save.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static void load() {
        ArrayList<Player> players = null;
        ArrayBlockingQueue<Player> playersOrder = null;
        Player currentPlayerSaved = null;
        Tile[][] letters;
        LetterBag letterBag = null;
        ArrayList<Move> moveList = null;
        Game gamsu = new Game();

        try {
            FileInputStream fileIn = new FileInputStream("save.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);

            players = (ArrayList) in.readObject();
            playersOrder = (ArrayBlockingQueue) in.readObject();
            currentPlayerSaved = (Player) in.readObject();
            letters = (Tile[][]) in.readObject();
            letterBag = (LetterBag) in.readObject();
            moveList = (ArrayList) in.readObject();

            in.close();
            fileIn.close();

            Board.getInstance().setBoard(letters);
            Game.getLetterBag().setList(letterBag.getList());
            System.out.println(letterBag.pick().getContent() + letterBag.pick().getContent()
                    + letterBag.pick().getContent() + letterBag.pick().getContent());

            PLAYER_LIST.addAll(players);
            MOVE_LIST.addAll(moveList);

            for (int i = 0; i < (players.size() - 1); i++) {
                Player playerOrder = playersOrder.poll();
                playersOrder.add(playerOrder);
            }

            currentPlayer = playersOrder.peek();

            for (int i = 0; i < playersOrder.size(); i++) {
                PLAYER_ORDER.add(playersOrder.poll());
            }

        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return;
        }
    }
}
