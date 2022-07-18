package com.cjh.oop;

import com.cjh.oop.package1.Person;

/**
 * @ClassName study
 * @Description 面向对象
 * @Author Administrator
 * @Date 2022/7/18 14:09
 * @Version 1.0
 */
public class Application {
    public static void main(String[] args) {
        Person person = new Person();
        System.out.println(person.name);
    }
}
