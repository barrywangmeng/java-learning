package cn.barrywangmeng.designModel.masterWorker;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @Description:
 * @Author: wangmeng
 * @Date: 2018/12/17-22:21
 */
public class Worker implements Runnable {
    private ConcurrentLinkedQueue<Task> taskQueue;

    private ConcurrentHashMap<String, Object> resultMap;

    public void setTaskQueue(ConcurrentLinkedQueue<Task> taskQueue) {
        this.taskQueue = taskQueue;
    }

    public void setResultMap(ConcurrentHashMap<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    @Override
    public void run() {
        while (true) {
            Task task = this.taskQueue.poll();
            if (task == null) {
                break;
            }

            try {
                Object result = handle(task);
                this.resultMap.put(Integer.toString(task.getId()), result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //实际是去做的每一个工作
    private Object handle(Task task) throws Exception {
        //每一个任务处理的时间是:
        Thread.sleep(200);
        int count = task.getCount();
        return count;
    }
}
