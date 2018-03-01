package cn.barrywangmeng.proxy.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CGLibProxy implements MethodInterceptor {
    private Object target;

    public <T> T getProxy(Class<T> cls) {
        return (T) Enhancer.create(cls, this);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("预处理---");
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("调用后处理---");
        return result;
    }

    public static void main(String[] args) {
        CGLibProxy cgLibProxy = new CGLibProxy();
        cn.barrywangmeng.proxy.cglib.BookFacadeImpl bookFacade = new cn.barrywangmeng.proxy.cglib.BookFacadeImpl();
        cn.barrywangmeng.proxy.cglib.BookFacadeImpl proxy = cgLibProxy.getProxy(bookFacade.getClass());
        proxy.addBook();
    }
}
