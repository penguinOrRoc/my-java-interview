package com.penguin.java.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class AQS {
    public static void main(String[] args){
        //默认非公平锁
        ReentrantLock reentrantLock = new ReentrantLock();
        //线程的管理和唤醒机制
        new Thread(()->{
            reentrantLock.lock();
            System.out.println("1号顾客开始办理业务~");
            try {
                TimeUnit.SECONDS.sleep(1000);
                System.out.println("1号顾客办完业务了~");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally{
                reentrantLock.unlock();
            }
        },"First").start();

        new Thread(()->{
            reentrantLock.lock();
            System.out.println("2号顾客开始办理业务~");
            try {
                TimeUnit.SECONDS.sleep(1000);
                System.out.println("2号顾客办完业务了~");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally{
                reentrantLock.unlock();
            }
        },"Second").start();
        new Thread(()->{
            reentrantLock.lock();
            System.out.println("3号顾客开始办理业务~");
            try {
                TimeUnit.SECONDS.sleep(1000);
                System.out.println("3号顾客办完业务了~");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally{
                reentrantLock.unlock();
            }
        },"Third").start();

    }
}
