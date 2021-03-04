package com.penguin.java.base;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {
    public static void main(String[] args){
        int[] nums = new int[]{1,3,5,7};
        int target = 8;
        int[] res = q1a2(nums,target);
        if(res != null)
            System.out.println("我们的目标："+res[0]+","+res[1]);

    }
    /**
     * 问题1：给定一个数m，求大于该数的最小2的n次幂，返回n
     */
    /**
     * 问题2：给定一个整数数组nums 和一个目标值target，请你在该数组中找到 和为目标值的两个整数，并返回它们的数组下标
     * 可以假设每次输入只会对应一个答案（找到一次就不找了），但是同一个元素只能使用一次
     * 解决：
     * 1.暴力破解法
     * 2.hash查找法，可能存在相同元素的hash碰撞问题
     */
    //暴力破解法 时间复杂度高
    public static int[] q1a1(int[] nums,int target){
        //如果包含相同元素，即hash碰撞问题
        for (int i = 0; i < nums.length ;i++){
            for(int j=i+1;j < nums.length;j++){
                if(nums[i]+nums[j] == target){
                    return new int[]{i,j};
                }
            }
        }
        return null;
    }
    //hash查找
    public static int[] q1a2(int[] nums,int target){

        Map<Integer,Integer> map = new HashMap<>();
        for(int i = 0;i < nums.length;i++){
            int targetValue = target - nums[i];
            if(map.containsKey(targetValue)){
                return new int[]{i,map.get(targetValue)};
            }
            map.put(nums[i],i);
        }

        return null;
    }
    /**
     * 问题3：给定一个链表，删除相邻相同的数据项。如2 > 2 > 3,返回2 > 3
     */
    /**
     * 问题4：给定一个链表，其中元素只有1和0，删除0的节点，并返回链表（AQS源码）
     */
}
