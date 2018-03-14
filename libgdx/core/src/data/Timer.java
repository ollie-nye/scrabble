package data;

public class Timer implements Runnable {
    private static int time;

    public void run() {
        while(true) {
            try {
                Thread.sleep(970);
                time++;
                System.out.println(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getTime() {
        return time;
    }
}
