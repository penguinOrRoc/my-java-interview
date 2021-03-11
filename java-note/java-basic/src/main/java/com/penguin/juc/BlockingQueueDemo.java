package com.penguin.juc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BlockingQueueDemo {
    public  static void main(String[] args) throws InterruptedException {
        BlockingQueueResource blockingQueueResource = new BlockingQueueResource(new ArrayBlockingQueue<String>(3));
        new Thread(()->{
            try {
                blockingQueueResource.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"生产者").start();
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);//生产一定时间再消费
                blockingQueueResource.consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"消费者").start();

        //暂停主线程，模拟一定时间的生产
        TimeUnit.SECONDS.sleep(10);
        System.out.println("时间到，老板叫停生产~");
        System.out.println("库存还有~"+blockingQueueResource.blockingQueue.size());
        blockingQueueResource.stop();
    }
}
class BlockingQueueResource{
    private  volatile  boolean flag = true;//默认开启，进行生产+消息
    private AtomicInteger atomicInteger = new AtomicInteger();
    BlockingQueue<String> blockingQueue = null;

    public BlockingQueueResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }
    public void produce() throws InterruptedException {
        String data = null;
        boolean returnValue ;
        while (flag){//不叫停，一直生产
            data = atomicInteger.incrementAndGet()+"";
            returnValue = blockingQueue.offer(data,2,TimeUnit.SECONDS);
            if(returnValue){
                System.out.println(Thread.currentThread().getName()+"\t插入"+data+"成功~");
            }else{
                System.out.println(Thread.currentThread().getName()+"\t插入"+data+"失败~");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName()+"：FLAF = false ,老板叫停生产~");
    }
    public void consumer() throws InterruptedException {
        String result  = null;
        while (flag){
            result = blockingQueue.poll(2, TimeUnit.SECONDS);//2秒未获取到就返回
            if(null == result || result.equalsIgnoreCase("")){
                flag = false;
                System.out.println(Thread.currentThread().getName()+"\t超过2秒钟没有库存，不再消费~");
            }else{
            System.out.println(Thread.currentThread().getName()+"\t消费"+result+"成功~");}

        }
    }
    public void stop(){
        this.flag = false;
    }
}


