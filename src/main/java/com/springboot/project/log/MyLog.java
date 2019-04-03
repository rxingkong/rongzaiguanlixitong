package com.springboot.project.log;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MyLog {
    String value();//记录方法的功能
    String  description();//详细的描述方法
}
