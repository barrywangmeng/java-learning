package cn.barrywangmeng.cache.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.Weigher;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @Description: Cache 简单测试
 * @Author: wangmeng
 * @Date: 2018/12/9-17:02
 */
public class CacheLoaderTest {

    @Test
    public void testBasic() throws ExecutionException, InterruptedException {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder().expireAfterAccess(1, TimeUnit.SECONDS).build
                (createCacheLoader());

        Employee employee = cache.get("Alex");

        TimeUnit.SECONDS.sleep(2);
        employee = cache.get("Alex");//查看findEmployee执行的次数
    }

    /**
     * 通过key的数量来实现逐出策略
     */
    @Test
    public void testEvictionBySize() {
        CacheLoader<String, Employee> cacheLoader = createCacheLoader();
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder().maximumSize(3).build(cacheLoader);

        cache.getUnchecked("Alex");
        cache.getUnchecked("Jack");
        cache.getUnchecked("Gavin");
        cache.getUnchecked("Barry");
        System.out.println(cache.toString());
    }

    @Test
    public void testEvictionByWeigh() {
        CacheLoader<String, Employee> cacheLoader = createCacheLoader();
        Weigher<String, Employee> weigher = (key, employee) -> employee.getName().length() + employee.getEmpId().length() + employee.getDept().length();
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                .maximumWeight(45)
                .concurrencyLevel(1)
                .weigher(weigher)
                .build(cacheLoader);

        cache.getUnchecked("Alex");
        cache.getUnchecked("Jack");
        cache.getUnchecked("Gavin");
        cache.getUnchecked("Barry");
    }


    private CacheLoader<String, Employee> createCacheLoader() {
        return new CacheLoader<String, Employee>() {
            @Override
            public Employee load(String key) throws Exception {
                return findEmployeeByName(key);
            }
        };
    }

    private Employee findEmployeeByName(String name) {
        System.out.println("The employee " + name + " is load from DB!");
        return new Employee(name, name, name);
    }
}
