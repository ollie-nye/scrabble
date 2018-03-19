package data;

import scrabble.Game;

public class Timer implements Runnable {
    private static int time = 0;
    private static boolean paused = false;

    public void run() {
        while(true) {
            if(paused) {
                System.out.print("");
            } else {
                while (!paused) {
                    try {
                        Thread.sleep(1);
                        time++;
                        if (time == Game.getTurmTime()) {
                            Game.endTurn();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static int getTime() {
        return time;
    }

    public static int getTimeLeft() {
        return Game.getTurmTime() - time;
    }

    public static void pause() {
        paused = true;
    }

    public static void resume() {
        paused = false;
    }

    public static void reset() {
        time = 0;
        resume();
    }

    public static String timeFormatter(int time) {
        int minutes = time / (60 * 1000);
        int seconds = (time / 1000) % 60;
        int nanoseconds = time % 1000;

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(String.format("|%02d|.|%02d|",minutes,seconds));

        return stringBuffer.toString();
    }
}
