/*
 * Copyright 2020 tuhu.cn All right reserved. This software is the
 * confidential and proprietary information of tuhu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tuhu.cn
 */
package cn.barrywangmeng.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * NIO client
 *
 * @author wangmeng
 * @date 2020/6/7 21:06
 */
public class NIOClient {

    public static void main(String[] args) {
        for(int i = 0; i < 10; i++){
            new Worker().start();
        }
    }

    static class Worker extends Thread {
        @Override
        public void run() {
            SocketChannel channel = null;
            Selector selector = null;
            try {
                // SocketChannel 底层封装了Socket
                channel = SocketChannel.open();
                channel.connect(new InetSocketAddress("localhost", 9000));

                selector = Selector.open();
                // 监听connect行为
                channel.register(selector, SelectionKey.OP_CONNECT);
                while (true) {
                    // 服务器程序一定会给客户端返回一个响应
                    selector.select();

                    Iterator<SelectionKey> keysIterator = selector.selectedKeys().iterator();
                    while (keysIterator.hasNext()) {
                        SelectionKey key = (SelectionKey) keysIterator.next();
                        keysIterator.remove();

                        // 如果说server返回的是一个connectable的消息
                        if (key.isConnectable()) {
                            channel = (SocketChannel) key.channel();

                            if (channel.isConnectionPending()) {
                                channel.finishConnect();

                                // READ事件，就是可以读数据的事件
                                // 一单简历了连接成功之后，此时就可以给server发送一个请求了
                                ByteBuffer buffer = ByteBuffer.allocate(1024);
                                buffer.put("你好".getBytes());
                                buffer.flip();
                                channel.write(buffer);
                            }

                            channel.register(selector, SelectionKey.OP_READ);
                        } else if (key.isReadable()) {
                            channel = (SocketChannel) key.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            int len = channel.read(buffer);

                            if (len > 0) {
                                System.out.println("[" + Thread.currentThread().getName()
                                        + "]收到响应：" + new String(buffer.array(), 0, len));
                                Thread.sleep(5000);
                                channel.register(selector, SelectionKey.OP_WRITE);
                            }
                        } else if(key.isWritable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            buffer.put("你好".getBytes());
                            buffer.flip();

                            channel = (SocketChannel) key.channel();
                            channel.write(buffer);
                            channel.register(selector, SelectionKey.OP_READ);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                if(channel != null){
                    try {
                        channel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if(selector != null){
                    try {
                        selector.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}