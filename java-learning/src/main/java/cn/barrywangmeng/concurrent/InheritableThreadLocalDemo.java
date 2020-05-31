package cn.barrywangmeng.concurrent;

public class InheritableThreadLocalDemo {

    public static void main(String[] args) {
//        ThreadLocal<String> threadLocal = new ThreadLocal<>();
//        ThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();
//        threadLocal.set("父类数据:threadLocal");
//        inheritableThreadLocal.set("父类数据:inheritableThreadLocal");
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("子线程获取父类threadLocal数据：" + threadLocal.get());
//                System.out.println("子线程获取父类inheritableThreadLocal数据：" + inheritableThreadLocal.get());
//            }
//        }).start();

        int a = 12 & 7 << 1;
        System.out.println(a);

        int sum = sum(5, 9);
        System.out.println(sum);
    }

    public static int sum(int a, int b) {
        while(b != 0){
            int temp = a ^ b;
            b = (a & b) << 1;
            a = temp;
        }

        return a;
    }
}
