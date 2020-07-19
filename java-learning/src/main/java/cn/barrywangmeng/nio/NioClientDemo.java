package cn.barrywangmeng.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * NioClientDemo
 *
 * @author wangmeng
 * @date 2020/7/19 23:00
 */
public class NioClientDemo {

    private SocketChannel clientSocketChannel;
    private Selector selector;
    private List<String> responseList = new ArrayList<>();

    private CountDownLatch connected = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        NioClientDemo client = new NioClientDemo();
        for (int i = 0; i < 30; i++) {

            client.send("hello world: " + i);
            Thread.sleep(1000L);
        }
    }

    public NioClientDemo() throws Exception {
        clientSocketChannel = SocketChannel.open();
        clientSocketChannel.configureBlocking(false);

        selector = Selector.open();
        clientSocketChannel.register(selector, SelectionKey.OP_CONNECT);
        clientSocketChannel.connect(new InetSocketAddress(9000));

        new Thread(() -> {
            try {
                handleKeys();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        if (connected.getCount() != 0) {
            connected.await();
        }

        System.out.println("Client 启动完成！");
    }

    private void handleKeys() throws IOException {
        while (true) {
            // 通过Selector选择Channel
            selector.select();

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                handleKey(key);
            }
        }
    }

    private synchronized void handleKey(SelectionKey key) throws IOException {
        // 连接就绪
        if (key.isConnectable()) {
            handleConnectableKey(key);
        }

        // 读就绪
        if (key.isReadable()) {
            handleReadableKey(key);
        }

        // 写就绪
        if (key.isWritable()) {
            handleWritableKey(key);
        }
    }

    private synchronized void send(String content) throws ClosedChannelException {
        responseList.add(content);
        System.out.println("Client端send()写入数据： " + content);

        clientSocketChannel.register(selector, SelectionKey.OP_WRITE, responseList);
        // 唤醒selector
        selector.wakeup();
    }

    private void handleWritableKey(SelectionKey key) throws IOException {
        SocketChannel clientSocketChannel = (SocketChannel) key.channel();
        List<String> responseList = (ArrayList<String>) key.attachment();

        for (String content : responseList) {
            System.out.println("Client端写入数据：" + content);
            this.write(clientSocketChannel, content);
        }

        responseList.clear();
        clientSocketChannel.register(selector, SelectionKey.OP_READ, responseList);
    }

    private void handleReadableKey(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer readBuffer = this.read(channel);

        if (readBuffer.position() > 0) {
            String content = this.newString(readBuffer);
            System.out.println("Client端读取数据： " + content);
        }
    }

    private void handleConnectableKey(SelectionKey key) throws IOException{
        if (!clientSocketChannel.isConnectionPending()) {
            return;
        }

        clientSocketChannel.finishConnect();
        System.out.println("Client端完成连接...");
        clientSocketChannel.register(selector, SelectionKey.OP_READ, responseList);
        // 标记为已连接 countDown唤醒主线程
        connected.countDown();
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