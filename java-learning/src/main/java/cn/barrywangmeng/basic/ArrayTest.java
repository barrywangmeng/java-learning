package cn.barrywangmeng.basic;

import java.util.Arrays;
import java.util.List;

/**
 * 测试数组中一些常用操作
 *
 * @author wangmeng 2018/02/04
 */
public class ArrayTest {
    public static void main(String[] args) {
        //这里asList
        Integer[] datas = new Integer[]{1, 2, 3, 4, 5};
        List list = Arrays.asList(datas);

        System.out.println(list.size());
    }
}
