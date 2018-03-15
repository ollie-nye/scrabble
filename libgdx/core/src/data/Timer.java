package data;

import scrabble.Game;

public class Timer implements Runnable {
    private static int time = 0;

    public void run() {
        while(Game.getCurrentMove() != null) {
            try {
                Thread.sleep(1);
                time++;
                if(time == Game.getTurmTime()) {
                    Game.endTurn();
                }
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
        int minutes = time / (60 * 1000);
        int seconds = (time / 1000) % 60;
        int nanoseconds = time % 1000;

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(String.format("|%02d|.|%02d|.|%03d|",minutes,seconds,nanoseconds));

        return stringBuffer.toString();
    }
}
