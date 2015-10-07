package com.shefron.module.refletion;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2014/11/29.
 */
public class MethodInvoke {
    public int add(int param1, int param2){
        return (param1+param2);
    }

    public String echo(String msg){
        return "echo:"+msg;
    }

    public static void main(String[] args) throws Exception{

        Object methodInvoke = MethodInvoke.class.newInstance();

        //调用add方法
        Method addMethod = MethodInvoke.class.getDeclaredMethod("add", new Class[]{int.class, int.class});

        Object value = addMethod.invoke(methodInvoke, new Object[]{100, 100});
        System.out.println("add = "+ value);

        //调用echo方法...

    }
}
