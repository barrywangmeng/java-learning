package cn.barrywangmeng.concurrent.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Description: CountDownLatch 等待和唤醒
 * @Author: wangmeng
 * @Date: 2018/12/16-16:38
 */
public class UseCountDownLatch {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("进入t1线程。。。");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("t1线程初始化完毕，通知t3线程继续操作！");
                countDownLatch.countDown();
            }
        }, "t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("进入t2线程。。。");
                try {
                    TimeUnit.SECONDS.sleep(4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("t2线程初始化完毕，通知t3线程继续操作！");
                countDownLatch.countDown();
            }
        }, "t2");

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("进入t3 线程，并且等待...");
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("t3线程进行后续的执行操作...");
            }
        }, "t3");

        t1.start();
        t2.start();
        t3.start();
    }
}
