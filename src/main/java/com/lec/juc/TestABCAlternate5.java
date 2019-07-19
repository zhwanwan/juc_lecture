package com.lec.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 编写一程序：
 * 开启3个线程，这三个线程的ID分别为A、B、C,每个线程将自己的ID在屏幕上打印10遍，要求输出的结果必须按顺序显示。
 * 如：ABCABCABC...依次递归
 * <p>
 * 方法五：使用 AtomicInteger
 */
public class TestABCAlternate5 {

    private AtomicInteger ai = new AtomicInteger(0);
    private static final int MAX_SYC_VALUE = 3 * 10;

    private class ThreadA implements Runnable {
        @Override
        public void run() {
            while (ai.get() < MAX_SYC_VALUE) {
                if (ai.get() % 3 == 0) {
                    System.out.print("A");
                    ai.getAndIncrement();
                }
            }
        }
    }

    private class ThreadB implements Runnable {
        @Override
        public void run() {
            while (ai.get() < MAX_SYC_VALUE) {
                if (ai.get() % 3 == 1) {
                    System.out.print("B");
                    ai.getAndIncrement();
                }
            }
        }
    }

    private class ThreadC implements Runnable {
        @Override
        public void run() {
            while (ai.get() < MAX_SYC_VALUE) {
                if (ai.get() % 3 == 2) {
                    System.out.print("C");
                    ai.getAndIncrement();
                }
            }
        }
    }

    public static void main(String[] args) {

        TestABCAlternate5 alternate5 = new TestABCAlternate5();
        ExecutorService service = Executors.newFixedThreadPool(3);

        service.execute(alternate5.new ThreadA());
        service.execute(alternate5.new ThreadB());
        service.execute(alternate5.new ThreadC());

        service.shutdown();
    }


}

