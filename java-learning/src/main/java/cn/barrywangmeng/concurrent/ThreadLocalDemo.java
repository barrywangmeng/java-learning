package cn.barrywangmeng.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description:
 * @Author: wangmeng
 * @Date: 2018/12/16-10:54
 */
public class ThreadLocalDemo {

    private static AtomicInteger nextHashCode =
            new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        nextHashCode.getAndAdd(10);
        System.out.println(nextHashCode.get());
        nextHashCode.getAndAdd(10);
        System.out.println(nextHashCode.get());
        ThreadLocal<Object> threadLocal = new InheritableThreadLocal<>();
        threadLocal.set(1L);

        new Thread() {
            @Override
            public void run() {
                System.out.println("main父线程：" + threadLocal.get());
                threadLocal.set(2L);
                System.out.println("线程1：" + threadLocal.get());
            }
        }.start();
//        new Thread() {
//            @Override
//            public void run() {
//                threadLocal.set(2L);
//                System.out.println("线程2：" + threadLocal.get());
//            }
//        }.start();

        Thread.sleep(1000);
        System.out.println("main线程 threadLocal2：" + threadLocal.get());
        ThreadLocal<Long> threadLocal2 = new ThreadLocal<>();
        threadLocal2.set(10L);
        System.out.println("main线程 threadLocal2：" + threadLocal2.get());
    }
}
