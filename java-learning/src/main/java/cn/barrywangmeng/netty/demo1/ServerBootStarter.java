package cn.barrywangmeng.netty.demo1;

/**
 * @author wangmeng2
 * @date 2020/7/17 7:01
 */
public class ServerBootStarter {

    private static final int PORT = 8000;

    public static void main(String[] args) {
        Server server = new Server(PORT);
        server.start();
    }
}