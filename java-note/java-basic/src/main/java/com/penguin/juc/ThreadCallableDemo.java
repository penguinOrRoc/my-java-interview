package com.penguin.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ThreadCallableDemo{
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(new CallableDemo());
        FutureTask<Integer> futureTask01 = new FutureTask<>(new CallableDemo());

        new Thread(futureTask,"callableThread01").start();
        new Thread(futureTask01,"callableThread02").start();
        while (!futureTask.isDone()){//未计算完毕，等待

        }
        Integer res = futureTask.get();
        System.out.println(res);//如果get()没有获取到结果会阻塞，直至获取



        /**
         new ThreadDemo().run();
         ThreadDemo threadDemo = new ThreadDemo();
         threadDemo.start();
         */
        /**

         new RunableDemo().run();
         RunableDemo runableDemo = new RunableDemo();
         runableDemo.run();
         */
    }
}
class CallableDemo implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        //TimeUnit.SECONDS.sleep(10);
        System.out.println("进入CallAble");
        return 1024;
    }
}
class ThreadDemo extends Thread{
    @Override
    public void run() {
        super.run();
        System.out.println("Thread类的run方法");
    }
}
class RunableDemo implements Runnable{
    @Override
    public void run() {
        System.out.println("Runnable接口的Run方法");
    }
}


