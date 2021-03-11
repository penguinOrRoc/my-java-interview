package com.penguin.juc;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AQSDemo {

    public static void main(String[] args){
        new HashMap<>();
        //默认非公平锁
        Lock reentrantLock = new ReentrantLock();
        //线程的管理和唤醒机制
        new Thread(()->{
            reentrantLock.lock();
            System.out.println("1号顾客开始办理业务~");
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally{
                reentrantLock.unlock();
                System.out.println("1号顾客办完业务了~");
            }
        },"First").start();

        new Thread(()->{
            reentrantLock.lock();
            System.out.println("2号顾客开始办理业务~");
            try {
                TimeUnit.SECONDS.sleep(10);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally{
                reentrantLock.unlock();
                System.out.println("2号顾客办完业务了~");
            }
        },"Second").start();
        new Thread(()->{
            reentrantLock.lock();
            System.out.println("3号顾客开始办理业务~");
            try {
                TimeUnit.SECONDS.sleep(10);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally{
                reentrantLock.unlock();
                System.out.println("3号顾客办完业务了~");

            }
        },"Third").start();

    }
}
class AQSDemoPlus{

    public static void main(String[] args) {
        //默认非公平锁
        Lock lock = new ReentrantLock();
        ExecutorService executorService = Executors.newCachedThreadPool();//1池 n线程  执行很多短期异步任务
        for (int i = 1; i <= 5; i++) {
            executorService.execute(() -> {
                lock.lock();
                System.out.println(Thread.currentThread().getName()+"号------Start~");
                try {
                    System.out.println(Thread.currentThread().getName()+"正在办理业务……");
                    TimeUnit.SECONDS.sleep(2);//模拟办理业务的时间
                   // Thread.sleep(3000);线程休眠
                    System.out.println(Thread.currentThread().getName()+"号------End~");
                    System.out.println();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            });
        }
        executorService.shutdown();

    }
}


