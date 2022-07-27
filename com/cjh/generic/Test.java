package com.cjh.generic;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Test
 * @Description
 * @Author Administrator
 * @Date 2022/7/27 16:51
 * @Version 1.0
 */
public class Test {
    // <T extends  Number> ָ���������ͱ�֤���Ͱ�ȫ
    private  static  <T extends  Number> double add(T a, T b) {
        return a.doubleValue() + b.doubleValue();
    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // ʹ�÷���������������
        List<String> list  = new ArrayList<>();
        list.add("a");
        list.add("10");
        for (String s : list) {
            System.out.println(s);
        }
        // ͨ���෺��ʵ��ͬһ���������ݲ�ͬ�����ȥ��������
        Person<String> person = new Person<>();

        // ��֤���Ͳ���
//        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();
        // ͨ������ȥ���ö������ӷ���,���Աܿ���������
        list2.getClass().getMethod("add", Object.class).invoke(list2, "hallo");

        for (int i = 0; i < list2.size(); i++) {
            System.out.println(list2.get(i));
        }
    }
}

class Person<T> implements Car<T>{ // �˴��������д��ʶ
    private T val; // ���ⲿָ��
    public T getVal() {
        return val;
    }

    @Override
    public  void driver() {
        System.out.println('d');;
    }
}

interface Car<T> {
    public  void driver();
}




