package cn.barrywangmeng.thread.juc;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 测试CyclicBarrier
 * @Author: wangmeng
 * @Date: 2018/12/16-17:05
 */
public class UseCyclicBarrier {

    //模拟运动员的类。
    static class Runner implements Runnable {

        private String name;

        private CyclicBarrier cyclicBarrier;

        @Override
        public void run() {
            try {
                System.out.println("运动员：" + this.name + "进行准备工作！");
                TimeUnit.SECONDS.sleep((new Random().nextInt(5)));
                System.out.println("运动员：" + this.name + "准备完成！");
                this.cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("运动员" + this.name + "开始起跑！！！");
        }

        public Runner(String name, CyclicBarrier cyclicBarrier) {
            this.name = name;
            this.cyclicBarrier = cyclicBarrier;
        }
    }

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        ExecutorService executorPools = Executors.newFixedThreadPool(2);

        executorPools.submit(new Thread(new Runner("张三", cyclicBarrier)));
        executorPools.submit(new Thread(new Runner("李四", cyclicBarrier)));

        executorPools.shutdown();
    }
}
