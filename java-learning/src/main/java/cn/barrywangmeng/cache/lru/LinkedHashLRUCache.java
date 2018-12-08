package cn.barrywangmeng.cache.lru;

import com.google.common.base.Preconditions;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description: 不是一个线程安全的类，这里是使用LinkedHashMap来做LRU算法
 * @Author: wangmeng
 * @Date: 2018/12/8-10:14
 */
public class LinkedHashLRUCache<K, V> implements LRUCache<K, V> {

    private static class InternalLRUCache<K, V> extends LinkedHashMap<K, V> {

        final private int limit;
        private InternalLRUCache(int limit) {
            super(16, 0.75f, true);
            this.limit = limit ;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return  size() > limit;
        }
    }


    private final int limit;
    //使用组合关系犹豫继承，这里只对外暴漏LRUCache中的方法
    private final InternalLRUCache<K, V> internalLRUCache;
    public LinkedHashLRUCache(int limit) {
        Preconditions.checkArgument(limit > 0, "The limit big than zero.");
        this.limit = limit;
        this.internalLRUCache = new InternalLRUCache(limit);

    }

    @Override
    public void put(K key, V value) {
        this.internalLRUCache.put(key, value);
    }

    @Override
    public V get(K key) {
        return this.internalLRUCache.get(key);
    }

    @Override
    public void remove(K key) {
        this.internalLRUCache.remove(key);
    }

    @Override
    public int size() {
        return this.internalLRUCache.size();
    }

    @Override
    public void clear() {
        this.internalLRUCache.clear();
    }

    @Override
    public int limit() {
        return this.limit;
    }

    @Override
    public String toString() {
        return internalLRUCache.toString();
    }
}
