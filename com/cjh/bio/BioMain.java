package com.cjh.bio;

import java.util.concurrent.CountDownLatch;

/**
 * @ClassName BioMain
 * @Description
 * @Author Administrator
 * @Date 2022/8/31 10:44
 * @Version 1.0
 */
public class BioMain {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("BIO MAIN COMING");
        Integer clientNumber = 20;

        CountDownLatch countDownLatch = new CountDownLatch(clientNumber);

        for (int i = 0; i < clientNumber ; i++, countDownLatch.countDown()) {
            SockertClientRequestThread client = new SockertClientRequestThread(countDownLatch, i);
            new Thread(client).start();
        }

        //这个wait不涉及到具体的实验逻辑，只是为了保证守护线程在启动所有线程后，进入等待状态
        synchronized (BioMain.class) {
            BioMain.class.wait();
        }

    }
}
