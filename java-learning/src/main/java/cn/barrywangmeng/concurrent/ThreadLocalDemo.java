package cn.barrywangmeng.concurrent;

import java.lang.reflect.Field;

/**
 * @Description: 测试ThreadLocal内存泄漏情况
 * @Author: wangmeng
 * @Date: 2018/12/16-10:54
 */
public class ThreadLocalDemo {

    private static final int HASH_INCREMENT = 0x61c88647;

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, InterruptedException {
//        Thread t = new Thread(()->test("abc",false));
//        t.start();
//        t.join();
//        System.out.println("--gc后--");
//        Thread t2 = new Thread(() -> test("def", true));
//        t2.start();
//        t2.join();

        int hashCode = 0;
        for(int i=0; i< 32; i++){
            hashCode = i * HASH_INCREMENT+HASH_INCREMENT;//每次递增HASH_INCREMENT
            System.out.println(i + ":" + (hashCode & (16-1)));
        }
    }

    private static void test(String s,boolean isGC)  {
        try {
            ThreadLocal<Object> threadLocal = new ThreadLocal<>();
            threadLocal.set(s);
            if (isGC) {
                System.gc();
            }
            Thread t = Thread.currentThread();
            Class<? extends Thread> clz = t.getClass();
            Field field = clz.getDeclaredField("threadLocals");
            field.setAccessible(true);
            Object threadLocalMap = field.get(t);
            Class<?> tlmClass = threadLocalMap.getClass();
            Field tableField = tlmClass.getDeclaredField("table");
            tableField.setAccessible(true);
            Object[] arr = (Object[]) tableField.get(threadLocalMap);
            for (Object o : arr) {
                if (o != null) {
                    Class<?> entryClass = o.getClass();
                    Field valueField = entryClass.getDeclaredField("value");
                    Field referenceField = entryClass.getSuperclass().getSuperclass().getDeclaredField("referent");
                    valueField.setAccessible(true);
                    referenceField.setAccessible(true);
                    System.out.println(String.format("弱引用key:%s,值:%s", referenceField.get(o), valueField.get(o)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
