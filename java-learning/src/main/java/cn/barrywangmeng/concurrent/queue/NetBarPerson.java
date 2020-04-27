package cn.barrywangmeng.concurrent.queue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 模拟网民
 * @Author: wangmeng
 * @Date: 2018/12/12-23:33
 */
public class NetBarPerson implements Delayed {

    private String id;
    private String name;
    private long endTime;//上网的截止时间
    private TimeUnit timeUnit = TimeUnit.SECONDS;

    public NetBarPerson() {
    }

    public NetBarPerson(String id, String name, long endTime) {
        this.id = id;
        this.name = name;
        this.endTime = endTime;
    }


    // 用来判断是否到了下机时间
    @Override
    public long getDelay(TimeUnit unit) {
        return endTime - System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed delayed) {
        NetBarPerson w = (NetBarPerson)delayed;
        return this.getDelay(timeUnit) - w.getDelay(timeUnit) > 0 ? 1 : 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
