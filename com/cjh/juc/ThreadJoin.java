package com.cjh.juc;

/**
 * @ClassName ThreadJoin
 * @Description
 * @Author Administrator
 * @Date 2022/8/4 16:16
 * @Version 1.0
 */
public class ThreadJoin {
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        int count = 100000000;
        Thread thread = new Thread(() -> {
            int a = 0;
            for (int i = 0; i < count; i++) {
                a++;
            }
        });
        thread.start();
        int b = 0;
        for (int i = 0; i < count; i++) {
            b--;
        }
        long time = System.currentTimeMillis() - start;
        thread.join(); // 等待线程执行再执行下面的代码
        System.out.println("concurrentcy: " + time + "ms, b =" + b);

        new Thread(() -> {
            System.out.println("thread2 start");
        }).start();
    }
}

