package com.shefron.module.rmi.dgc;

import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * Created by Administrator on 2014/12/2.
 */
public class SimpleClient {

    public static void main(String[] args){
        try{

            Context naming = new InitialContext();

            HelloService helloService1 = (HelloService)naming.lookup("rmi://localhost:1099/helloService");
            helloService1.access();

            Thread.sleep(10*1000);
            helloService1.bye();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
