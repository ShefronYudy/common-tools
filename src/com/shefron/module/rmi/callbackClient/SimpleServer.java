package com.shefron.module.rmi.callbackClient;

import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * Created by Administrator on 2014/12/1.
 */
public class SimpleServer {

    public static void main(String[] args){
        try {
            Context context = new InitialContext();
            StockQuoteRegistryImpl registry = new StockQuoteRegistryImpl();

            context.rebind("rmi://localhost:1099/registerStockQuote",registry);

            System.out.println("服务器注册了一个远程对象");

            new Thread(registry).start();

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

}
