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
 * FileChannel 随机写demo
 *
 * @author wangmeng
 * @date 2020/6/3 17:15
 */
public class FileChannelDemo1 {
    public static void main(String[] args) throws Exception{
        FileOutputStream out = new FileOutputStream("D:\\tmp\\hello.txt");
        FileChannel channel = out.getChannel();

        ByteBuffer buffer = ByteBuffer.wrap("hello world".getBytes());
        channel.write(buffer);

        // channel必然会从buffer的position=0的位置开始读起，一直读到limit，limit=字符串字节数组的长度
        System.out.println(buffer.position());
        System.out.println(channel.position());

        buffer.rewind();
        System.out.println(buffer.position());
        System.out.println(channel.position());

        channel.position(5);
        channel.write(buffer);

        channel.close();
        out.close();
    }
}