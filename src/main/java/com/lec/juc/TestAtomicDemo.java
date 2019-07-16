package com.lec.juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * i++的原子性问题：i++实际上分为三个步骤"读-改-写"
 * int i = 10;
 * i = i++; //10
 * <p>
 * int temp = i;
 * i = i + 1;
 * i = temp;
 * <p>
 * volatile修改只能解决内存可见性问题，但是不能解决原子性问题
 * <p>
 * ---------------------------------------------------
 * 原子变量：JDK1.5提供了java.util.concurrent.atomic包常用的原子变量操作类：
 * AtomicBoolean
 * AtomicInteger
 * AtomicIntegerArray
 * AtomicIntegerFieldUpdater
 * AtomicLong
 * AtomicLongArray
 * AtomicLongFieldUpdater
 * AtomicMarkableReference
 * AtomicReference
 * AtomicReferenceArray
 * AtomicReferenceFieldUpdater
 * AtomicStampedReference
 * DoubleAccumulator
 * DoubleAdder
 * LongAccumulator
 * LongAdder
 * <p>
 * 1.volatile保证内存可见性
 * 2.CAS(Compare-And-Swap)算法保证数据的原子性
 * CAS算法是硬件对于并发操作共享数据的支持
 * CAS包含了三个操作数：
 * 1)内存值V
 * 2)预估值A
 * 3)更新值B
 * 当且仅当V==A时，V=B;否则，将不做任何操作。
 */
public class TestAtomicDemo {

    public static void main(String[] args) {

        AtomicDemo ad = new AtomicDemo();

        for (int i = 0; i < 10; i++) {
            new Thread(ad).start();
        }
    }
}

class AtomicDemo implements Runnable {

    private AtomicInteger serialNumber = new AtomicInteger();

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ":" + getSerialNumber());
    }

    public int getSerialNumber() {
        return serialNumber.getAndIncrement();
    }

}
