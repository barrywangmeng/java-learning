package cn.barrywangmeng.thread.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Description:使用Semaphore模拟限流操作
 * @Author: wangmeng
 * @Date: 2018/12/16-18:30
 */
public class UseSemaphore {

    public static void main(String[] args) {
        ExecutorService threadPools = Executors.newFixedThreadPool(20);
        //同一时间只能有5个线程执行
        Semaphore semaphore = new Semaphore(5);


        for (int i = 0; i < 20; i++) {
            final int token = i;
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();

                        //进行业务操作
                        System.out.println("获得许可，执行操作..." + token);
                        long sleepTime = (long)(Math.random() * 10000);
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        semaphore.release();
                    }
                }
            };

            threadPools.execute(run);
        }

        System.out.println("queue length: " + semaphore.getQueueLength());
        threadPools.shutdown();
    }
}
