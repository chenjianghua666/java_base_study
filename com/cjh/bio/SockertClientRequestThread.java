package com.cjh.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName SockertClientRequestThread
 * @Description
 * @Author Administrator
 * @Date 2022/8/31 11:34
 * @Version 1.0
 */
public class SockertClientRequestThread implements Runnable {
    static  {

    }
    private CountDownLatch countDownLatch;

    private  Integer clientIndex; // Thread number

    public SockertClientRequestThread(CountDownLatch countDownLatch, Integer clientIndex) {
        this.clientIndex = clientIndex;
        this.countDownLatch = countDownLatch;
    }
    @Override
    public void run() {
        Socket socket = null;
        OutputStream clientRequest = null;
        InputStream clientResponse = null;

        try {
            socket = new Socket("localhost", 888);
            clientRequest = socket.getOutputStream();
            clientResponse = socket.getInputStream();
            this.countDownLatch.await();
            // send request
            clientRequest.write(("this is " + this.clientIndex + "request").getBytes());
            clientRequest.flush();

            // wait server response
            System.out.println("it's" + this.clientIndex + "wait server response");
            int maxLen = 1024;
            byte[] contextBytes = new byte[maxLen];

            int realLen = 0;
            String message = "";

            message += new String(contextBytes, 0 , realLen);
            System.out.println("request from server" + message);


        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {

            try {
                if (clientRequest != null) {
                    clientRequest.close();
                }
                if (clientResponse != null) {
                    clientResponse.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
