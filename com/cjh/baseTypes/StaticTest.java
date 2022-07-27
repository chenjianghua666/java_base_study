package com.cjh.baseTypes;

import java.io.OutputStream;

/**
 * @ClassName StaticTest
 * @Description
 * @Author Administrator
 * @Date 2022/7/27 15:04
 * @Version 1.0
 */
public class StaticTest {
    static Integer number = 10;
    static {
        number = 11;
        System.out.println("static method block run");
    }

    public static void main(String[] args) {
        System.out.println(number);
        System.out.println(OutStaticClass.args);
        OutStaticClass outStaticClass = new OutStaticClass();
        outStaticClass.start();
    }
}
