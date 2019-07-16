package com.lec.juc;

/**
 * volatile关键字
 * <p>
 * 内存可见性问题是指：
 * 当多个线程操作共享数据时，彼此不可见。
 * 解决方法：
 * 1.synchronized加锁，但是效率很低
 * 2.对共享数据使用volatile修饰，当多个线程操作共享数据时，可以保证数据可见性。
 * 【每次需要从主存读取数据，性能有所降低】相对于synchronized，它是一种较为轻量级的同步策略。
 *
 * 注意：
 * 1.volatile不具备"互斥性"
 * 2.volatile不能保证变量的"原子性"
 *
 */
public class TestVolatile {

    public static void main(String[] args) {
        ThreadDemo td = new ThreadDemo();
        new Thread(td).start();
        while (true) {

            /*synchronized (td) {
            }*/
            if (td.isFlag()) {
                System.out.println("--------------");
                break;
            }
        }
    }

}

class ThreadDemo implements Runnable {

    private boolean flag = false;

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
        System.out.println("flag = " + flag);

    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}