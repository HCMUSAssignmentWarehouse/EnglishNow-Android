package com.iceteaviet.englishnow.utils;

/**
 * Created by Genius Doan on 05/01/2018.
 */

/**
 * This is a synchronization primitive related to {@link java.util.concurrent.CountDownLatch}
 * except that it starts at zero and can count upward.
 * <p>
 * The intended use case is for waiting for all tasks to be finished when the number of tasks isn't
 * known ahead of time, or may change while waiting.
 */
public class CountUpDownLatch {
    private static CountUpDownLatch instance;
    private int count;

    private CountUpDownLatch() {
        count = 0;
    }

    public static CountUpDownLatch getInstance() {
        if (instance == null)
            instance = new CountUpDownLatch();

        return instance;
    }

    public synchronized void countDown() {
        count--;
        notifyAll();
    }

    public synchronized void countUp() {
        count++;
        notifyAll();
    }

    public synchronized void await() throws InterruptedException {
        while (count != 0) {
            wait();
        }
    }

    public void reset() {
        count = 0;
    }
}
