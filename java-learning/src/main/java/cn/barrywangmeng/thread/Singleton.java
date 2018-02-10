package cn.barrywangmeng.thread;

/**
 * 这里来记录单例模式的线程安全问题
 * 以及volatile关键字在这里的用法
 */
public class Singleton {
    private static volatile Singleton singleton = null;

    /**
     * 懒汉式单例
     * 第一种方式很容易产生线程安全问题
     * 如果线程线程1和线程2同时进入if语句，那么将会产生两个Singleton实例
     */
    public static Singleton getSingleton1() {
        if (singleton == null) {
            return new Singleton();
        }

        return singleton;
    }

    /**
     * 第二种方式使用了double check
     * 但是这里仍然可能存在问题，双重锁可能不能正常运行
     * 具体原因如下：
     * A线程在进入到synchronize后，在new对象的时候，是有三个步骤的：分配内存空间，
     * 初始化对象，然后将内存地址赋值给变量；在这么三个步骤中，极有可能会在操作上进行重排序，在重排序的情况下，还没有初始化
     * 对象，先将内存地址赋值给了变量（这种情况是可能存在的），当线程B进入时，发现变量不为null，就会直接返回这个实例，然而此时
     * 可能拿到的是还没有初始化完成的对象。
     * 解决方案是将sinleton加上volatile修饰，volatile能够防止重排序。
     */
    public static Singleton getSingleton2() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    return new Singleton();
                }
            }
        }

        return singleton;
    }

    public Singleton() {
    }
}
