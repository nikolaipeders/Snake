public class Timer {

    public static long start;
    public static long time;

    public Timer() {

    }

    public void resetTimerToZero() {
        start = System.currentTimeMillis();
    }

    public void startDisplayTimer() {
        time = System.currentTimeMillis();
    }

    public long elapsedTime() {
        return System.currentTimeMillis() - start;
    }

    public String getElapsedTime() {
        return String.format("%.2f", (System.currentTimeMillis() - time) / 1000F);
    }
}
