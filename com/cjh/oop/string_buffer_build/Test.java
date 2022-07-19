package com.cjh.oop.string_buffer_build;

public class Test {


    public static void main(String[] args) {
        StringBuffer stringBuffer = new StringBuffer();
        String name = "xiaohua"; // 不可变的
        stringBuffer.append(name);

        System.out.println(stringBuffer.toString());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name);
        stringBuilder.append("jaja");
        System.out.println(stringBuilder.toString());


        Integer num1 = 100;
        Integer num2 = 100;

        Integer num3 = 200;
        Integer num4 = 200;

        System.out.println(num1 == num2); // true
        System.out.println(num3 == num4); // false
    }

}
