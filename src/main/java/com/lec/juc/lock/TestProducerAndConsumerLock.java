package com.lec.juc.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者和消费者:
 * 使用JDK1.5提供的Lock+Condition方式实现
 */
public class TestProducerAndConsumerLock {

    public static void main(String[] args) {

        Clerk clerk = new Clerk();

        Producer producer = new Producer(clerk);
        Consumer consumer = new Consumer(clerk);

        new Thread(producer, "生产者A").start();
        new Thread(consumer, "消费者B").start();
        new Thread(producer, "生产者C").start();
        new Thread(consumer, "消费者D").start();
    }

}

class Clerk {

    private int product = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();


    public void purchase() {

        lock.lock();

        try {
            while (product >= 1) { // 为了避免虚假唤醒，应该总是使用在循环中。
                System.out.println("产品已满！");
                try {
                    //this.wait();
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            System.out.println(Thread.currentThread().getName() + " : " + ++product);
            //this.notifyAll();
            condition.signalAll();
        } finally {
            lock.unlock();
        }


    }

    public void sell() {
        lock.lock();
        try {
            while (product <= 0) {
                System.out.println("缺货！");
                try {
                    //this.wait();
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " : " + --product);
            //this.notifyAll();
            condition.signalAll();
        } finally {
            lock.unlock();
        }

    }
}

class Producer implements Runnable {

    private Clerk clerk;

    public Producer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.purchase();
        }
    }
}

class Consumer implements Runnable {

    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sell();
        }
    }
}