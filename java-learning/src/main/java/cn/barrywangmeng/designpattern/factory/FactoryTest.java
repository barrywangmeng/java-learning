package cn.barrywangmeng.designpattern.factory;

import org.junit.Test;

import cn.barrywangmeng.designpattern.factory.easy.factory.Fruit;
import cn.barrywangmeng.designpattern.factory.easy.factory.Gardener;
import cn.barrywangmeng.designpattern.factory.factory.method.ExportFactory;
import cn.barrywangmeng.designpattern.factory.factory.method.ExportFile;
import cn.barrywangmeng.designpattern.factory.factory.method.ExportHtmlFactory;

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

    @Test
    public void testFactoryMethod() {
        /**
         * 工厂模式方法就是类的创建模式。
         * 工厂方法模式的用意是定义一个创建产品对象的工厂接口，将实际创建工厂推迟到子类中。
         * 工厂方法模式是对简单工厂进一步抽象的结果。
         * 工厂方法模式涉及的角色：
         * 1，抽象工厂角色
         * 2，具体工厂角色
         * 3，抽象导出角色
         * 4，具体导出角色
         */
        String data = "";
        ExportFactory exportFactory = new ExportHtmlFactory();
        ExportFile financial = exportFactory.factory("financial");
        financial.export(data);
    }
}
