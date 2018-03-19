package data;

import scrabble.Game;

public class Timer implements Runnable {
    private int time = 0;
    private boolean paused = false;

    public void run() {
        while (true) {
            if (Game.getTimer() == this) {
                if (!getPaused()) {
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
            } else {
                break;
            }
        }
    }

    public int getTime() {
        return time;
    }

    public void pauseTimer() {
        paused = true;
    }

    public void startTimer() {
        paused = false;
    }

    public synchronized boolean getPaused() {
        return paused;
    }

    public int getTimeLeft() {
        return Game.getTurmTime() - time;
    }

    public void reset() {
        time = 0;
    }

    public static String timeFormatter(int time) {
        int minutes = time / (60 * 1000);
        int seconds = (time / 1000) % 60;
        int nanoseconds = time % 1000;

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(String.format("|%02d|.|%02d|", minutes, seconds));

        return stringBuffer.toString();
    }
}
