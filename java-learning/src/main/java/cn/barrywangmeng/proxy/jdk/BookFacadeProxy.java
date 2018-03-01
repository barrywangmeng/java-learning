package cn.barrywangmeng.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class BookFacadeProxy implements InvocationHandler {
    private Object target;

    public Object bind(Object target) {
        this.target = target;

        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        System.out.println("预处理操作---");

        result = method.invoke(target, args);

        System.out.println("调用后处理---");
        return result;
    }

    public static void main(String[] args) {
        BookFacadeImpl bookFacadeImpl = new BookFacadeImpl();
        BookFacadeProxy bookFacadeProxy = new BookFacadeProxy();
        BookFacade bookFacade = (BookFacade) bookFacadeProxy.bind(bookFacadeImpl);
        bookFacade.addBook();
    }
}
