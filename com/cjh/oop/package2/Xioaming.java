package com.cjh.oop.package2;

import com.cjh.oop.Person;

/**
 * @ClassName Xioaming
 * @Description
 * @Author Administrator
 * @Date 2022/7/18 19:29
 * @Version 1.0
 */
public class Xioaming {
    void printMessage() {
        Person person = new Person();
        System.out.println(person.name);
        // protect �ٰ����ⲻ�ܷ���
        // private ֻ���ٵ�ǰ����ʹ��
        System.out.println(person.name);
    }
}

class XiaoHong extends  Person {
    void say() {
        System.out.println(this.sex);
        System.out.println(this.name);
    }
}
