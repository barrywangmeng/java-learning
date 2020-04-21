package cn.barrywangmeng.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author wangmeng2
 * @date 2020/3/311:34
 */
public class InterruptTest {

    public static void main(String[] args) throws Exception{
        InterruptThread thread = new InterruptThread();
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        thread.setShouldRun(false);
        thread.interrupt();
        System.out.println("=================================" + thread.isInterrupted());
    }

    private static class  InterruptThread extends Thread {
        private Boolean shouldRun = true;

        @Override
        public void  run() {
            while (shouldRun) {
                System.out.println("线程1在执行工作...");
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void setShouldRun(Boolean shouldRun) {
            this.shouldRun = shouldRun;
        }
    }
}
