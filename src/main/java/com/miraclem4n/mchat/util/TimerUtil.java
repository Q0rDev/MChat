package com.miraclem4n.mchat.util;

import java.util.Date;

/**
 * Class used when a Timer is needed.
 */
public class TimerUtil {
    long start = 0;
    long end = 0;

    /**
     * Class Initializer, also starts timer.
     */
    public TimerUtil() {
        start = new Date().getTime();
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
        start = new Date().getTime();
    }

    /**
     * Stops timer.
     */
    public void stop() {
        end = new Date().getTime();
    }

    /**
     * Calculates Difference.
     * @return Difference between start and end values.
     */
    public long difference() {
        return end - start;
    }
}
