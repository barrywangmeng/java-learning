package cn.barrywangmeng.concurrent.queue;

import java.util.concurrent.DelayQueue;

/**
 * @Description: 模拟网吧
 * @Author: wangmeng
 * @Date: 2018/12/12-23:32
 */
public class NetBar implements Runnable {

    private DelayQueue<NetBarPerson> delayQueue = new DelayQueue<>();
    private boolean start = true;//标识网吧营业
    public void startMachine(String id, String name, int money) {
        NetBarPerson wangmin = new NetBarPerson(id, name, money * 1000 + System.currentTimeMillis());
        System.out.println("网名：" + name + ", 身份证： " + id + ", 缴费：" + money + "元，开始上网！");
        delayQueue.add(wangmin);
    }

    public void overMachine(NetBarPerson wm) {
        System.out.println("网名：" + wm.getName() + ", 身份证： " + wm.getId() + "已经到了下机时间！");
    }

    @Override
    public void run() {
        try {
            NetBarPerson wm = delayQueue.take();
            overMachine(wm);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {


        NetBar wangb = new NetBar();
        System.err.println("网吧正常营业: ");
        Thread yingye = new Thread(wangb);
        yingye.start();

        wangb.startMachine("001", "张三", 2);
        wangb.startMachine("001", "李四", 5);
        wangb.startMachine("001", "王五", 10);

    }
}
