package com.cjh.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Objects;

/**
 * @ClassName NioDemon
 * @Description
 * @Author Administrator
 * @Date 2022/8/31 15:58
 * @Version 1.0
 */
public class NioDemon {

    public  static  void fastCopy(String src, String dist) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(src);
        FileChannel fcin = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream(dist);
        FileChannel fcout = fileOutputStream.getChannel();

        // 缓冲区分配1024个字节
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        try {
            while (true) {
                int r = fcin.read(buffer);
                if (r == -1) {
                    break;
                }
                // exchange read and write
                buffer.flip();

                // write buffer to file
                fcout.write(buffer);

                buffer.clear();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println(Objects.requireNonNull(NioDemon.class.getResource("/")).getPath()+"doc");
            String src = Objects.requireNonNull(NioDemon.class.getResource("/")).getPath()+"doc/juc.md";
            String dst = Objects.requireNonNull(NioDemon.class.getResource("/")).getPath()+"doc/juc_copy.md";
            fastCopy(src, dst);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
