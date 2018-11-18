package cn.barrywangmeng.basic;

import com.google.common.collect.Lists;

import java.lang.ref.SoftReference;
import java.util.List;
import java.util.Objects;

/**
 * 软引用测试
 */
public class SoftReferenceTest {
    public static void main(String[] args) {
        List<Integer> list = Lists.newArrayList();
        list.add(1);
        list.add(2);

        SoftReference<List<Integer>> softReference = new SoftReference<>(list);
        list = null;
        for (Integer integer : Objects.requireNonNull(softReference.get())) {
            System.out.println(integer);
        }
    }
}
