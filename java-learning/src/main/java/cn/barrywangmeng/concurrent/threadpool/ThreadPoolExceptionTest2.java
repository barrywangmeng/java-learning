package cn.barrywangmeng.concurrent.threadpool;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 测试线程池主线程感知子线程异常问题
 *
 * @author wangmeng
 * @date 2020/6/13 18:08
 */
public class ThreadPoolExceptionTest2 {

    private static final ThreadFactory FACTORY = new BasicThreadFactory.Builder().namingPattern("myThreadPool")
            .daemon(true).build();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService execute = new MyThreadPool(10, 10,
                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), FACTORY);

        TimeUnit.SECONDS.sleep(5);
        for (int i = 0; i < 10; i++) {
            execute.execute(new MyRunner());
        }
    }

    private static class MyRunner implements Runnable {
        @Override
        public void run() {
            int count = 0;
            while (true) {
                count++;
                System.out.println("我要开始生产Bug了============");
                if (count == 10) {
                    System.out.println(1 / 0);
                }

                if (count == 20) {
                    System.out.println("这里是不会执行到的==========");
                    break;
                }
            }
        }
    }
}

class MyThreadPool extends ThreadPoolExecutor {

    private final static Logger LOGGER = LoggerFactory.getLogger(MyHandler.class);

    public MyThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        try {
            LOGGER.error(String.format("Thread %s: end %s", t.getMessage(), r));
        } finally {
            super.afterExecute(r, t);
        }
    }
}