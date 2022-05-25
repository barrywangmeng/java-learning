package cn.barrywangmeng.designpattern.masterworker;

import java.util.Random;

/**
 * @Description:
 * @Author: wangmeng
 * @Date: 2018/12/17-22:40
 */
public class Main {

    public static void main(String[] args) {
        Master master = new Master(new Worker(), Runtime.getRuntime().availableProcessors());

        Random r = new Random();
        for (int i = 0; i < 100; i++) {
            Task t = new Task(i, r.nextInt(1000));
            master.submit(t);
        }

        master.execute();
        long start = System.currentTimeMillis();

        //CountDownLatch
        while (true) {
            if (master.isComplete()) {
                long end = System.currentTimeMillis();
                int result = master.getResult();
                System.out.println("最终执行结果：" + (end - start) + ", 总耗时："+ result);
                break;
            }
        }
    }
}
