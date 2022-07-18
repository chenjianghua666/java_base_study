package com.cjh.oop.package1;

import com.cjh.oop.package1.Person;

/**
 * @ClassName Student
 * @Description
 * @Author Administrator
 * @Date 2022/7/18 19:17
 * @Version 1.0
 */
public class Teacher extends Person {
    @Override
    public String toString() {
        return this.name  + "address" + this.address + "gender" + this.sex;
    }
}
