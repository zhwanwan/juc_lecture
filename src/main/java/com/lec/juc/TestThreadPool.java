package com.lec.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *一、线程池：提供了一个线程对列，对列中保存着所有等替代状态的线程。避免了创建于销毁额外开销，提高了响应的速度。
 *二、线程池的体系结构：
 *  java.util.concurrent.Executor：负责线程的使用与调度的根接口
 *      |--**ExecutorService 子接口：线程池的主要接口
 *          |--ThreadPoolExecutor 线程池的实现类
 *          |--ScheduledExecutorService 子接口：负责线程的调度
 *              |--ScheduledThreadPoolExecutor: 继承ThreadPoolExecutor，实现ScheduledExecutorService
 *三、工具类
 * ExecutorService newFixedThreadPool() : 创建固定大小的线程池
 * ExecutorService newCachedThreadPool() : 缓存线程池，线程池的数量不固定，可以根据需求自动的更改数量。
 * ExecutorService newSingleThreadExecutor() : 创建单个线程池。线程池中只有一个线程
 * ScheduledExecutorService newScheduledThreadPool() : 创建固定大小的线程，可以延迟或定时的执行任务。
 *
 */
public class TestThreadPool {
    public static void main(String[] args) {


        //1.创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(5);

        List<Future<Integer>> list = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            Future<Integer> future = pool.submit(() -> {
                int sum = 0;
                for (int j = 0; j <= 100; j++) {
                    sum += j;
                }
                return sum;
            });
            list.add(future);
        }
        pool.shutdown();

        list.forEach(f -> {
            try {
                System.out.println(f.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });



        /*ThreadPoolDemo demo = new ThreadPoolDemo();

        //2.分配任务
        for (int i = 0; i < 5; i++) {
            pool.submit(demo);
        }

        //关闭线程池
        pool.shutdown();*/


//        new Thread(demo).start();
//        new Thread(demo).start();
    }
}

class ThreadPoolDemo implements Runnable {

    private int i = 0;

    @Override
    public void run() {
        while (i < 1000) {
            System.out.println(Thread.currentThread().getName() + " : " + i++);
        }
    }
}