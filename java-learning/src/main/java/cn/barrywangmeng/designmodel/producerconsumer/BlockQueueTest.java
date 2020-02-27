package cn.barrywangmeng.designmodel.producerconsumer;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 用阻塞队列来模拟生产者消费者
 */
public class BlockQueueTest {

    private int queueSize = 10;
    private ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(queueSize);

    public static void main(String[] args) {
        BlockQueueTest blockQueueTest = new BlockQueueTest();
        Consumer consumer = blockQueueTest.new Consumer();
        Producer producer = blockQueueTest.new Producer();

        producer.start();
        consumer.start();
    }

    class Consumer extends Thread {
        @Override
        public void run() {
            consumer();
        }

        private void consumer() {
            while (true) {
                try {
                    queue.take();
                    System.out.println("Thread：" + Thread.currentThread().getId() + "从队列取走一个元素，队列剩余：" + queue.size() + "个元素！");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Producer extends Thread {

        @Override
        public void run() {
            producer();
        }

        private void producer() {
            while (true) {
                try {
                    queue.put(1);
                    System.out.println("Thread：" + Thread.currentThread().getId() + "向队列中插入一条数据， 队列剩余空间为：" + (queueSize - queue.size()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
