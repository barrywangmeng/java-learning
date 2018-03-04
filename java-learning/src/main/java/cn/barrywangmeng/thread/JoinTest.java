package cn.barrywangmeng.thread;

/**
 * 这里是用来测试学习多线程关键字join
 * 需求：现在有T1、T2、T3三个线程，你怎样保证T2在T1执行完后执行，T3在T2执行完后执行
 */
public class JoinTest {
    public static void main(String[] args) throws InterruptedException {
        Thread th1 = new Thread01();
        Thread th2 = new Thread02();
        Thread th3 = new Thread03();
        th1.start();
        th1.join();
        System.out.println("Thread01运行结束。。。");
        th2.start();
        th2.join();
        System.out.println("Thread02运行结束。。。");
        th3.start();
        th3.join();
        System.out.println("Thread03运行结束。。。");
        System.out.println("------主函数-------");
    }
}

class Thread01 extends Thread{

    public void run() {
        System.out.println("Thread01...running...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
class Thread02 extends Thread{
    public void run() {
        System.out.println("Thread02...running...");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
class Thread03 extends Thread{
    public void run() {
        System.out.println("Thread03...running...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
