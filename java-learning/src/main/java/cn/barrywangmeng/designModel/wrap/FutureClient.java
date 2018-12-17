package cn.barrywangmeng.designModel.wrap;

/**
 * @Description:
 * @Author: wangmeng
 * @Date: 2018/12/17-21:02
 */
public class FutureClient {
    public Data request(final String queryStr) {
        FutureData futureData = new FutureData();

        //异步起一个线程去进行相应的操作
        new Thread(new Runnable() {
            @Override
            public void run() {
                //需要把请求参数设置到真实的数据处理对象中
                RealData realData = new RealData(queryStr);

                //真是请求处理完成 以后，我们要进行设置结果给包装类
                futureData.setRealData(realData);
            }
        }).start();

        return futureData;
    }
}
