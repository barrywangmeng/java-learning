package cn.barrywangmeng.basic;

/**
 * 多线程的学习
 */
public class RunnableThreadTest implements Runnable{

    private String name;

    public RunnableThreadTest(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            for (long k = 0; k < 1000000; k++) {
                System.out.println(name + ": " + i);
            }
        }
    }
}
