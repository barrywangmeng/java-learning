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
     * 第一个singleTon==null存在的意义：
     * 这里就涉及一个性能问题了，因为对于单例模式的话，new SingleTon（）只需要执行一次就 OK 了，
     * 而如果没有第一重 singleTon == null 的话，每一次有线程进入 getInstance（）时，均会执行锁定操作来实现线程同步，
     * 这是非常耗费性能的，而如果我加上第一重 singleTon == null 的话，
     * 那么就只有在第一次，也就是 singleton ==null 成立时的情况下执行一次锁定以实现线程同步，
     * 而以后的话，便只要直接返回 Singleton 实例就 OK 了而根本无需再进入 lock 语句块了，这样就可以解决由线程同步带来的性能问题了。
     *
     * 第二种方式使用了double check
     * 但是这里仍然可能存在问题，双重锁可能不能正常运行
     * 具体原因如下：
     * A线程在进入到synchronize后，在new对象的时候，是有三个步骤的：分配内存空间，
     * 初始化对象，然后将内存地址赋值给变量；在这么三个步骤中，极有可能会在操作上进行重排序，在重排序的情况下，还没有初始化
     * 对象，先将内存地址赋值给了变量（这种情况是可能存在的），当线程B进入时，发现变量不为null，就会直接返回这个实例，然而此时
     * 可能拿到的是还没有初始化完成的对象。
     * 解决方案是将singleton加上volatile修饰，volatile能够防止重排序。
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
