package cn.barrywangmeng.java8;

import java.util.Arrays;
import java.util.stream.Collectors;

public class IntStreamTest {
    public static void main(String[] args) {
        /**
         * 去小米笔试题有一个将int数组转化为string数组，用java8的lambda表达式
         */
        int[] arrays = new int[]{1, 2, 3, 4, 5};
        String[] strings = Arrays.stream(arrays).mapToObj(value -> value + "").collect(Collectors.toList()).toArray(new String[arrays.length]);

        //延伸将string数组在转化为int数组
        int[] intArrays = Arrays.stream(strings).mapToInt(Integer::valueOf).toArray();
        System.out.println(intArrays);
    }
}
