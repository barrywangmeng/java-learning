package cn.barrywangmeng.cache.reference;

import com.google.common.collect.Lists;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 模拟Java四种引用类型的方法
 * @Author: wangmeng
 * @Date: 2018/12/8-11:10
 */
public class ReferenceExample {
    public static void main(String[] args) throws Exception{
        //Strong reference
//        int counter = 0;
//        List<Ref> container = Lists.newArrayList();
//        for (;;) {
//            int current = counter++;
//            container.add(new Ref(current));
//            System.out.println("The " + current + " Ref will be insert into container");
//            TimeUnit.MICROSECONDS.sleep(500);
//        }

        /**
         * SoftReference:判断JVM快要溢出的时候，JVM GC时会判断有没有SoftReference数据
         */
//        int counter = 0;
//        List<SoftReference<Ref>> container = Lists.newArrayList();
//        for (;;) {
//            int current = counter++;
//            container.add(new SoftReference<>(new Ref(current)));
//            System.out.println("The " + current + " Ref will be insert into container");
//            TimeUnit.MILLISECONDS.sleep(50);
//        }

        /**
         * Weak reference: 当GC的时候就会被回收
         */
        int counter = 0;
        List<WeakReference<Ref>> container = Lists.newArrayList();
        for (;;) {
            int current = counter++;
            container.add(new WeakReference<>(new Ref(current)));
            System.out.println("The " + current + " Ref will be insert into container");
            TimeUnit.MILLISECONDS.sleep(50);
        }

        /**
         * PhantomReference 中有一个最佳实践，可以通过查看：org.apache.commons.io.FileCleaningTracker查看
         *
         * 幽灵引用起到一个通知作用，例如GC后会将
         */
//        Ref ref = new Ref(10);
//        ReferenceQueue queue = new ReferenceQueue<>();
//        MyPhantomReference reference = new MyPhantomReference(ref, queue, 10);
//        ref = null;
//        System.out.println(reference.get());
//        System.gc();
//
//        Reference remove = queue.remove();
//        ((MyPhantomReference)remove).doAction();
    }

    private static class MyPhantomReference extends PhantomReference<Object> {
        private int index;
        public MyPhantomReference(Object referent, ReferenceQueue<? super Object> q, int index) {
            super(referent, q);
            this.index = index;
        }

        public void doAction() {
            System.out.println("The object " + index + " is GC");
        }
    }

    private static class Ref {
        //调用Ref的时候，每次都new出来一个lM的byte，模拟触发GC
        private byte[] data = new byte[1024 * 1024];

        private final int index;

        private Ref(int index) {
            this.index = index;
        }

        @Override
        protected void finalize() throws Throwable {
            System.out.println("The index [" + index + "] will be GC.");
        }
    }
}
