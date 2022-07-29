package com.cjh.test;

import com.cjh.juc.ThreadTest;
import org.junit.Test;

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
}
