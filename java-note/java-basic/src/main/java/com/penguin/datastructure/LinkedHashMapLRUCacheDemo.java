package com.penguin.datastructure;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

//LRU算法的核心是Hash链表（LinkedHashMap）
public class LinkedHashMapLRUCacheDemo<K,V> extends LinkedHashMap<K,V> {

    public static void main(String[] args){
        LinkedHashMapLRUCacheDemo lruCacheDemo = new LinkedHashMapLRUCacheDemo(2);
        lruCacheDemo.put(1,"a");
        lruCacheDemo.put(2,"b");
        lruCacheDemo.put(3,"c");
        lruCacheDemo.put(4,"d");
        System.out.println(lruCacheDemo.keySet());
        lruCacheDemo.put(5,"e");
        System.out.println(lruCacheDemo.keySet());
    }

    private  int capacity;//缓存池的大小
    public LinkedHashMapLRUCacheDemo(int capacity) {
        super(capacity,0.75F,true);
        //the ordering mode - <tt>true</tt> for
        //     *         access-order   访问, <tt>false</tt> for insertion-order
        this.capacity = capacity;
    }
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return super.removeEldestEntry(eldest);
    }

    //LRU算法的核心是Hash链表（LinkedHashMap）
    public static class PenguinLRUCacheDemo{
        //1.构造Node节点，作为数据载体
        class Node<K,V>{
            K key;
            V value;
            Node<K,V> prev;
            Node<K,V> next;

            public Node() {
                this.prev = this.next = null;
            }

            public Node(K key, V value) {
                this.key = key;
                this.value = value;
                this.prev = this.next = null;
            }
        }
        //2.构造双向链表，用于存放NODE
        class  DoubleLinkedList<K,V>{
            Node<K,V> head;
            Node<K,V> tail;
            public  DoubleLinkedList(){//构造方法
                head = new Node<>();
                tail = new Node<>();
                head.next = tail;
                tail.prev = head;
            }
            //添加到链表头部
            public void addHead(Node<K,V> node){
                // 头节点始终为空
                node.next = head.next;
                node.prev = head;
                head.next.prev = node;
                head.next = node;
            }
            //移除节点
            public void removeNode(Node<K,V> node){
                node.prev.next = node.next;
                node.next.prev = node.prev;
                node.prev = null;
                node.next = null;
            }
            public Node getLast(){
                return tail.prev;
            }
        }
        private int cacheSize;
        Map<Integer,Node<Integer,Integer>> map;
        DoubleLinkedList<Integer,Integer> doubleLinkedList;

        //1.初始化方法
        public  PenguinLRUCacheDemo(int cacheSize){
            this.cacheSize = cacheSize;
            map = new HashMap<>();
            doubleLinkedList = new DoubleLinkedList<>();
        }
        //2.get方法
        public int get(int key){
            if(!map.containsKey(key)){
                return  -1;
            }
            Node<Integer,Integer> node = map.get(key);
            //根据最近最少使用算法，将当前节点放到头部
            doubleLinkedList.removeNode(node);
            doubleLinkedList.addHead(node);
            return node.value;
        }
        //3.put方法
        public  void  put(int key,int value){
            if(map.containsKey(key)){
                Node<Integer,Integer> node = map.get(key);
                node.value = value;
                map.put(key,node);
                doubleLinkedList.removeNode(node);
                doubleLinkedList.addHead(node);
            }else{
                if(map.size() ==cacheSize){
                    Node<Integer,Integer> lastNode = doubleLinkedList.getLast();
                    map.remove(lastNode);
                    doubleLinkedList.removeNode(lastNode);
                }
                Node<Integer,Integer> newNode = new Node<>(key,value);
                map.put(key,newNode);
                doubleLinkedList.addHead(newNode);
            }
        }

    }
}
