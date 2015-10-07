package com.shefron.module.rmi.concurrency;

import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * Created by Administrator on 2014/12/1.
 */
public class SimpleServer {

    public static void main(String[] args){
        try{
            Context namingContext = new InitialContext();


            Stack stack = new StackImpl("a Stack");

            namingContext.rebind("rmi://localhost:1099/stack",stack);

            System.out.println("服务器注册了 a stack");



        }catch (Exception e){
            e.printStackTrace();
        }






    }
}
