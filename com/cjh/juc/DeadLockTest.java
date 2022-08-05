package com.cjh.juc;

import java.io.ObjectInput;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName DeadLockTest
 * @Description
 * @Author Administrator
 * @Date 2022/8/4 16:31
 * @Version 1.0
 */
public class DeadLockTest {
    private static String A = "a";
    private static String B =  "b";

    public static void main(String[] args) {
        deadLock();
    }

    private static void deadLock() {
        Thread t1 = new Thread(() -> {
            synchronized (A) {
                System.out.println("A ÒÑ±»1Ëø×¡");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (B) {
                    System.out.println("1");
                }
            }
        });
        Lock lock = new ReentrantLock();

        Thread t2 = new Thread(() -> {
            try {
                if (lock.tryLock(100, TimeUnit.SECONDS)) {
                    System.out.println(B);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        });

        t1.start();
        t2.start();
    }
}
