package cn.barrywangmeng.cache.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

/**
 * @Description: Cache 简单测试2
 * @Author: wangmeng
 * @Date: 2018/12/9-17:02
 */
public class CacheLoaderTest2 {

    /**
     * TTL - > time to alive
     * Access time = /Write/Update/Read
     */
    @Test
    public void testEvictByAccessTime() throws ExecutionException, InterruptedException {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder().expireAfterAccess(2, TimeUnit.SECONDS).build(createCacheLoader());

        assertThat(cache.getUnchecked("Barry"), notNullValue());
        assertThat(cache.size(), equalTo(1L));

        TimeUnit.SECONDS.sleep(3);
        assertThat(cache.getIfPresent("Barry"), nullValue());

        TimeUnit.SECONDS.sleep(1);
        assertThat(cache.getUnchecked("Guava"), notNullValue());
        TimeUnit.SECONDS.sleep(1);
        assertThat(cache.getUnchecked("Guava"), notNullValue());
        TimeUnit.SECONDS.sleep(1);
        assertThat(cache.getUnchecked("Guava"), notNullValue());
    }

    /**
     * Write time => write/update
     */
    @Test
    public void testEvictByWriteTime() throws ExecutionException, InterruptedException {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder().refreshAfterWrite(2, TimeUnit.SECONDS).build
                (createCacheLoader());

        assertThat(cache.getUnchecked("Barry"), notNullValue());
        assertThat(cache.size(), equalTo(1L));

        TimeUnit.SECONDS.sleep(3);
        assertThat(cache.getIfPresent("Barry"), nullValue());

        TimeUnit.SECONDS.sleep(1);
        assertThat(cache.getUnchecked("Guava"), notNullValue());
        TimeUnit.SECONDS.sleep(1);
        assertThat(cache.getUnchecked("Guava"), notNullValue());
        TimeUnit.SECONDS.sleep(1);
        assertThat(cache.getUnchecked("Guava"), notNullValue());
    }

    /**
     * 可以指定key和value 的引用类型
     */
    @Test
    public void testWeakReference() throws ExecutionException, InterruptedException {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.SECONDS)
                .weakKeys()
                .weakValues()
                .build(createCacheLoader());

        assertThat(cache.getUnchecked("Barry"), notNullValue());
        assertThat(cache.size(), equalTo(1L));

        TimeUnit.SECONDS.sleep(3);
        assertThat(cache.getIfPresent("Barry"), nullValue());

        TimeUnit.SECONDS.sleep(1);
        assertThat(cache.getUnchecked("Guava"), notNullValue());
        TimeUnit.SECONDS.sleep(1);
        assertThat(cache.getUnchecked("Guava"), notNullValue());
        TimeUnit.SECONDS.sleep(1);
        assertThat(cache.getUnchecked("Guava"), notNullValue());
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
