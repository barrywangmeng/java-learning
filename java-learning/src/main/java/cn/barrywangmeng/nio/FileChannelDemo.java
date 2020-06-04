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
 * FileDemo 测试
 *
 * @author wangmeng
 * @date 2020/6/3 10:35
 */
public class FileChannelDemo {

    public static void main(String[] args) throws Exception{
        FileOutputStream out = new FileOutputStream("D:\\tmp\\hello.txt");
        FileChannel channel = out.getChannel();

        ByteBuffer buffer = ByteBuffer.wrap("hello world".getBytes());
        channel.write(buffer);

        channel.close();
        out.close();
    }
}
