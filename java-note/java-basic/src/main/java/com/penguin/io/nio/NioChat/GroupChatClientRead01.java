package com.penguin.io.nio.NioChat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GroupChatClientRead01 {
    public static void main(String[] args) throws IOException {
        //启动客户端
        GroupChatClient client = new GroupChatClient();
        //监听客户端的数据
        ExecutorService threadPool = Executors.newCachedThreadPool();
        threadPool.execute(()->{
            while(true){
                try {
                    //客户端读取从服务器端发送的数据
                    client.readInfo();
                    Thread.currentThread().sleep(3000);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });



    }


}


