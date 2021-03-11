package com.penguin.juc;

public class CasDemo {
    public static  void main(String[] args){
        final  CasSource cas = new CasSource();
        for(int i=0;i<10;i++){
            new Thread(()->{
                int expect = cas.get();
                boolean b = cas.comcareAndSet(expect, (int) (Math.random()*101));
            },"thread"+i).start();
        }
    }

}
class CasSource{
    private volatile int value;
    //获取内存值
    public synchronized int get(){
        return value;
    }
    //比较
    public synchronized int compareAndSwap(int expect,int newValue){
        int oldValue = value;
        if(oldValue == expect){
            this.value = newValue;
        }
        return  oldValue;
    }
    //设置
    public synchronized  boolean comcareAndSet(int expect,int newValue){
        return  expect == compareAndSwap(expect,newValue);
    }
}


