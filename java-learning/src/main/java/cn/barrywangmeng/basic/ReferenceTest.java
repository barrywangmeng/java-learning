package cn.barrywangmeng.basic;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/**
 * 源于小米的一道笔试题
 * 考察引用传递
 * 可以查看自己分析的一篇文章：http://www.cnblogs.com/wang-meng/p/8685948.html
 */
public class ReferenceTest {

    public static void main(String[] args) throws Exception{
        ReferenceQueue<User> referenceQueue = new ReferenceQueue();
        SoftReference softReference = new SoftReference(new User("zhangsan",24), referenceQueue);

        //手动触发GC
        System.gc();
        Thread.sleep(1000);
        System.out.println("手动触发GC:" + softReference.get());
        System.out.println("手动触发的队列:" + referenceQueue.poll());
        //通过堆内存不足触发GC
        makeHeapNotEnough();
        System.out.println("通过堆内存不足触发GC:" + softReference.get());
        System.out.println("通过堆内存不足触发GC:" + referenceQueue.poll());

        WeakReference weakReference = new WeakReference(new User("zhangsan",24));
        System.gc();
        System.out.println("手动触发GC:" + weakReference.get());

        WeakHashMap<User, String> weakHashMap = new WeakHashMap();
        //强引用
        User zhangsan = new User("zhangsan", 24);
        weakHashMap.put(zhangsan, "zhangsan");
        System.gc();
        System.out.println("有强引用的时候:map大小" + weakHashMap.size());
        //去掉强引用
        zhangsan = null;
        System.gc();
        Thread.sleep(1000);
        System.out.println("无强引用的时候:map大小"+weakHashMap.size());

    }

    private static void makeHeapNotEnough() {
        SoftReference softReference = new SoftReference(new byte[1024*1024*5]);
        byte[] bytes = new byte[1024*1024*5];

    }
}
