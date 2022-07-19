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

        String strValue1 = "haha"; // 创建的对象放到string pool中
        String strValue2 = strValue1; // 引用的是同一个地址
        String strValue3 = new String("haha"); // 会创建一个新的对象
        String strValue4 = strValue3.intern(); // 引用同一个地址
        System.out.println("====>" + (strValue1 == strValue2)+ "===>"  + (strValue1 == strValue3)); // true ===> false
        // intern 先将指向的对象存入pool中然后再返回这个对象的引用, strValue3.intern()调用的pool引用的对象与strValue4指向的是同一个引用
        System.out.println(strValue3.intern() == strValue4);
        System.out.println(strValue4.intern());
    }

}
