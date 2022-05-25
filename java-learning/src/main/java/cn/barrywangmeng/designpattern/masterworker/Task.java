package cn.barrywangmeng.designpattern.masterworker;

/**
 * @Description:
 * @Author: wangmeng
 * @Date: 2018/12/17-22:20
 */
public class Task {
    private int id;
    private int count;

    public Task() {
    }

    public Task(int id, int count) {
        this.id = id;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
