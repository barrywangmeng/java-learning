package cn.barrywangmeng.concurrent.juc;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: Exchanger只能用于两个线程之间的交换。
 * @Author: wangmeng
 * @Date: 2018/12/17-21:35
 */
public class UseExchanger {
    private static final Exchanger<String> exchanger = new Exchanger<>();

    private static ExecutorService executorService = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String a = "银行流水A";
                try {
                    String b = exchanger.exchange(a);//交换自己的数据并且获取别人的数据
                    System.out.println("线程A，获取线程B交换的数据：" + b);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String b = "银行流水B";
                try {
                    String a = exchanger.exchange(b);//交换自己的数据并且获取别人的数据
                    System.out.println("线程B，获取线程A交换的数据：" + a);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executorService.shutdown();
    }
}
