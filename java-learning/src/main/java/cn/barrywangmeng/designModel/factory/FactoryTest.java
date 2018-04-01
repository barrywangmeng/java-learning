package cn.barrywangmeng.designModel.factory;

import org.junit.Test;

import cn.barrywangmeng.designModel.factory.easyFactory.Fruit;
import cn.barrywangmeng.designModel.factory.easyFactory.Gardener;

public class FactoryTest {

    @Test
    public void testEasyFactory() {
        //这种调用方式有个缺点，如果我继续添加Fruit的实现类，那么Gardener中需要修改添加if else逻辑，违反了开闭原则
//        Fruit fruit = Gardener.getFruit("APPLE");
//        fruit.grow();
//
//        Fruit fruit1 = Gardener.getFruit("GRAPE");
//        fruit1.grow();

        /**
         * 这种方法虽然不用去修改Gardener类，但是如果后期想更改类的包路径，那么代码的维护成本就会很大
         * 需要修改所有使用此类全限定名的地方。
         * 好的方案是将需要获取的工厂实现类的全限定名写到配置文件中。
         */
        Fruit fruit = Gardener.getFruit("cn.barrywangmeng.designModel.factory.easyFactory.Apple");
        fruit.grow();

        Fruit fruit1 = Gardener.getFruit("cn.barrywangmeng.designModel.factory.easyFactory.Grape");
        fruit1.grow();
    }
}
