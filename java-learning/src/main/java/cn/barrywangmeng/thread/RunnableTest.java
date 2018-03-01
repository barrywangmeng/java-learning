package cn.barrywangmeng.thread;

public class RunnableTest {

    public static class MyRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println("started..");
        }
    }

    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();
        Thread thread = new Thread(myRunnable);
        thread.start();

        System.out.println(thread.getId() + " " + thread.getName());
    }
}
