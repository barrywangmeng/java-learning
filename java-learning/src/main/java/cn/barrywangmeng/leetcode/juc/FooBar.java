package cn.barrywangmeng.leetcode.juc;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Description 多线程交替打印foobar
 * https://leetcode-cn.com/problems/print-foobar-alternately/</br>
 * @Author wangmeng
 * @Date 2022/3/14 12:35
 */
public class FooBar {

    /**
     * 两个不同的线程将会共用一个 FooBar实例：
     *
     * 线程 A 将会调用foo()方法，而
     * 线程 B 将会调用bar()方法
     * 请设计修改程序，以确保 "foobar" 被输出 n 次。
     */
    private int n;
    private BlockingQueue<Integer> foo = new LinkedBlockingDeque<>();
    private BlockingQueue<Integer> bar = new LinkedBlockingDeque<>();

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            foo.put(i);
            printFoo.run();
            bar.put(i);
        }
    }


    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            bar.take();
            printBar.run();
            foo.take();
        }
    }
}

class FooBarTest {
    public static void main(String[] args) throws Exception{
        FooBar fooBar = new FooBar(5);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    fooBar.foo(new Runnable() {
                        @Override
                        public void run() {
                            System.out.print("foo");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    fooBar.bar(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("bar");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}