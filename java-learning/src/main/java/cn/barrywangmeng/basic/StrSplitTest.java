package cn.barrywangmeng.basic;

import com.google.common.collect.Lists;

import java.util.List;

public class StrSplitTest {
    public static void main(String[] args) {
        //构建测试数据，这里添加数据量为100000
        List<Integer> list = Lists.newArrayList();
        for (int i = 1; i < 100000; i++) {
            list.add(i);
        }

        //切割起始时间
        long startTime = System.currentTimeMillis();

        //这里是存放分割后的数据，例如："1,2,3"
        List<String> result = Lists.newArrayList();
        int i = 0;
        StringBuilder sb = new StringBuilder();
        for (Integer data : list) {
            i++;

            if (i % 3 == 0) {
                sb.append(data);
                result.add(sb.toString());
                sb = new StringBuilder();
            } else {
                sb.append(data).append(",");
            }
        }

        for (String s : result) {
            System.out.println(s);
        }
        System.out.println(String.format("处理完所有数据共耗时：%s 毫秒！", System.currentTimeMillis() - startTime));
    }
}
