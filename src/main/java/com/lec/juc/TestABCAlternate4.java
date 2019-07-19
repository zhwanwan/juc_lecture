package com.lec.juc;

import java.util.concurrent.Semaphore;

/**
 * 编写一程序：
 * 开启3个线程，这三个线程的ID分别为A、B、C,每个线程将自己的ID在屏幕上打印10遍，要求输出的结果必须按顺序显示。
 * 如：ABCABCABC...依次递归
 * <p>
 * 方法四：使用 Semaphore:
 * Semaphore又称信号量，是操作系统中的一个概念，在Java并发编程中，信号量控制的是线程并发的数量。
 * Semaphore实现原理简单理解：
 * Semaphore是用来保护一个或者多个共享资源的访问，Semaphore信号量内部维护了一个计数器，其值为可以访问的共享资源的个数。
 * 一个线程要访问共享资源，先获得信号量，如果信号量的计数器值大于1，意味着有共享资源可以访问，则使其计数器值减去1，再访问共享资源。
 * <p>
 * Semaphore 内部主要通过AQS（AbstractQueuedSynchronizer）实现线程的管理。
 * Semaphore 有两个构造函数，第一个构造函数包含一个permits表示许可数，它最后传递给了AQS的state值。
 * 线程在运行时首先获得许可，如果成功获得，则许可数减1，线程运行；当线程运行结束就释放许可，许可数加1。
 * 如果许可数为0，则获取失败，线程位于AQS的等待对列中，它会被其他释放许可的线程唤醒。
 * 另外一个构造函数除了包括permits参数外，还有布尔类型的fair表示公平性。
 * 一般常用非公平的信号量，表示在获取许可时限尝试获取，而不必关心是否有需要获取许可的线程位于等待对列中，如果获取失败，才会入列；
 * 而公平的信号量在获取许可时首先要查看等待对列中是否已有线程，遵循FIFO原则获取许可。
 */
public class TestABCAlternate4 {
    // 以A开始的信号量,初始信号量数量为1
    private static Semaphore A = new Semaphore(1);
    // B、C信号量,A完成后开始,初始信号数量为0
    private static Semaphore B = new Semaphore(0);
    private static Semaphore C = new Semaphore(0);

    static class ThreadA extends Thread {
        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    A.acquire(); //A获取信号执行，A信号量减1
                    System.out.print("A");
                    B.release(); //B释放信号，B信号量加1（初始为0）
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class ThreadB extends Thread {
        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    B.acquire(); //B获取信号执行，B信号量减1
                    System.out.print("B");
                    C.release(); //C释放信号，C信号量加1（初始为0）
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class ThreadC extends Thread {
        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    C.acquire(); //C获取信号执行，C信号量减1
                    System.out.print("C");
                    A.release(); //A释放信号，A信号量加1
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        /**
         * 初始(A=1,B=0,C=0)—>
         * 第一次执行线程A时(A=1,B=0,C=0)—->
         * 第一次执行线程B时（A=0,B=1,C=0）—->
         * 第一次执行线程C时(A=0,B=0,C=1)—>
         * 第二次执行线程A(A=1,B=0,C=0)如此循环。
         */
        new ThreadA().start();
        new ThreadB().start();
        new ThreadC().start();
    }


}

