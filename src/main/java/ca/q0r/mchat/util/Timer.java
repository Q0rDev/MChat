package ca.q0r.mchat.util;

/**
 * Class used when a Timer is needed.
 */
public class Timer {
    long start = 0;
    long end = 0;

    /**
     * Class Initializer, also starts timer.
     */
    public Timer() {
        start = System.nanoTime();
    }

    /**
     * Resets start and end values.
     */
    public void reset() {
        start = 0;
        end = 0;
    }

    /**
     * Starts timer.
     */
    public void start() {
        start = System.nanoTime();
    }

    /**
     * Stops timer.
     */
    public void stop() {
        end = System.nanoTime();
    }

    /**
     * Calculates Difference.
     * @return Difference between start and end values in milliseconds.
     */
    public long difference() {
        return differenceInMillis();
    }

    /**
     * Calculates Difference.
     * @return Difference between start and end values in seconds.
     */
    public long differenceInSeconds() {
        return differenceInMillis() / 1000;
    }

    /**
     * Calculates Difference.
     * @return Difference between start and end values in milliseconds.
     */
    public long differenceInMillis() {
        return differenceInMicros() / 1000;
    }

    /**
     * Calculates Difference.
     * @return Difference between start and end values in microseconds.
     */
    public long differenceInMicros() {
        return differenceInNanos() / 1000;
    }

    /**
     * Calculates Difference.
     * @return Difference between start and end values in nanoseconds.
     */
    public long differenceInNanos() {
        return end - start;
    }
}
