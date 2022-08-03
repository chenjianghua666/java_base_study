package com.cjh.juc;

import org.junit.Test;

/**
 * @ClassName MemoryBarrierTest
 * @Description
 * @Author Administrator
 * @Date 2022/8/3 15:22
 * @Version 1.0
 */
public class MemoryBarrierTest {
    // hsdis jitwatch 进行反编译得到汇编代码
    private volatile int a;
    public void update() {
        a = 1;
    }

    @Test
    public void testMethod() {
        MemoryBarrierTest memoryBarrierTest = new MemoryBarrierTest();
        memoryBarrierTest.update();
    }
}
