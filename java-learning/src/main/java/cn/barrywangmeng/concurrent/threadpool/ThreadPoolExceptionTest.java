package cn.barrywangmeng.concurrent.threadpool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 测试线程池主线程感知子线程异常问题
 *
 * @author wangmeng
 * @date 2020/6/13 18:08
 */
public class ThreadPoolExceptionTest {

    public static void main(String[] args) throws InterruptedException {
        MyHandler myHandler = new MyHandler();
        ExecutorService execute = new ThreadPoolExecutor(10, 10,
                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadFactoryBuilder().setUncaughtExceptionHandler(myHandler).build());

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

class MyHandler implements Thread.UncaughtExceptionHandler {
    private final static Logger LOGGER = LoggerFactory.getLogger(MyHandler.class);
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        LOGGER.error("threadId = {}, threadName = {}, ex = {}", t.getId(), t.getName(), e.getMessage());
    }
}