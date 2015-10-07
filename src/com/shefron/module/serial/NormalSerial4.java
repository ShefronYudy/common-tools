package com.shefron.module.serial;

import java.io.*;
import java.util.Properties;

/**
 * 实现Externallizable接口实现序列化
 * Created by Administrator on 2014/11/29.
 */
public class NormalSerial4 implements Externalizable{

    private String name;

    static {
        System.out.println("NormalSerial4 的静态代码块执行");
    }

    /**
     * 实现了Externalizable接口的对象序列化需要默认的构造函数
     */
    public NormalSerial4() {
        System.out.println("执行了默认构造方法NormalSerial4()");
    }

    public NormalSerial4(String name){
        this.name = name;
        System.out.println("执行有参数的构造方法NormalSerial4(String name)");
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);

    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String)in.readObject();
    }


}
