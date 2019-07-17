package com.lec.juc;

/**
 * 编写一程序：
 * 开启3个线程，这三个线程的ID分别为A、B、C,每个线程将自己的ID在屏幕上打印10遍，要求输出的结果必须按顺序显示。
 * 如：ABCABCABC...依次递归
 *
 * 方法一：使用synchronized, wait和notifyAll
 */
public class TestABCAlternate1 {

    public static class PrintThread implements Runnable {

        private String name;
        private Object prev;
        private Object self;

        public PrintThread(String name, Object prev, Object self) {
            this.name = name;
            this.prev = prev;
            this.self = self;
        }

        @Override
        public void run() {
            int count = 10;
            while (count > 0) { //多线程并发，不能用if，必须使用while循环
                synchronized (prev) {  //先获取 prev 锁
                    synchronized (self) { //再获取 self 锁
                        System.out.print(name);
                        count--;
                        self.notifyAll(); //唤醒其他线程竞争self锁，注意此时self锁并未立即释放。
                    }
                    //此时执行完self的同步块，这时self锁才释放
                    try {
                        if (count == 0) { //最后一次打印
                            prev.notifyAll();
                        } else {
                            prev.wait(); //释放prev锁，当前线程休眠，等待唤醒
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Object a = new Object();
        Object b = new Object();
        Object c = new Object();

        PrintThread pa = new PrintThread("A", c, a);
        PrintThread pb = new PrintThread("B", a, b);
        PrintThread pc = new PrintThread("C", b, c);

        new Thread(pa).start();
        Thread.sleep(10);
        new Thread(pb).start();
        Thread.sleep(10);
        new Thread(pc).start();
        Thread.sleep(10);
    }


}

