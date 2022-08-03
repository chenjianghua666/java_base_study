package com.cjh.juc;

import java.util.concurrent.locks.LockSupport;

/**
 * @ClassName ParkTest
 * @Description
 * @Author Administrator
 * @Date 2022/8/3 16:33
 * @Version 1.0
 */
public class ParkTest {
    public static void main(String[] args) {
        MythreadPark mythreadPark = new MythreadPark(Thread.currentThread());
        mythreadPark.start();
        System.out.println("before park");
        LockSupport.park("ParkTestDemo"); // �����߳�
        System.out.println("after park");
    }
}


class MythreadPark extends Thread {
    private Object object;

    public MythreadPark(Object object) {
        this.object = object;
    }

    @Override
    public void run() {
        super.run();
        System.out.println(" before interrupt");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Thread thread = (Thread)object;
        thread.interrupt(); // �ͷ���Դ, ֹͣ����
        System.out.println("after interrupt");
    }
}