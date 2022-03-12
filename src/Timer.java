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
        if (time < 1) {
            return "0";
        }
        else {
            return String.format("%.0f", (System.currentTimeMillis() - time) / 1000F);
        }
    }
}
