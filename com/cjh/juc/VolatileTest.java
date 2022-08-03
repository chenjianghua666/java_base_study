package com.cjh.juc;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName VolatileTest
 * @Description
 * @Author Administrator
 * @Date 2022/8/3 14:41
 * @Version 1.0
 */
public class VolatileTest {
    /**
     * 这个程序可能会存在 b=3, a =1的结果,线程修改A的属性后会先存在cpu告诉缓存中,对于print线程并不可见所以读到的值是1但是已经
     **/
    int a = 1;
    int b = 2;
    public void change() {
        a = 3;
        b = a;
    }

    void print() {
        System.out.println("a=" + a + "; b="+ b);
    }

    volatile int num; // 无法保证原子性
    volatile long l; // long 和 double 分为高32和低32两部分,因此普通的long和double类型肚子可能不是原子性
    volatile double d;
    AtomicInteger num2 = new AtomicInteger(0); // 原子性操作
    public void addNum() {
        synchronized (this){ // 可以使用加锁去保证原子性 或者使用 AtomicInteger
            num ++;
        }
        num2.incrementAndGet();
    }

    @Test
    public void testAddNum() throws InterruptedException {
        final VolatileTest test = new VolatileTest();
        for (int n=0; n < 1000; n++) {
            new Thread(() -> {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                test.addNum();
            }).start();
        }
        Thread.sleep(10000);
        System.out.println(test.num + "\n" + test.num2);
    }

    @Test
    public void testMethod() {
        while(true) {
            final VolatileTest test = new VolatileTest();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    test.change();
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    test.print();
                }
            }).start();
        }
    }
}
