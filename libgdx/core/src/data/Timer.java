package data;

import scrabble.Game;

public class Timer implements Runnable {
    private static int time = 0;

    public void run() {
        while(Game.getCurrentMove() != null) {
            try {
                Thread.sleep(970);
                time++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        time = 0;
    }

    public static int getTime() {
        return time;
    }

    public static int getTimeLeft() {
        return Game.getTurmTime() - time;
    }

    public static String timeFormatter(int time) {
        int minutes = time / 60;
        int seconds = time % 60;

        System.out.println(minutes);
        return (minutes + " : " + seconds);
    }
}
