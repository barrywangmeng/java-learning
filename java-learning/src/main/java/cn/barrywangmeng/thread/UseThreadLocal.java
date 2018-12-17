package cn.barrywangmeng.thread;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: wangmeng
 * @Date: 2018/12/16-10:45
 */
public class UseThreadLocal {

    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public String getThreadLocal() {
        return this.threadLocal.get();
    }

    public void setThreadLocal(String value) {
        threadLocal.set(value);
    }

    public static void main(String[] args) {
        UseThreadLocal utl = new UseThreadLocal();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                utl.setThreadLocal("张三");
                System.out.println("当前t1：" + utl.getThreadLocal());
            }
        }, "t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                utl.setThreadLocal("李四");
                System.out.println("当前t2：" + utl.getThreadLocal());
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
