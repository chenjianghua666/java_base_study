package com.cjh.test;

import com.cjh.juc.ThreadTest;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName TestThread
 * @Description
 * @Author Administrator
 * @Date 2022/7/29 10:46
 * @Version 1.0
 */

public class TestThread {
    @Test
    public void  a() {
        System.out.println("this is a test method");
    }

    @Test
    public void testOneThread() {
        ThreadTest threadTest = new ThreadTest();
        threadTest.run();
    }

    @Test
    public void testUnsafeThread() {

    }
}
