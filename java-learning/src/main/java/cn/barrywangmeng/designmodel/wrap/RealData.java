package cn.barrywangmeng.designmodel.wrap;

import java.util.concurrent.TimeUnit;

/**
 * @Description:真实数据类
 * @Author: wangmeng
 * @Date: 2018/12/17-21:01
 */
public class RealData implements Data {

    private String result;
    public RealData(String queryStr) {
        System.out.println("根据查询参数：" + queryStr + "进行查询数据库的操作");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        result = "查询结果：100条数据";
    }

    @Override
    public String getRequest() {
        return result;
    }
}
