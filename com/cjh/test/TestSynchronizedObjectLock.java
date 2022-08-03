package com.cjh.test;

import org.junit.Test;

/**
 * @ClassName TestSynchronizedObectLock
 * @Description
 * @Author Administrator
 * @Date 2022/8/3 10:37
 * @Version 1.0
 */
//public class TestSynchronizedObjectLock{
//    @Test
//    public  void  testSynchronizedLock() {
//        SynchronizedObjectLockTest synchronizedObjectLockTest = new SynchronizedObjectLockTest();
//        Thread t1 = new Thread(synchronizedObjectLockTest);
//        Thread t2 = new Thread(synchronizedObjectLockTest);
//        t1.start();
//        t2.start();
//    }
//}

public class TestSynchronizedObjectLock implements Runnable {
    static TestSynchronizedObjectLock instence = new TestSynchronizedObjectLock();

    Object object1 = new Object();
    Object object2 = new Object();

    @Override
    public void run() {
        synchronized (object1) {
            System.out.println("Current Thread is "+ Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " end");
        }

        synchronized (object2) {

            System.out.println("Current Thread is "+ Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " end");
        }
    }
    public static void main(String[] args) {
        Thread t1 = new Thread(instence);
        Thread t2 = new Thread(instence);
        t1.start();
        t2.start();
    }
    
}
