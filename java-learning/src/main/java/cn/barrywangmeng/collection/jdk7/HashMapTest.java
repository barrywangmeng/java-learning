package cn.barrywangmeng.collection.jdk7;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Description:
 * @Author: wangmeng
 * @Date: 2018/11/22-20:45
 */
public class HashMapTest {
    public static void main(String[] args) {
        HashMap<Integer, Integer> map = new HashMap();
        map.put(3, 3);
        map.put(7, 7);
        map.put(11, 11);
        Set<Map.Entry<Integer, Integer>> entries = map.entrySet();
        Integer i = 16;
        System.out.println(i.hashCode());
    }
}
