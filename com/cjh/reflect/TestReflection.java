package com.cjh.reflect;

import java.lang.reflect.InvocationTargetException;

/**
 * @ClassName TestReflection
 * @Description 反射测试学习
 * @Author Administrator
 * @Date 2022/7/27 15:14
 * @Version 1.0
 */
public class TestReflection implements face {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 获取类对象的三种方法
        System.out.println("根据类名: \t" + User.class);
        System.out.println("根据对象: \t" + new User().getClass());
        System.out.println("根据全限定类型: \t" + Class.forName("com.cjh.reflect.User"));

        Class<User> userClass = User.class;
        // 常用方法
        System.out.println("获取全限定类名: \t" + userClass.getName());
        System.out.println("获取类名: \t" + userClass.getSimpleName());
        System.out.println("实例化: \t" +  userClass.getDeclaredConstructor().newInstance());
        User user1 = userClass.getDeclaredConstructor().newInstance();

        System.out.println(user1.getName());
    }

    @Override
    public void say() {
        System.out.println("say hello");
    }
}

interface face {
    String name = "name is string value";
    void say();
}