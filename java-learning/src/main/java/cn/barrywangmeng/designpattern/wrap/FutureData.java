package cn.barrywangmeng.designpattern.wrap;

/**
 * @Description: 包装类
 * @Author: wangmeng
 * @Date: 2018/12/17-21:01
 */
public class FutureData implements Data {
    //真实数据对象的引用
    private RealData realData;
    //实际处理状态
    private boolean isReady = false;

    @Override
    public synchronized String getRequest() {
        while (!isReady) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return this.realData.getRequest();
    }

    public synchronized void setRealData(RealData realData) {
        if (isReady) {
            return;
        }

        //如果真实对象赋值成功，那么就认为数据已经准备好了
        this.realData = realData;
        isReady = true;

        //真实数据已经准备好了的时候，我们进行唤醒操作
        notify();
    }
}
