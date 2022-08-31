package com.cjh.nio;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * @ClassName ChannelTest
 * @Description
 * @Author Administrator
 * @Date 2022/8/31 18:46
 * @Version 1.0
 */
public class ChannelTest {
    // 写文件到本地
    @Test
    public void write() {
        try {

            FileOutputStream fileOutputStream = new FileOutputStream("F:\\audio.txt");
            FileChannel channel = fileOutputStream.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            for (int i = 0; i < 10; i++) {
                buffer.clear();
                buffer.put(("hello, 使用buffer和channel实现写数据到文件" + i + "\r\n").getBytes());

                buffer.flip();
                channel.write(buffer);
                System.out.println("写数据到文件中");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 读本地文件
    @Test
    public void read() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("F:\\audio.txt");
        FileChannel channel = fileInputStream.getChannel();

        int bufferSize = 1024*1024;
        ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);


        ByteBuffer bb = ByteBuffer.allocate(1024);

        int bytesRead = channel.read(byteBuffer);

        while (bytesRead != -1) {
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                byte b = byteBuffer.get();
                if (b == 10 || b == 13) {
                    bb.flip();

                    final  String line = Charset.forName("GBK").decode(bb).toString();
                    System.out.println(line);
                    bb.clear();
                }else {
                    if (bb.hasRemaining()) {
                        bb.put(b);
                    }else  {
                        // 空间不足扩容
                        bb = ByteBuffer.allocate(1024*2);
                        bb.put(b);
                    }
                }
            }
            byteBuffer.clear();
            bytesRead = channel.read(byteBuffer);
        }

        channel.close();



    }
}
