package com.shefron.module.serial;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Administrator on 2014/11/29.
 */
public class NormalSerial2 implements Serializable{

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
    public NormalSerial2(){
        System.out.println("调用构造方法NormalSerial()");
        count++;
    }

    public NormalSerial2(String name, String password){
        System.out.println("调用有参数构造NormalSerial方法");
        this.name = name;
        this.password = password;
        this.count++;
    }

    public String toString(){
        return "count="+count
                +"\tMAX_COUNT="+MAX_COUNT
                +"\tname="+name
                +"\tpassword="+password;
    }

    /** 字节8位取反 */
    private byte[] change(byte[] buff){
        for(int i=0;i<buff.length;i++){
            int b=0;
            for(int j=0;j<8;j++){
                int bit = (buff[i]>>j & 1)==0?1:0;
                b+=(1<<j)*bit;
            }
            buff[i]=(byte)b;
        }
        return buff;
    }

    /**
     * 该方法为固定，修饰符private不能修改
     * @param objOutput
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream objOutput) throws IOException,ClassNotFoundException{
        objOutput.defaultWriteObject();
        objOutput.writeObject(change(password.getBytes()));
        objOutput.writeInt(count);

    }

    private void readObject(ObjectInputStream objInput) throws IOException,ClassNotFoundException{
        objInput.defaultReadObject();
        byte[] buff = (byte[])objInput.readObject();
        this.password = new String(change(buff));
        this.count = objInput.readInt();


    }



}
