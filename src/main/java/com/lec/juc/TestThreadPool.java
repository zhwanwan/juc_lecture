package com.lec.juc;

/**
 * 线程池
 */
public class TestThreadPool {
    public static void main(String[] args) {

    }
}

class ThreadPoolDemo implements Runnable {
    private int i = 0;


    @Override
    public void run() {
        while (i <= 100) {
            System.out.println(Thread.currentThread().getName() + " : " + i++);
        }
    }
}