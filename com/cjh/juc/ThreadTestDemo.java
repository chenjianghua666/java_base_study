package com.cjh.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName Mst
 * @Description
 * @Author Administrator
 * @Date 2022/8/3 19:25
 * @Version 1.0
 */
public class ThreadTestDemo {
    private final List<Integer> list = new ArrayList<>();

    public void add(Integer i) {
        list.add(i);
    }

    public int size() {
        return this.list.size();
    }

    public static void main(String[] args) {
        ThreadTestDemo threadTestDemo = new ThreadTestDemo();
        Object lock = new Object(); // ����һ��������

        // ����һ���߳�,��ȡ����
        Thread T1 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("T1 start");
                if (threadTestDemo.size() != 5) {
                    try {
                        lock.wait(); // ���߳�����,����ȴ�״̬
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("T1 end" + threadTestDemo.list.size());
                lock.notify(); // ֪ͨ�����̼߳���ִ��
            }
        });
        Thread T2 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("T2 start");
                for (int i = 0; i < 10; i++) {
                    threadTestDemo.add(i);
                    if (i == 5) {
                        lock.notify();
                        try {
                            lock.wait(); // ����ȴ�,��ֹ�����̼߳��������ݽ����޸�
                            System.out.println("T2 ����ִ��");
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            System.out.println("T2 end " + threadTestDemo.list.size());
        });

        T2.start();
        T1.start();
    }
}
