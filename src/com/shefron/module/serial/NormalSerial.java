package com.shefron.module.serial;

import java.io.Serializable;

/**
 * Created by Administrator on 2014/11/29.
 */
public class NormalSerial implements Serializable{

    /** 序列化版本标志 */
    private static final long serialVersionUID = 20141129L;

    private static int count;//用于计算对象个数

    private static final int MAX_COUNT = 1000;

    private String name;

    /** 不会对其反序列化*/
    private transient String password;

    static {
        System.out.println("调用NormalSerial的静态代码段");
    }

    /** 反序列化不会调用任何构造方法 */
    public NormalSerial(){
        System.out.println("调用构造方法NormalSerial()");
        count++;
    }

    public NormalSerial(String name, String password){
        System.out.println("调用有参数构造NormalSerial方法");
        this.name = name;
        this.password = password;
        this.count ++;
    }

    public String toString(){
        return "count="+count
                +"\tMAX_COUNT="+MAX_COUNT
                +"\tname="+name
                +"\tpassword="+password;
    }




}
