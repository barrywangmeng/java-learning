package cn.barrywangmeng.designModel.Singleton;

/**
 * 创建一个线程安全的单例，这里用了double check
 */
public class Singleton {

    public Singleton() {
    }

    private static volatile Singleton singleton = null;

    public Singleton getSingleton() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    return new Singleton();
                }
            }
        }

        return singleton;
    }
}
