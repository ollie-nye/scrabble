package scrabble;

public class Main {
    private static Main instance = null;
    public static Game game;

    public static Main getInstance() {
        if (instance == null) {
            instance = new Main();
        }
        return instance;
    }
}
