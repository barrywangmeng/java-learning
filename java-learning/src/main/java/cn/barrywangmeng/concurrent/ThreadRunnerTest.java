package cn.barrywangmeng.concurrent;

public class ThreadRunnerTest {


    public static class MyThread extends Thread {
        @Override
        public void run() {
            //do something
            System.out.println("started...");
        }
    }

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.run();

        System.out.println(myThread.getId() + " " + myThread.getName());
    }
}
