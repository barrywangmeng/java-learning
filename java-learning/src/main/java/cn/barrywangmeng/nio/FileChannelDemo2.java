/*
 * Copyright 2020 tuhu.cn All right reserved. This software is the
 * confidential and proprietary information of tuhu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tuhu.cn
 */
package cn.barrywangmeng.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 测试FileChannel并发读写
 *
 * @author wangmeng2
 * @date 2020/6/3 17:36
 */
public class FileChannelDemo2 {
    public static void main(String[] args) throws Exception{
        FileOutputStream out = new FileOutputStream("D:\\tmp\\hello2.txt");
        FileChannel channel = out.getChannel();

        for (int i = 0; i < 10; i++) {
            new Thread(){
                @Override
                public void run() {
                    try {
                        ByteBuffer buffer = ByteBuffer.wrap("hello world".getBytes());
                        channel.write(buffer);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }
}