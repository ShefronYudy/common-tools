package com.shefron.module.serial;

import java.io.*;
import java.util.Properties;

/**
 * 单例反序列化的不唯一实现
 * Created by Administrator on 2014/11/29.
 */
public class NormalSerial3 implements Serializable{

    private static final NormalSerial3 instance = new NormalSerial3();
    private Properties properties = new Properties();

    private NormalSerial3(){
        try {
            InputStream is = getClass().getResourceAsStream("myapp.properties");
            properties.load(is);
            is.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static NormalSerial3 getInstance(){
        return instance;
    }

    /**
     * 决定了经过反序列化最后返回的对象，
     * 从而实现返回的对象是单例（同一个）
     * @return
     * @throws ObjectStreamException
     */
    private Object readResolve() throws ObjectStreamException{
        return instance;
    }

    public Properties getProperties(){
        return properties;
    }





}
