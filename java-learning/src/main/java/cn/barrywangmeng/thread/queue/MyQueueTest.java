package cn.barrywangmeng.thread.queue;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: wangmeng
 * @Date: 2018/12/12-21:41
 */
public class MyQueueTest {

    public static void main(String[] args) throws Exception {
        MyQueue myQueue = new MyQueue(5);
        myQueue.put("a");
        myQueue.put("b");
        myQueue.put("c");
        myQueue.put("d");
        myQueue.put("e");

        System.out.println("当前元素的个数： " + myQueue.size());

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                myQueue.put("f");
                myQueue.put("g");
            }
        }, "t1");

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                    Object value = myQueue.take();

                    TimeUnit.MILLISECONDS.sleep(1000);
                    Object value2 = myQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t2");

        thread.start();
        TimeUnit.MILLISECONDS.sleep(1000);
        thread2.start();

        TimeUnit.MILLISECONDS.sleep(5000);
        System.out.println(myQueue.getQueueList().toString());
    }
}
