package cn.barrywangmeng.concurrent.queue;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: 模拟阻塞队列的实现
 * @Author: wangmeng
 * @Date: 2018/12/12-21:21
 */
public class MyQueue {

    //队列的容器
    private final LinkedList<Object> list = new LinkedList<>();

    //计数器int count
    private final AtomicInteger count = new AtomicInteger();

    //最大容量限制
    private final int maxSize;

    //最小容量限制
    private final int minSize = 0;

    //全局锁
    private final Object lock = new Object();

    public MyQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public void put(Object obj) {


        synchronized (lock) {
            while (count.get() == this.maxSize) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //添加新的元素进入到容器中
            list.add(obj);
            count.getAndIncrement();
            System.out.println("元素： " + obj + " 已经添加到容器中！");

            //进行唤醒可能正在等待的take方法操作的线程
            lock.notify();
        }
    }

    public Object take() {
        Object temp = null;

        synchronized (lock) {
            while (count.get() == minSize) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //取出元素
            temp = list.removeFirst();
            count.getAndDecrement();
            lock.notify();//唤醒可能因为list满了而等待的线程
        }

        return temp;
    }

    public int size() {
        return this.count.get();
    }

    public List<Object> getQueueList() {
        return list;
    }
}
