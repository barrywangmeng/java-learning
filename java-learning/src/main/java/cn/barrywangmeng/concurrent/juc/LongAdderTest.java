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