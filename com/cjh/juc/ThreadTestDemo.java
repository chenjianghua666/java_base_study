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
        Object lock = new Object(); // 创建一个锁对象

        // 创建一个线程,读取数据
        Thread T1 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("T1 start");
                if (threadTestDemo.size() != 5) {
                    try {
                        lock.wait(); // 让线程阻塞,进入等待状态
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("T1 end" + threadTestDemo.list.size());
                lock.notify(); // 通知其他线程继续执行
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
                            lock.wait(); // 进入等待,防止继续线程继续对数据进行修改
                            System.out.println("T2 继续执行");
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
