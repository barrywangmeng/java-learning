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
 * @author wangmeng2
 * @date 2020/6/18:06
 */
public class BufferDemo2 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(100);

        System.out.println("position = " + buffer.position());
        System.out.println("capacity = " + buffer.capacity());
        System.out.println("limit = " + buffer.limit());

        byte[] src = new byte[]{1, 2, 3, 4, 5};
        buffer.put(src);
        System.out.println("position = " + buffer.position());

        buffer.position(0);
        byte[] dst = new byte[5];
        buffer.get(dst);
        System.out.println("position = " + buffer.position());

        System.out.print("dst=[");
        for(int i = 0; i < dst.length; i++) {
            System.out.print(i);
            if(i < dst.length - 1) {
                System.out.print(",");
            }
        }
        System.out.print("]");

    }
}