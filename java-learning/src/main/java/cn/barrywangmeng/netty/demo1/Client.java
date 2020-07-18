package cn.barrywangmeng.netty.demo1;

import java.io.IOException;
import java.net.Socket;

/**
 * Client端程序
 * @author wangmeng2
 * @date 2020/7/17 6:53
 */
public class Client {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8000;
    private static final int SLEEP_TIME = 5000;

    public static void main(String[] args) throws IOException {
        final Socket socket = new Socket(HOST, PORT);

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("客户端启动成功！");
                while (true) {
                    try {
                        String message = "hello world";
                        System.out.println("客户端发送数据：" + message);
                        socket.getOutputStream().write(message.getBytes());
                    } catch (IOException e) {
                        System.out.println("写数据出错了！");
                    }

                    sleep();
                }
            }
        }).start();
    }

    private static void sleep() {
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}