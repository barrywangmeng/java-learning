package cn.barrywangmeng.cache.lru;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import java.util.LinkedList;
import java.util.Map;

/**
 * @Description:使用LinkedList+HashMap来实现LRU算法
 * @Author: wangmeng
 * @Date: 2018/12/8-10:41
 */
public class LinkedListLRUCache<K, V> implements LRUCache<K, V> {

    private final int limit;
    private final LinkedList<K> keys = new LinkedList<>();
    private final Map<K, V> cache = Maps.newHashMap();

    public LinkedListLRUCache(int limit) {
        this.limit = limit;
    }

    @Override
    public void put(K key, V value) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);
        if (keys.size() >= limit) {
            K oldesKey = keys.removeFirst();
            cache.remove(oldesKey);
        }

        keys.addLast(key);
        cache.put(key, value);
    }

    @Override
    public V get(K key) {
        boolean exist = keys.remove(key);
        if (!exist) {
            return null;
        }

        keys.addLast(key);
        return cache.get(key);
    }

    @Override
    public void remove(K key) {

        boolean exist = keys.remove(key);
        if (exist) {
            keys.remove(key);
            cache.remove(key);
        }
    }

    @Override
    public int size() {
        return keys.size();
    }

    @Override
    public void clear() {
        keys.clear();
        cache.clear();
    }

    @Override
    public int limit() {
        return this.limit;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (K key : keys) {
            builder.append(key).append("=").append(cache.get(key)).append(";");
        }
        return builder.toString();
    }
}
