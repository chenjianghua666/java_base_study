package com.cjh.oop;

/**
 * @ClassName Person
 * @Description
 * @Author Administrator
 * @Date 2022/7/18 19:15
 * @Version 1.0
 */
public class Person {
    public String name = "hello word"; //  对所有公开, 不管是当前包还是当前包以外的地方都能访问
    private String age = "18"; //  只能再当前进行使用,继承也不行
    protected Integer sex = 1; // 继承可以使用,当前再当前类的包以外是不能访问使用的
    final String address = "wuhan";
}
