package cn.barrywangmeng.concurrent.queue;

/**
 * @Description:
 * @Author: wangmeng
 * @Date: 2018/12/12-21:59
 */
public class UseBlockingQueue {

    public static void main(String[] args) throws Exception{
        //1，高性能无阻塞队列
        /*
        ConcurrentLinkedQueue<String> clq = new ConcurrentLinkedQueue<>();
        clq.offer("a");
        clq.add("b");
        clq.add("c");
        clq.add("d");

        //从头部取出一个元素，并移除
        System.out.println("头部取出元素：" + clq.poll());
        System.out.println("容器长度：" + clq.size());
        //从头部取出一个元素但是不会移除
        System.out.println("从头部取出元素：" + clq.peek());
        */

        //2. 有界阻塞队列
        /*ArrayBlockingQueue<String> abq = new ArrayBlockingQueue<>(5);
        abq.add("a");
        abq.add("b");
        abq.add("c");
        abq.add("d");
        abq.add("e");

        //offer 插入队列，如果满了就等待，等待指定时间返回。插入成功返回true
        System.out.println(abq.offer("f", 2, TimeUnit.SECONDS));

        ArrayBlockingQueue<String> abq2 = new ArrayBlockingQueue<>(5);
        abq.drainTo(abq2, 3);
        for (Iterator iterator =  abq2.iterator(); iterator.hasNext();) {
            String value = (String)iterator.next();
            System.out.println("元素： " + value);
        }*/

        //3. 无界阻塞队列
        //LinkedBlockingQueue<String> lbq = new LinkedBlockingQueue<>();

        //4. SynchronousQueue，不能盛饭任何元素的阻塞队列
        /*SynchronousQueue<String> sq = new SynchronousQueue<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("元素内容：" + sq.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sq.add("a");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();*/

        //5. PriorityBlockingQueue
        /*PriorityBlockingQueue<Node> pbq = new PriorityBlockingQueue<Node>();
        Node n3 = new Node(3, "node3");
        Node n4 = new Node(4, "node4");
        Node n2 = new Node(2, "node2");
        Node n1 = new Node(1, "node1");

        pbq.add(n4);
        pbq.add(n3);
        pbq.add(n1);
        pbq.add(n2);

        System.err.println("0 容器为: " + pbq);
        System.err.println("1 获取元素: " + pbq.take().getId());
        System.err.println("1 容器为: " + pbq);
        System.err.println("2 获取元素: " + pbq.take().getId());
        System.err.println("2 容器为: " + pbq);
        System.err.println("3 获取元素: " + pbq.take().getId());
        System.err.println("3 容器为: " + pbq);
        System.err.println("4 获取元素: " + pbq.take().getId());*/
    }
}
