package cn.barrywangmeng.limiter;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @Description:
 * @Author: wangmeng
 * @Date: 2018/11/17-0:27
 */
public class BucketTest {
    public static void main(String[] args) {
        final Bucket bucket = new Bucket();
        final AtomicInteger DATA_CREATOR = new AtomicInteger(0);
        IntStream.range(0, 5).forEach(i -> {
            new Thread(() -> {
                for (; ;) {
                    int data = DATA_CREATOR.getAndIncrement();
                    bucket.submit(data);
                    try {
                        TimeUnit.MICROSECONDS.sleep(200L);
                    } catch (Exception e) {
                        if (e instanceof IllegalStateException) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }).start();
        });
    }
}
