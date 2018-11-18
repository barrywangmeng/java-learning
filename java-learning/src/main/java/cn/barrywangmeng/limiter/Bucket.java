package cn.barrywangmeng.limiter;

import com.google.common.util.concurrent.Monitor;

import java.util.concurrent.ConcurrentLinkedQueue;

import static java.lang.Thread.currentThread;

/**
 * @Description: 模拟桶限流器
 * @Author: wangmeng
 * @Date: 2018/11/17-0:17
 */
public class Bucket {
    public final ConcurrentLinkedQueue<Integer> container = new ConcurrentLinkedQueue<>();

    private final static int BUCKET_LIMIT = 1000;

    private final Monitor offerMonitor = new Monitor();

    public void submit(Integer data) {
        if (offerMonitor.enterIf(offerMonitor.newGuard(() ->container.size() < BUCKET_LIMIT))) {
            try {
                container.offer(data);
                System.out.println(currentThread() + " submit data " + data + ",current size:" + container.size());
            } finally {
                offerMonitor.leave();
            }
        } else {
            throw new IllegalStateException("The bucket is full");
        }
    }
}
