package com.miraclem4n.mchat.util;

import java.util.Date;

public class TimerUtil {
    long start = 0;
    long end = 0;

    public TimerUtil() {
        start = new Date().getTime();
    }

    public void reset() {
        start = 0;
        end = 0;
    }

    public void start() {
        start = new Date().getTime();
    }

    public void stop() {
        end = new Date().getTime();
    }

    public long difference() {
        return end - start;
    }
}
