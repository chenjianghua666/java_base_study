package com.cjh.juc;

/**
 * @ClassName ThreadTest
 * @Description
 * @Author Administrator
 * @Date 2022/7/29 10:32
 * @Version 1.0
 */
public class ThreadTest implements Runnable {
    @Override
    public void run() {
        System.out.println("thread running");
    }
}

