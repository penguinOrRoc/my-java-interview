package com.penguin.datastructure.list;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListDemo {
    static List<String> arrayList = new ArrayList<String>(); //Object 数组
    static List<String> linkedList = new LinkedList<>();//Node 链表
    static List<String> vector = new Vector<>();//Object 数组
    static List<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();  //数组

    public static void main(String[] args) {
        arrayList.add("a1");
        linkedList.add("l1");
        vector.add("v1");   //synchronized   修饰的
        copyOnWriteArrayList.add("c1");
        copyOnWriteArrayList.get(1); //ReentrantLock
    }


}


