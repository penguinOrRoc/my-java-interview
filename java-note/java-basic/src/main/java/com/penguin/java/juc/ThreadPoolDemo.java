package com.penguin.java.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolDemo {
    public static void main(String[] args) {
//        ExecutorService executorService = Executors.newFixedThreadPool(5);// 1池 5线程  适用长期任务执行
//        ExecutorService executorService = Executors.newSingleThreadExecutor();//1池 1线程 任务有序执行
        ExecutorService executorService = Executors.newCachedThreadPool();//1池 n线程  执行很多短期异步任务

        //模拟10个用户去5个窗口办理业务
        try {
            for (int i = 1; i <= 100; i++) {
                executorService.execute(() -> {
                            System.out.println(Thread.currentThread().getName() + "\t办理业务~");
                        }
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}


