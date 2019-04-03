package com.springboot.project.exception;

import com.springboot.project.utils.R;
//自定义异常
public class RZException extends RuntimeException {
    public RZException(){}
    public RZException(String msg){
        super(msg);
    }
    public RZException(String msg,Throwable throwable){
        super(msg,throwable);
    }
}
