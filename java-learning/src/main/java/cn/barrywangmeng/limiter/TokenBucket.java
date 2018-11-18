package cn.barrywangmeng.limiter;

import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.currentThread;

/**
 * @Description: 模拟手机抢购的令牌桶限流器
 * @Author: wangmeng
 * @Date: 2018/11/17-10:51
 */
public class TokenBucket {
    private AtomicInteger phoneNumbers = new AtomicInteger(0);

    //最多100个手机数据
    private final static int LIMIT = 100;

    //限制1s钟只能有10个人抢到机器
    private RateLimiter rateLimiter = RateLimiter.create(10);

    private final int saleLimit;

    public TokenBucket() {
        this(LIMIT);
    }

    public TokenBucket(int limit) {
        this.saleLimit = limit;
    }

    public int buy() {
        Stopwatch started = Stopwatch.createStarted();
        //获取锁，最多等待10s就放弃
        boolean success = rateLimiter.tryAcquire(10, TimeUnit.SECONDS);
        if (success) {
            if (phoneNumbers.get() >= saleLimit) {
                throw new IllegalStateException("Not any phone can be sale, please wait to next time.");
            }

            //抢到手机，处理逻辑, 异步去处理
            int phoneNo = phoneNumbers.getAndIncrement();
            System.out.println(currentThread() + "user get thephone: " + phoneNo + ",ELT: " + started.stop());

            return phoneNo;
        } else {
            started.stop();
            throw new RuntimeException("Sorry, occur exception when buy phone!");
        }
    }

    public static void main(String[] args) {
        TokenBucket tokenBucket = new TokenBucket();
        for (int i = 0; i < 110; i++) {
            new Thread(tokenBucket::buy).start();
        }
    }
}
