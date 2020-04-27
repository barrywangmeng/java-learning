package cn.barrywangmeng.concurrent.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: wangmeng
 * @Date: 2018/12/17-20:44
 */
public class UseFuture implements Callable<String> {

    private String param;

    public UseFuture(String param) {
        this.param = param;
    }

    @Override
    public String call() throws Exception {
        //模拟执行业务逻辑的耗时
        TimeUnit.SECONDS.sleep(3);
        String result = this.param + " 处理完成！";
        return result;
    }

    public static void main(String[] args) throws Exception{
        String queryStr = "query1";
        String queryStr2 = "query2";
        FutureTask<String> future1 = new FutureTask<String>(new UseFuture(queryStr));
        FutureTask<String> future2 = new FutureTask<String>(new UseFuture(queryStr2));

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(future1);//异步操作
        executorService.submit(future2);//异步操作

        System.out.println("执行中...");

        TimeUnit.SECONDS.sleep(2);//处理其他相关的任务。
        String result1 = future1.get();
        String result2 = future2.get();

        System.out.println("数据处理完成。。" + result1);
        System.out.println("数据处理完成。。" + result2);
    }
}
