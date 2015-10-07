package com.shefron.module.refletion;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2014/11/29.
 */
public class DumpMethod {

    public static void main(String[] args) {
        try {
            Class classType = Class.forName(args[0]);

            // will private,public,protected,default
            Method methods [] = classType.getDeclaredMethods();

            for(Method method : methods)
                System.out.println(method.toString());


        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
