package cn.barrywangmeng.concurrent.juc.lock;

/**
 * @Description: 模拟synchronize死锁案例
 * @Author: wangmeng
 * @Date: 2019/1/26-22:30
 */
public class SyncDeadLock {
    private static Object lockA = new Object();
    private static Object lockB = new Object();

    public static void main(String[] args) {
        new SyncDeadLock().deadLock();
    }

    private void deadLock() {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lockA) {
                    try {
                        System.out.println(Thread.currentThread().getName() + " get lockA ing!");
                        Thread.sleep(500);
                        System.out.println(Thread.currentThread().getName() + " after sleep 5000ms!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread().getName() + " need lockB! just waiting!");
                    synchronized (lockB) {
                        System.out.println(Thread.currentThread().getName() + " get lockB ing!");
                    }
                }
            }
        }, "Thread1");

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lockB) {
                    try {
                        System.out.println(Thread.currentThread().getName() + " get lockB ing!");
                        Thread.sleep(500);
                        System.out.println(Thread.currentThread().getName() + " after sleep 5000ms!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread().getName() + " need lockA! just waiting!");
                    synchronized (lockA) {
                        System.out.println(Thread.currentThread().getName() + " get lockA ing!");
                    }
                }
            }
        }, "Thread2");
        thread1.start();
        thread2.start();
    }
}
