package com.penguin.juc;

import java.util.concurrent.CountDownLatch;

/**
 * 线程八锁
 * 1.非静态方法的锁默认为this（对象可能有多个），静态方法的锁对应的是Class（该实例永远只有一个）
 * 2.某一时刻内，只能有一个线程持有锁，无论几个方法
 */
public class StaticSynchronizedDemo {
    public static void main(String[] args) throws InterruptedException {

        synchronizedTest();
        synchronizedStaticTest();
    }
    //静态同步方法测试
    private static void synchronizedStaticTest() throws InterruptedException {
        StaticSynchronizedResource number = new StaticSynchronizedResource();
        StaticSynchronizedResource number01 = new StaticSynchronizedResource();

        System.out.println("#不同对象静态Synchronized方法的输出测试#");
        //使用闭锁保证结论最后输出
        CountDownLatch countDownLatch03 = new CountDownLatch(2);
        new Thread(() -> {
            number.getStaticOne();//静态方法获取锁，范围是Class，其他对象的静态方法无法获取锁，因此输出one two
            countDownLatch03.countDown();
        },"thread31").start();
        new Thread(() -> {
            number01.getStaticTwo();
            countDownLatch03.countDown();
        },"thread32").start();
        countDownLatch03.await();
        System.out.println("#结果：synchronized one \t synchronized two");

        System.out.println("#不同对象静态/非静态Synchronized方法的输出测试#");
        //使用闭锁保证结论最后输出
        CountDownLatch countDownLatch02 = new CountDownLatch(2);
        new Thread(() -> {
            number.getStaticOne();//静态方法获取锁，范围是Class，其他对象的静态方法无法获取锁，但其他对象的非静态方法可以获取锁，因此输出two one
            countDownLatch02.countDown();
        },"thread21").start();
        new Thread(() -> {
            number01.getTwo();
            countDownLatch02.countDown();
        },"thread22").start();
        countDownLatch02.await();
        System.out.println("#结果：synchronized two \t synchronized one");
        System.out.println("#不同对象静态Synchronized方法的输出测试#");
        //使用闭锁保证结论最后输出
        CountDownLatch countDownLatch01 = new CountDownLatch(2);
        new Thread(() -> {
            number.getStaticOne();
            countDownLatch01.countDown();
        },"thread11").start();
        new Thread(() -> {
            number01.getStaticTwo();
            countDownLatch01.countDown();
        },"thread12").start();

        countDownLatch01.await();
        System.out.println("#结果：synchronized one\tsynchronized two");
        System.out.println("#同一对象静态Synchronized方法的输出测试#");
        //使用闭锁保证结论最后输出
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(() -> {
            number.getStaticOne();
            countDownLatch.countDown();
        },"thread1").start();
        new Thread(() -> {
            number.getStaticTwo();
            countDownLatch.countDown();
        },"thread2").start();
        countDownLatch.await();
        System.out.println("#结果：synchronized one\tsynchronized two\n");
        //使用闭锁保证结论最后输出



    }
    //普通同步方法测试
    private static void synchronizedTest() throws InterruptedException {
        StaticSynchronizedResource number = new StaticSynchronizedResource();
        StaticSynchronizedResource number01 = new StaticSynchronizedResource();
        System.out.println("#同一对象Synchronized方法的输出测试#");
        //使用闭锁保证结论最后输出
        CountDownLatch countDownLatch = new CountDownLatch(3);
        new Thread(() -> {
            number.getOne();
            countDownLatch.countDown();
        },"thread1").start();
        new Thread(() -> {
            number.getTwo();
            countDownLatch.countDown();
        },"thread2").start();
        new Thread(() -> {
            number.getThree();
            countDownLatch.countDown();
        },"thread3").start();
        countDownLatch.await();
        System.out.println("#结果：synchronized one\tno synchronized three\t synchronized two");

        System.out.println("#不同对象Synchronized方法的输出测试#");
        //使用闭锁保证结论最后输出
        CountDownLatch countDownLatch01 = new CountDownLatch(2);
        new Thread(() -> {
            number.getOne();
            countDownLatch.countDown();
        },"thread11").start();
        new Thread(() -> {
            number01.getTwo();
            countDownLatch.countDown();
        },"thread12").start();
        countDownLatch01.await();
        System.out.println("#结果：synchronized one\tno synchronized three\t synchronized two");
    }
}
class StaticSynchronizedResource {
    public synchronized void getOne() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\tsynchronized one");
    }
    public synchronized void getTwo() {
        System.out.println("\tsynchronized two");
    }
    public static synchronized void getStaticOne() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\tsynchronized one");
    }
    public static synchronized void getStaticTwo() {
        System.out.println("\tsynchronized two");
    }
    public synchronized void getThree() {
        System.out.println("\tsynchronized three");
    }

}



