package com.lec.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 编写一程序：
 * 开启3个线程，这三个线程的ID分别为A、B、C,每个线程将自己的ID在屏幕上打印10遍，要求输出的结果必须按顺序显示。
 * 如：ABCABCABC...依次递归
 * <p>
 * 方法三：使用Lock->ReentrantLock 和Condition（await 、signal、signalAll）
 */
public class TestABCAlternate3 {

    private static Lock lock = new ReentrantLock();
    private static Condition A = lock.newCondition();
    private static Condition B = lock.newCondition();
    private static Condition C = lock.newCondition();

    private static int count = 0;

    static class ThreadA extends Thread {
        @Override
        public void run() {
            try {
                lock.lock();
                for (int i = 0; i < 10; i++) {
                    while (count % 3 != 0) { //注意这里是不等于0，也就是说没轮到该线程执行，之前一直等待状态
                        A.await(); //该线程A将会释放lock锁，构造成节点加入等待队列并进入等待状态
                    }
                    System.out.print("A");
                    count++;
                    B.signal(); //A执行完唤醒B线程
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    static class ThreadB extends Thread {
        @Override
        public void run() {
            try {
                lock.lock();
                for (int i = 0; i < 10; i++) {
                    while (count % 3 != 1) {
                        B.await(); // B释放lock锁，当前面A线程执行后会通过B.signal()唤醒该线程
                    }
                    System.out.print("B");
                    count++;
                    C.signal(); // B执行完唤醒C线程
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    static class ThreadC extends Thread {

        @Override
        public void run() {
            try {
                lock.lock();
                for (int i = 0; i < 10; i++) {
                    while (count % 3 != 2) {
                        C.await(); // C释放lock锁
                    }
                    System.out.print("C");
                    count++;
                    A.signal(); // C执行完唤醒A线程
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        new TestABCAlternate3.ThreadA().start();
        new TestABCAlternate3.ThreadB().start();
        new TestABCAlternate3.ThreadC().start();
    }


}

