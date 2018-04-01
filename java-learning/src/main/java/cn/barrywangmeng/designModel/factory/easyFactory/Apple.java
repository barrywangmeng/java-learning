package cn.barrywangmeng.designModel.factory.easyFactory;

public class Apple implements Fruit {
    @Override
    public void grow() {
        System.out.println("Apple.grow()");
    }

    @Override
    public void harveset() {
        System.out.println("Apple.harveset()");
    }

    @Override
    public void plant() {
        System.out.println("Apple.plant()");
    }
}
