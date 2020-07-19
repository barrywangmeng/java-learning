package cn.barrywangmeng.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * FileDemo 测试
 *
 * @author wangmeng
 * @date 2020/6/3 10:35
 */
public class FileChannelDemo {

    public static void main(String[] args) throws Exception{
        FileOutputStream out = new FileOutputStream("D:\\tmp\\hello.txt");
        FileChannel channel = out.getChannel();

        ByteBuffer buffer = ByteBuffer.wrap("hello world".getBytes());
        channel.write(buffer);

        channel.close();
        out.close();
    }
}
