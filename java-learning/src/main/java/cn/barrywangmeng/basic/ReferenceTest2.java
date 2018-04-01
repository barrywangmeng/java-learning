package cn.barrywangmeng.basic;

public class ReferenceTest2 {
    private User user = new User("Jack", 1l);

    private int num;

    private String string ="";

    //提供改变自身方法的引用类型
    void fun0(User user){
        user.setName("Lucy");
    }

    //基本类型作为参数传递时，是传递值的拷贝，无论你怎么改变这个拷贝，原值是不会改变的
    void fun1(int value) {
        value = 100;
    }

    //没有提供改变自身方法的引用类型
    void fun2(String text) {
        text = "windows";
    }

    //在Java中对象作为参数传递时，是把对象在内存中的地址拷贝了一份传给了参数。
    public static void main(String[] args) {
        ReferenceTest2 demo = new ReferenceTest2();
        demo.num = 10;
        demo.string = "Linux";

        System.out.println("before name: " + demo.user.getName());//Jack
        demo.fun0(demo.user);
        System.out.println("after name: " + demo.user.getName());//Lucy

        System.out.println("before num: " + demo.num);//10
        demo.fun1(demo.num);
        System.out.println("after num: " + demo.num);//传递的是一份拷贝，只是拷贝的数据被修改了

        System.out.println("before string: " + demo.string);//Linux
        demo.fun2(demo.string);
        System.out.println("after string: " + demo.string);//Linux
    }
}

class User {
    private String name;
    private long age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public User(String name, long age) {
        this.name = name;
        this.age = age;
    }
}
