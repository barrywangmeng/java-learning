package cn.barrywangmeng.cache.lru;

/**
 * @Description:
 * @Author: wangmeng
 * @Date: 2018/12/8-10:13
 */
public interface LRUCache<K, V> {
    void put(K key, V value);

    V get(K key);

    void remove(K key);

    int size();

    void clear();

    int limit();
}
