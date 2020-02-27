package cn.barrywangmeng.designmodel.masterworker;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @Description:
 * @Author: wangmeng
 * @Date: 2018/12/17-22:22
 */
public class Master {

    //1，盛装任务的一个容器
    private ConcurrentLinkedQueue<Task> taskQueue = new ConcurrentLinkedQueue<>();

    //2， 盛装worker执行器
    private HashMap<String, Thread> workers = Maps.newHashMap();

    //3， 接收worker处理成功的结果集合
    private ConcurrentHashMap<String, Object> resultMap = new ConcurrentHashMap<>();

    //4， 构造方法里面，要对worker进行一个初始化操作
    public Master(Worker worker, int workCount) {
        //4.1 每一个worker应该有master里任务队列容器的引用
        worker.setTaskQueue(this.taskQueue);

        //4.2 每一个worker应该有master里结果集容器的引用
        worker.setResultMap(this.resultMap);

        //4.3 把所有的worker进行初始化，放入worker容器中
        for (int i = 0; i < workCount; i++) {
            this.workers.put(Integer.toString(i), new Thread(worker));
        }
    }

    //5 需要一个提交任务的方法
    public void submit(Task task) {
        this.taskQueue.add(task);
    }

    //6 需要有一个真正让我们Master里所有的Worker进行工作的方法
    public void execute() {
        for (Map.Entry<String, Thread> stringThreadEntry : this.workers.entrySet()) {
            stringThreadEntry.getValue().start();
        }
    }

    //7 需要有一个统计的方法，用于合并结果集
    public int getResult() {
        int sum = 0;
        for (String key : resultMap.keySet()) {
            Object value = resultMap.get(key);
            sum += (Integer) value;
        }

        return sum;
    }

    //8 判断是否所有的worker都完成任务了，如果完成返回true
    public boolean isComplete() {
        for (Map.Entry<String, Thread> stringThreadEntry : this.workers.entrySet()) {
            if (stringThreadEntry.getValue().getState() != Thread.State.TERMINATED) {
                return false;
            }
        }

        return true;
    }
}
