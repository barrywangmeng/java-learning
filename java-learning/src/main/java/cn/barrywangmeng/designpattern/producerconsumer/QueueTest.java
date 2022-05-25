package cn.barrywangmeng.designpattern.producerconsumer;

import java.util.PriorityQueue;

/**
 * Object.wait()和Object.notify()、非阻塞队列实现生产者-消费者模式
 */
public class QueueTest {
    private int queueSize = 10;
    private PriorityQueue<Integer> queue = new PriorityQueue<>(queueSize);

    public static void main(String[] args) {
        QueueTest queueTest = new QueueTest();
        Producer producer = queueTest.new Producer();
        Consumer consumer = queueTest.new Consumer();

        producer.start();
        consumer.start();
    }

    class Consumer extends Thread {

        @Override
        public void run() {
            consume();
        }

        private void consume() {
            while (true) {
                synchronized (queue) {
                    while (queue.size() == 0) {
                        System.out.println("队列空，等待数据");
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            queue.notify();
                        }
                    }

                    queue.poll(); //取出队首元素
                    queue.notify();
                    System.out.println("Thread：" + Thread.currentThread().getId() + "从队列取走一个元素，队列剩余：" + queue.size() + "个元素！");
                }
            }
        }
    }

    class Producer extends Thread {

        @Override
        public void run() {
            produce();
        }

        private void produce() {
            while (true) {
                synchronized (queue) {
                    while (queue.size() == queueSize) {
                        System.out.println("队列满了，等待剩余空间！");
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            queue.notify();
                        }
                    }

                    queue.offer(1); //每次插入一条数据
                    queue.notify();
                    System.out.println("Thread：" + Thread.currentThread().getId() + "向队列中插入一条数据， 队列剩余空间为：" + (queueSize - queue.size()));
                }
            }
        }
    }
}
