package com.cjh.collection;

import java.util.ArrayList;

/**
 * @ClassName Test
 * @Description
 * @Author Administrator
 * @Date 2022/7/28 10:31
 * @Version 1.0
 */
public class Test {

    public static void main(String[] args) throws IndexOutOfBoundsException {
        ArrayList<Integer> objects = new ArrayList<>();
        objects.add(1);
        Integer number = objects.get(0);
        objects.remove(0);
        System.out.println(number);
    }
}
