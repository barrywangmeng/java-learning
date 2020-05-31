/*
 * Copyright 2020 tuhu.cn All right reserved. This software is the
 * confidential and proprietary information of tuhu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tuhu.cn
 */
package cn.barrywangmeng.concurrent.juc;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * Atomic和LongAdder耗时测试
 *
 * @author：一枝花算不算浪漫
 * @date：2020-05-12 7:06
 */
public class AtomicLongAdderTest {
    public static void main(String[] args) throws Exception{
        testAtomicLongAdder(1, 10000000);
        testAtomicLongAdder(10, 10000000);
        testAtomicLongAdder(100, 10000000);
    }

    static void testAtomicLongAdder(int threadCount, int times) throws Exception{
        System.out.println("threadCount: " + threadCount + ", times: " + times);
        long start = System.currentTimeMillis();
        testLongAdder(threadCount, times);
        System.out.println("LongAdder 耗时：" + (System.currentTimeMillis() - start) + "ms");
        System.out.println("threadCount: " + threadCount + ", times: " + times);
        long atomicStart = System.currentTimeMillis();
        testAtomicLong(threadCount, times);
        System.out.println("AtomicLong 耗时：" + (System.currentTimeMillis() - atomicStart) + "ms");
        System.out.println("----------------------------------------");
    }

    static void testAtomicLong(int threadCount, int times) throws Exception{
        AtomicLong atomicLong = new AtomicLong();
        List<Thread> list = Lists.newArrayList();
        for (int i = 0; i < threadCount; i++) {
            list.add(new Thread(() -> {
                for (int j = 0; j < times; j++) {
                    atomicLong.incrementAndGet();
                }
            }));
        }

        for (Thread thread : list) {
            thread.start();
        }

        for (Thread thread : list) {
            thread.join();
        }

        System.out.println("AtomicLong value is : " + atomicLong.get());
    }

    static void testLongAdder(int threadCount, int times) throws Exception{
        LongAdder longAdder = new LongAdder();
        List<Thread> list = Lists.newArrayList();
        for (int i = 0; i < threadCount; i++) {
            list.add(new Thread(() -> {
                for (int j = 0; j < times; j++) {
                    longAdder.increment();
                }
            }));
        }

        for (Thread thread : list) {
            thread.start();
        }

        for (Thread thread : list) {
            thread.join();
        }

        System.out.println("LongAdder value is : " + longAdder.longValue());
    }
}