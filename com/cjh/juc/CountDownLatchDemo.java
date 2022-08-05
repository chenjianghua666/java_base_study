package com.cjh.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;

/**
 * @ClassName CountDownLatch
 * @Description
 * @Author Administrator
 * @Date 2022/8/4 10:43
 * @Version 1.0
 */
public class CountDownLatchDemo {
    // 使用CountDownLatch不涉及锁定, Count值为0时当前线程继续执行
    volatile  List<Integer> list = new ArrayList<>();

    public void  add(Integer i) {
        list.add(i);
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) {
        CountDownLatchDemo countDownLatchDemo = new CountDownLatchDemo();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread t1 = new Thread(() -> {
            System.out.println("t1 start");
            if (countDownLatchDemo.size() != 5) {
                try {
                    countDownLatch.await();
                    System.out.println("t2 end");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            System.out.println("t2 start");
            for (int i = 0; i < 10; i++) {
                countDownLatchDemo.add(i);
                if (i == 4) {
                    countDownLatch.countDown();
                }
            }
            System.out.println("t2 end");
        }, "t2");

        t2.start();
        t1.start();

    }
}
