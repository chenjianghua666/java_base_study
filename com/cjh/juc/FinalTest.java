package com.cjh.juc;

/**
 * @ClassName FinalTest
 * @Description
 * @Author Administrator
 * @Date 2022/8/3 15:39
 * @Version 1.0
 */
public class FinalTest {
    private int a;
    private final int b;
    private static  FinalTest finalTest;

    public  FinalTest(){
        a = 1;
        b = 2;
    }

    public static void writer() {
        finalTest = new FinalTest();
    }

    public static void read() {
        FinalTest demo = finalTest;
        int a = demo.a;
        int b = demo.b;
        System.out.println(a);
        System.out.println(b);
    }

    public static void main(String[] args) {
        new Thread(() -> {
            writer();
        }).start();

        new Thread(() -> {
            read();
        }).start();
    }
}
