package com.penguin.java.base;

import java.util.LinkedHashMap;
import java.util.Map;

//LRU算法的核心是Hash链表（LinkedHashMap）
public class LinkedHashMapLRUCacheDemo<K,V> extends LinkedHashMap<K,V> {

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
}
