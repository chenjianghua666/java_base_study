package com.cjh.juc;

/**
 * @ClassName LockSupportTest
 * @Description
 * @Author Administrator
 * @Date 2022/8/3 16:15
 * @Version 1.0
 */
public class LockSupportTest {

    public static void main(String[] args) throws InterruptedException {
        MythreadPark myThread = new MythreadPark(Thread.currentThread());
        synchronized (myThread) {
            myThread.start();
            Thread.sleep(3000); // 阻塞线程
            System.out.println("before wait");
            myThread.wait(); // 释放锁 阻塞主状态 等待调用notify(), notify()方法必须要在wait之前调用,要不然线程会一直处于阻塞状态
            System.out.println("after wait");
        }
    }
}

class MyThread extends Thread {
    @Override
    public void run() {
        super.run();
        synchronized (this) {
            System.out.println("before notify");
//            notify();
            System.out.println("after notify");
        }
    }
}



