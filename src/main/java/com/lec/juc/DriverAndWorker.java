package com.lec.juc;

import java.util.concurrent.CountDownLatch;

/**
 *
 */
public class DriverAndWorker {

    private static final int WORKERS = 5;

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(WORKERS);

        for (int i = 0; i < WORKERS; i++)
            new Thread(new Worker(startSignal, doneSignal)).start();

        System.out.println("workers begin to work");
        startSignal.countDown();
        doneSignal.await();
        System.out.println("workers finish working");

    }

}

class Worker implements Runnable {

    private final CountDownLatch startSignal;
    private final CountDownLatch doneSignal;

    Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
        this.startSignal = startSignal;
        this.doneSignal = doneSignal;
    }

    @Override
    public void run() {
        try {
            startSignal.await();
            doWork();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            doneSignal.countDown();
        }
    }

    void doWork() {
        System.out.println(Thread.currentThread().getName() + " is working.");
    }
}