package com.cjh.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName Test
 * @Description
 * @Author Administrator
 * @Date 2022/7/28 9:26
 * @Version 1.0
 */
public class Test {
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_PARAMETER)
@interface  MyNotEmpty {}