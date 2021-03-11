package com.penguin.datastructure.map;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapDemo {
    static Map<String,Object> hashMap = new HashMap<>();
    static Map<String,Object> linkedHashMap = new LinkedHashMap<>();
    static Map<String,Object> concurrentHashMap = new ConcurrentHashMap<>();

    public static void main(String[] args){
        hashMap.put("hashMap",new Object()); //Node<K,V>[]
        linkedHashMap.put("linkedHashMap",new Object());
        concurrentHashMap.put("concurrentHashMap",new Object());





    }
}


