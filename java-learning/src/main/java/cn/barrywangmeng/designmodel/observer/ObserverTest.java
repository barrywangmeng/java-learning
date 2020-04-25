package cn.barrywangmeng.designmodel.observer;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 观察者模式测试底阿妈
 *
 * @author wangmeng
 * @date 2020/4/25 19:38
 */
public class ObserverTest {

    public static void main(String[] args) {
        Subject subject = new Subject();
        Task1 task1 = new Task1();
        subject.addObserver(task1);

        Task2 task2 = new Task2();
        subject.addObserver(task2);

        subject.notifyObserver("xxxx");
    }
}

class Subject {
    // 容器
    private List<Observer> observerList = Lists.newArrayList();

    // add
    public void addObserver(Observer observer) {
        observerList.add(observer);
    }

    // remove
    public void removeObserver(Observer observer) {
        observerList.remove(observer);
    }

    // 通知观察者
    public void notifyObserver(Object object) {
        for (Observer item : observerList) {
            item.update(object);
        }
    }
}

interface Observer {
    void update(Object object);
}

class Task1 implements Observer {
    @Override
    public void update(Object object) {
        System.out.println("task1 received: " + object);
    }
}

class Task2 implements Observer {
    @Override
    public void update(Object object) {
        System.out.println("task2 received: " + object);
    }
}