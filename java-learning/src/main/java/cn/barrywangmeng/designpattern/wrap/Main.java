package cn.barrywangmeng.designpattern.wrap;

/**
 * @Description:
 * @Author: wangmeng
 * @Date: 2018/12/17-21:00
 */
public class Main {

    public static void main(String[] args) {
        FutureClient fc = new FutureClient();
        Data request = fc.request("请求参数...");

        System.out.println("做其他相关的业务操作。");

        //获取真实返回结果
        String value = request.getRequest();
        System.out.println(value);
    }
}
