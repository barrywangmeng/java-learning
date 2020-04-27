package cn.barrywangmeng.concurrent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 测试singleThreadPool异常问题
 *
 * @author: wangmeng
 * @date: 2019/3/25 23:40
 */
public class ThreadPoolException {
    private final static Logger LOGGER = LoggerFactory.getLogger(ThreadPoolException.class);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService execute = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadFactoryBuilder().setUncaughtExceptionHandler(new MyHandler()).build());

        execute.execute(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("=====11=======");
            }
        });

        TimeUnit.SECONDS.sleep(5);
        execute.execute(new Run1());
    }


    private static class Run1 implements Runnable {
        @Override
        public void run() {
            int count = 0;
            while (true) {
                count++;
                LOGGER.info("-------222-------------{}", count);

                if (count == 10) {
                    System.out.println(1 / 0);
                    try {
                    } catch (Exception e) {
                        LOGGER.error("Exception",e);
                    }
                }

                if (count == 20) {
                    LOGGER.info("count={}", count);
                    break;
                }
            }
        }
    }
}

class MyHandler implements Thread.UncaughtExceptionHandler {
    private final static Logger LOGGER = LoggerFactory.getLogger(MyHandler.class);
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        LOGGER.error("threadId = {}, threadName = {}, ex = {}", t.getId(), t.getName(), e.getMessage());
    }
}