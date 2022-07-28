package com.cjh.collection;

import java.util.LinkedList;

/**
 * @ClassName LinkedListTest
 * @Description
 * @Author Administrator
 * @Date 2022/7/28 17:53
 * @Version 1.0
 */
public class LinkedListTest {

    public static void main(String[] args) {
        LinkedList<String> strings = new LinkedList<>();
        strings.add("大公鸡");
        strings.add("老母鸡");
        LinkedList<String> strings1 = new LinkedList<>(strings);
        long start = System.currentTimeMillis();
        for (int i=0; i<100000; i++) {
            strings.add(i + "");
        }
        System.out.println(System.currentTimeMillis() - start);
        start = System.currentTimeMillis();
//        for (String str: strings) {
//        }
//        System.out.println( "增强型 \t" + (System.currentTimeMillis() - start));

        for (int i = 0; i < strings.size(); i++) {
            strings.get(i);
        }

        System.out.println( "普通型 \t" + (System.currentTimeMillis() - start));
    }

}
