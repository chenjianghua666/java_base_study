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
     * ���������ܻ���� b=3, a =1�Ľ��,�߳��޸�A�����Ժ���ȴ���cpu���߻�����,����print�̲߳����ɼ����Զ�����ֵ��1�����Ѿ�
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

    volatile int num; // �޷���֤ԭ����
    volatile long l; // long �� double ��Ϊ��32�͵�32������,�����ͨ��long��double���Ͷ��ӿ��ܲ���ԭ����
    volatile double d;
    AtomicInteger num2 = new AtomicInteger(0); // ԭ���Բ���
    public void addNum() {
        synchronized (this){ // ����ʹ�ü���ȥ��֤ԭ���� ����ʹ�� AtomicInteger
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
