package com.cjh.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName UnsafeThreadTest
 * @Description
 * @Author Administrator
 * @Date 2022/8/2 18:39
 * @Version 1.0
 */
public class UnsafeThreadTest {
    public static void main(String[] args) throws InterruptedException {
        Ticket ticket = new Ticket();
        final int threadSize = 100;
        final CountDownLatch countDownLatch = new CountDownLatch(threadSize);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < threadSize; i++) {
            executorService.execute(() -> {
                if (ticket.get() > 0) {
                    ticket.sale();
                }
                System.out.println(ticket.get());
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();

    }
}

class Ticket {
    private int total = 100; // ×Ü¹²100ÕÅ
    public void sale() {
        total --;
    }

    public int get() {
        return total;
    }
}