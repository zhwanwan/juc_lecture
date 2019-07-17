package com.lec.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 创建线程的方式三：实现Callable接口
 */
public class TestCallable {

    public static void main(String[] args) {

        CallThread ct = new CallThread();

        //1.执行Callable需要通过FutureTask接收运算结果
        FutureTask<Integer> futureTask = new FutureTask<>(ct);

        new Thread(futureTask).start();

        try {
            //2.接收线程运算后的结果
            Integer sum = futureTask.get();
            System.out.println("Sum: " + sum);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

}

class CallThread implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {

        int sum = 0;
        for (int i = 0; i <= 100; i++) {
            sum += i;
        }

        return sum;
    }
}