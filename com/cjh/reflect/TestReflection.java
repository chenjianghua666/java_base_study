package com.cjh.reflect;

import java.lang.reflect.InvocationTargetException;

/**
 * @ClassName TestReflection
 * @Description �������ѧϰ
 * @Author Administrator
 * @Date 2022/7/27 15:14
 * @Version 1.0
 */
public class TestReflection implements face {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // ��ȡ���������ַ���
        System.out.println("��������: \t" + User.class);
        System.out.println("���ݶ���: \t" + new User().getClass());
        System.out.println("����ȫ�޶�����: \t" + Class.forName("com.cjh.reflect.User"));

        Class<User> userClass = User.class;
        // ���÷���
        System.out.println("��ȡȫ�޶�����: \t" + userClass.getName());
        System.out.println("��ȡ����: \t" + userClass.getSimpleName());
        System.out.println("ʵ����: \t" +  userClass.getDeclaredConstructor().newInstance());
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