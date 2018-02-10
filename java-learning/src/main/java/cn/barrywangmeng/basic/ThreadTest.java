package cn.barrywangmeng.basic;

public class ThreadTest {
    public static void main(String[] args) {
        RunnableThreadTest rt1 = new RunnableThreadTest("阿三");
        RunnableThreadTest rt2 = new RunnableThreadTest("李四");

        Thread t1 = new Thread(rt1);
        Thread t2 = new Thread(rt2);

        t1.start();
        t2.start();
    }
}
