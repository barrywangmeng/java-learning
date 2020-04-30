package cn.barrywangmeng.concurrent.juc.lock;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Description: 模拟ReentrantLock死锁的问题
 * @Author: wangmeng
 * @Date: 2019/1/26-22:36
 */
public class LockDeadDemo {

    final static ExecutorService executorService = Executors.newSingleThreadExecutor();
    public static void main(String[] args) throws Exception{
        Future<Long> f1 = executorService.submit(new Callable<Long>() {
            public Long call() throws Exception {
                System.out.println("start f1");
                Thread.sleep(1000);//延时
                Future<Long> f2 = executorService.submit(new Callable<Long>() {
                        public Long call() throws Exception {
                            System.out.println("start f2");
                            return -1L;
                        }
                    });
                System.out.println("result" + f2.get());
                System.out.println("end f1");
                return -1L;
            }
        });

        f1.get();
    }
}
