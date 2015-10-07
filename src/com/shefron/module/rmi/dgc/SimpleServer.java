package com.shefron.module.rmi.dgc;

import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * Created by Administrator on 2014/12/2.
 */
public class SimpleServer {

    public static void main(String[] args){
        try{
            System.setProperty("java.rmi.dgc.leaseValue","3000");

            Context naming = new InitialContext();
            HelloService helloService = new HelloServiceImpl();



            naming.rebind("rmi://localhost:1099/helloService",helloService);
            System.out.println("服务器绑定一个helloService");
            Thread.sleep(1000);

            naming.unbind("rmi://localhost:1099/helloService");

            System.out.println("服务器注销一个helloService ");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
