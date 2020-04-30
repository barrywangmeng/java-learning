/*
 * Copyright 2020 tuhu.cn All right reserved. This software is the
 * confidential and proprietary information of tuhu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tuhu.cn
 */
package cn.barrywangmeng.concurrent.juc;

import java.util.concurrent.atomic.LongAdder;

/**
 * LongAdder测试类，源码学习
 *
 * @author wangmeng
 * @date 2020/4/27 14:33
 */
public class LongAdderTest {
    private static LongAdder longAdder = new LongAdder();

    public static void main(String[] args) {
        longAdder.increment();

        long l = longAdder.longValue();
    }
}