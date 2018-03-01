package cn.barrywangmeng.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

import org.springframework.stereotype.Service;

/**
 * Guava Cache学习的测试类
 */
@Service
public class GuavaCacheTest {

    //GuavaCache使用时主要有两种模式：LoadingCache和CallableCache
    private Cache<String, String> cache = CacheBuilder.newBuilder().build(new CacheLoader<String, String>() {
        @Override
        public String load(String key) throws Exception {
            return null;
        }
    });
}
