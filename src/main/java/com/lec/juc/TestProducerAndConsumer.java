package com.lec.juc;

/**
 * 生产者和消费者:
 * 使用synchronized + wait + notify方式实现生产消费模式
 */
public class TestProducerAndConsumer {

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

    public synchronized void purchase() {
        while (product >= 1) { // 为了避免虚假唤醒，应该总是使用在循环中。
            System.out.println("产品已满！");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.println(Thread.currentThread().getName() + " : " + ++product);
        this.notifyAll();
    }

    public synchronized void sell() {
        while (product <= 0) {
            System.out.println("缺货！");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " : " + --product);
        this.notifyAll();
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