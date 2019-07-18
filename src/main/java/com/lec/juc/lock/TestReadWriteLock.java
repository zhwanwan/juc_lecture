package com.lec.juc.lock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读-写锁：
 */
public class TestReadWriteLock {

    public static void main(String[] args) {
        ReadWriteLockDemo rw = new ReadWriteLockDemo();
        new Thread(() -> {
            rw.write((int) (Math.random() * 101));
        }, "Write").start();

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                rw.read();
            }).start();
        }
    }

}

class ReadWriteLockDemo {
    private int num = 0;
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public void read() {
        try {
            lock.readLock().lock();
            System.out.println(Thread.currentThread().getName() + " : " + num);
        } finally {
            lock.readLock().unlock();
        }
    }

    public void write(int num) {
        try {
            lock.writeLock().lock();
            System.out.println(Thread.currentThread().getName() + " : " + num);
            this.num = num;
        } finally {
            lock.writeLock().unlock();
        }
    }
}