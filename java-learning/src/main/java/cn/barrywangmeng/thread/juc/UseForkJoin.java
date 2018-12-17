package cn.barrywangmeng.thread.juc;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @Description: 模拟加法的运行，执行拆分合并的操作。
 * @Author: wangmeng
 * @Date: 2018/12/17-21:50
 */
public class UseForkJoin extends RecursiveTask<Integer> {
    //设置拆分阈值
    private static final int THRESHOLD = 2;

    private int start;
    private int end;

    public UseForkJoin(int start, int end) {
        this.start = start;
        this.end = end;
    }
    @Override
    protected Integer compute() {
        int sum = 0; //1 + 2 + ... + 100
        boolean canCompute = (end - start) <= THRESHOLD;
        if (canCompute) {
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            //如果说任务数大于阈值的话，就进行拆分fork操作，然后去join
            //1 + 100 / 2 = 50
            int middle = (start + end) / 2;
            UseForkJoin leftTask = new UseForkJoin(start, middle);
            UseForkJoin rightTask = new UseForkJoin(middle + 1, end);

            //执行左右两边的任务
            leftTask.fork(); //执行拆分
            rightTask.fork();//执行拆分

            //等待任务执行完成后 进行获取结果
            int leftResult = leftTask.join();
            int rightResult = rightTask.join();
            sum = leftResult + rightResult;
        }

        return sum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        UseForkJoin ufj = new UseForkJoin(1, 100);
        ForkJoinTask<Integer> submit = forkJoinPool.submit(ufj);
        Integer value = submit.get();
        System.out.println("最终返回的结果是：" + value);
    }
}
