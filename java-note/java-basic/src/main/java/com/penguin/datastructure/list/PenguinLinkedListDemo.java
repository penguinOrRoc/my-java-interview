package com.penguin.datastructure.list;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PenguinLinkedListDemo {
    public static void main(String[] args) {
        PenguinLinkedList<Object> p = new PenguinLinkedList<Object>();
        p.add("111");
        p.add("222");
        p.add("333");
        p.add("444");
        p.add("555");
        System.out.println("添加5次~");
        p.getAll();

        System.out.println("删除尾节点~");
        System.out.println("删除后的结果~");
        p.remove();
        p.getAll();
        System.out.println("反转链表~");
        System.out.println("反转后的结果~");
        p.rever();
        p.getAll();





    }

    public static class PenguinLinkedList<P> {
        int size = 0;
        Node<P> first;
        Node<P> last;

        //初始化构造方法
        public PenguinLinkedList() {
        }
        public  boolean rever(){
            //遍历每一个节点，把该节点的头尾指针逆转
            Node node = first;
            Node temp = null;
            while (node!=null){
                //交换当前节点的prev与next
                temp = node.prev;
                node.prev = node.next;
                node.next = temp;
                node = node.prev;
            }
            //修改链表的头尾指针
            temp = first;
            first = last;
            last = temp;

            return true;
        }

        public boolean add(P p) {//添加新节点到链表尾部
            Node<P> l = last;
            //指定新节点的头结点
            Node<P> newNode = new Node<>(l, p, null);
            //尾节点指向新节点
            last = newNode;
            if (l == null)  //如果尾节点为空，证明这是第一个元素，将头结点也指向新节点
                first = newNode;
            else            //如果尾节点非空，证明已经有元素，将新节点添加到尾节点之后
                l.next = newNode;
            size++;
            return true;
        }
        public boolean remove() {//从队尾删除元素
            Node<P> l = last;
            if(l == null){
                return false;
            }else{
                //修改尾指针的指向
                last  = l.prev;
                //尾指针的next置空
                l.prev.next = null;
                //断开原尾节点的连接
                l.prev = null;
                return true;
            }

        }
        public void getAll(){
            PenguinLinkedList.Node node = first;
            while (node != null) {
                System.out.println(node.item);
                node = node.next;
            }
            System.out.println("----------------");
        }


        private static class Node<P> {
            P item;
            Node<P> next;
            Node<P> prev;

            Node(Node<P> prev, P element, Node<P> next) {
                this.item = element;
                this.next = next;
                this.prev = prev;
            }
            Node() {

            }
        }

    }


}


