package cn.barrywangmeng.cache.lru;

/**
 * @Description:
 * @Author: wangmeng
 * @Date: 2018/12/8-10:30
 */
public class LinkedHashLRUTest {
    public static void main(String[] args) {
        LRUCache<String, String> cache = new LinkedHashLRUCache<>(3);
        cache.put("1", "1");
        cache.put("2", "2");
        cache.put("3", "3");
        System.out.println(cache);

        cache.put("4", "4");
        System.out.println(cache);

        System.out.println(cache.get("2"));
        System.out.println(cache);

        // 测试linkedHashMap中的accessOrder属性
//        LinkedHashMap<Integer, Integer> map = new LinkedHashMap(16, 0.75f, true);
//        map.put(19, 19);
//        map.put(3, 3);
//        map.put(35, 35);
//        map.put(7, 7);
//
//        Integer integer = map.get(35);
//        System.out.println(JSON.toJSONString(map));
    }
}
