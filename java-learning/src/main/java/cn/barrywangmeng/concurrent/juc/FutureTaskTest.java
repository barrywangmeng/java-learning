/*
 * Copyright 2020 tuhu.cn All right reserved. This software is the
 * confidential and proprietary information of tuhu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tuhu.cn
 */
package cn.barrywangmeng.concurrent.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author wangmeng
 * @date 2020/5/28 15:30
 */
public class FutureTaskTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService threadPool = Executors.newCachedThreadPool();

        System.out.println("====执行FutureTask线程任务====");
        Future<String> futureTask = threadPool.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("FutureTask执行业务逻辑");
                Thread.sleep(2000);
                System.out.println("FutureTask业务逻辑执行完毕！");
                return "欢迎关注: 一枝花算不算浪漫！";
            }
        });

        System.out.println("====执行主线程任务====");
        Thread.sleep(1000);
        boolean flag = true;
        while(flag){
            if(futureTask.isDone() && !futureTask.isCancelled()){
                System.out.println("FutureTask异步任务执行结果：" + futureTask.get());
                flag = false;
            }
        }

        threadPool.shutdown();
    }
}