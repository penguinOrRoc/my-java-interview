package com.penguin.java.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadOrderDemo {
    public static void main(String[] args) {
        ThreadOrderResource threadOrderResource = new ThreadOrderResource();
        //1 Ａ 2　Ｂ　３Ｃ
        new  Thread(()->{
            for(int i=1;i<20;i++){
                threadOrderResource.loopA(i);
            }
        },"A").start();
        new  Thread(()->{
            for(int i=1;i<20;i++){
                threadOrderResource.loopB(i);
            }
        },"B").start();
        new  Thread(()->{
            for(int i=1;i<20;i++){
                threadOrderResource.loopC(i);
            }
        },"C").start();
    }
}

class ThreadOrderResource {
    private int num = 1;//当前正在执行的线程标记
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void loopA(int totalloop) {
        lock.lock();
        try {
            //1.判断
            if (num != 1) {
                c1.await();
            }
            //2.打印
            for(int i=1;i<5;i++){
                System.out.println(Thread.currentThread().getName()+"\t"+i+"\t"+totalloop);
            }
            //3.唤醒B
            num = 2;
            c2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    public void loopB(int totalloop) {
        lock.lock();
        try {
            //1.判断
            if (num != 2) {
                c2.await();
            }
            //2.打印
            for(int i=1;i<5;i++){
                System.out.println(Thread.currentThread().getName()+"\t"+i+"\t"+totalloop);
            }
            //3.唤醒C
            num = 3;
            c3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    public void loopC(int totalloop) {
        lock.lock();
        try {
            //1.判断
            if (num != 3) {
                c3.await();
            }
            //2.打印
            for(int i=1;i<5;i++){
                System.out.println(Thread.currentThread().getName()+"\t"+i+"\t"+totalloop);
            }
            //3.唤醒A
            num = 1;
            c1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

}


