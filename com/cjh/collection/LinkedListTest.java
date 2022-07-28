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
        strings.add("�󹫼�");
        strings.add("��ĸ��");
        LinkedList<String> strings1 = new LinkedList<>(strings);
        long start = System.currentTimeMillis();
        for (int i=0; i<100000; i++) {
            strings.add(i + "");
        }
        System.out.println(System.currentTimeMillis() - start);
        start = System.currentTimeMillis();
//        for (String str: strings) {
//        }
//        System.out.println( "��ǿ�� \t" + (System.currentTimeMillis() - start));

        for (int i = 0; i < strings.size(); i++) {
            strings.get(i);
        }

        System.out.println( "��ͨ�� \t" + (System.currentTimeMillis() - start));
    }

}
