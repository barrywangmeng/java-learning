/*
 * Copyright 2020 tuhu.cn All right reserved. This software is the
 * confidential and proprietary information of tuhu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tuhu.cn
 */
package cn.barrywangmeng.nio;

import java.nio.ByteBuffer;

/**
 * NIO Buffer demo
 *
 * @author wangmeng2
 * @date 2020/6/17:28
 */
public class BufferDemo {

    public static void main(String[] args) {
        byte[] data = new byte[]{55, 56, 57, 58, 59};
        ByteBuffer buffer = ByteBuffer.wrap(data);

        System.out.println(buffer.capacity());
        System.out.println(buffer.position());
        System.out.println(buffer.limit());

        System.out.println(buffer.get());
        System.out.println(buffer.position());
        buffer.mark();

        buffer.position(3);
        System.out.println(buffer.get());
        System.out.println(buffer.position());

        buffer.reset();
        System.out.println(buffer.position());
    }
}