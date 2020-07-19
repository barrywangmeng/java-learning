package cn.barrywangmeng.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Nio Server demo
 *
 * @author wangmeng
 * @date 2020/7/19 19:08
 */
public class NioServerDemo {

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;

    public static void main(String[] args) throws IOException {
        NioServerDemo server = new NioServerDemo();
    }

    public NioServerDemo() throws IOException {
        // 创建Channel并且配置为非阻塞
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        // 绑定端口号
        serverSocketChannel.socket().bind(new InetSocketAddress(9000));

        // 创建Selector
        selector = Selector.open();
        // 将channel注册到selector中
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("NioServer 启动完成");

        handleKeys();
    }

    private void handleKeys() throws IOException {
        while (true) {
            int selectNums = selector.select();
            System.out.println("就绪的Channel数量：" + selectNums);

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                handleKey(key);
            }
        }
    }

    private void handleKey(SelectionKey key) throws IOException {
        if (key.isAcceptable()) {
            handleAcceptableKey(key);
        }
        if (key.isReadable()) {
            handleReadableKey(key);
        }
        if (key.isWritable()) {
            handleWritableKey(key);
        }
    }

    private void handleAcceptableKey(SelectionKey key) throws IOException {
        // 接收Client端SocketChannel
        SocketChannel clientSocketChannel = ((ServerSocketChannel) key.channel()).accept();
        clientSocketChannel.configureBlocking(false);
        System.out.println("接收到新的Client端Channel...");

        clientSocketChannel.register(selector, SelectionKey.OP_READ, new ArrayList<String>());
    }

    private void handleReadableKey(SelectionKey key) throws IOException {
        SocketChannel clientSocketChannel = (SocketChannel) key.channel();
        // 读取数据
        ByteBuffer readBuffer = this.read(clientSocketChannel);
        if (readBuffer == null) {
            System.out.println("断开 Channel...");

            clientSocketChannel.register(selector, 0);
        }

        // 打印数
        if (readBuffer.position() > 0) {
            String content = this.newString(readBuffer);
            System.out.println("读取数据： " + content);

            // 添加数据到list中
            List<String> responseList = (ArrayList<String>) key.attachment();
            responseList.add("响应： " + content);
            // 注册ClientSocketChannel到selector上
            clientSocketChannel.register(selector, SelectionKey.OP_WRITE, key.attachment());
        }
    }

    private void handleWritableKey(SelectionKey key) throws ClosedChannelException {
        SocketChannel clientSocketChannel = (SocketChannel) key.channel();

        ArrayList<String> responseAttachment = (ArrayList<String>) key.attachment();
        for (String content : responseAttachment) {
            System.out.println("写入数据：" + content);
            this.write(clientSocketChannel, content);
        }

        responseAttachment.clear();
        clientSocketChannel.register(selector, SelectionKey.OP_READ, responseAttachment);
    }

    private void write(SocketChannel channel, String content) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(content.getBytes("UTF-8"));

            // 切换模式，将写模式切换成读模式，读取buffer中的数据写入channel
            buffer.flip();
            channel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ByteBuffer read(SocketChannel channel) throws IOException{
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 将channel中数据写入buffer
        int count = channel.read(buffer);
        if (count == -1) {
            return null;
        }

        return buffer;
    }

    private String newString(ByteBuffer buffer) throws IOException{
        buffer.flip();
        byte[] bytes = new byte[buffer.remaining()];
        System.arraycopy(buffer.array(), buffer.position(), bytes, 0, buffer.remaining());
        return new String(bytes, "UTF-8");
    }
}