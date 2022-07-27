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
    // <T extends  Number> 指定泛型类型保证类型安全
    private  static  <T extends  Number> double add(T a, T b) {
        return a.doubleValue() + b.doubleValue();
    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // 使用泛型限制数组类型
        List<String> list  = new ArrayList<>();
        list.add("a");
        list.add("10");
        for (String s : list) {
            System.out.println(s);
        }
        // 通过类泛型实现同一个参数根据不同的情况去定义类型
        Person<String> person = new Person<>();

        // 验证类型擦除
//        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();
        // 通过反射去调用对象的添加方法,可以避开类型限制
        list2.getClass().getMethod("add", Object.class).invoke(list2, "hallo");

        for (int i = 0; i < list2.size(); i++) {
            System.out.println(list2.get(i));
        }
    }
}

class Person<T> implements Car<T>{ // 此处可以随便写标识
    private T val; // 由外部指定
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




