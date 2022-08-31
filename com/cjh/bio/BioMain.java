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

        //���wait���漰�������ʵ���߼���ֻ��Ϊ�˱�֤�ػ��߳������������̺߳󣬽���ȴ�״̬
        synchronized (BioMain.class) {
            BioMain.class.wait();
        }

    }
}
