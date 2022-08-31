package com.cjh.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName SocketServer1
 * @Description
 * @Author Administrator
 * @Date 2022/8/31 14:42
 * @Version 1.0
 */
public class SocketServer1 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(888);
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();
                Integer sourcePort = socket.getPort();
                int maxLen = 1024;
                byte[] contextBytes = new byte[maxLen];
                int realLen = in.read(contextBytes, 0, maxLen);

                // read message
                out.write(("this is from server" + sourcePort).getBytes());
                out.close();
                in.close();
                socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
    }
}
