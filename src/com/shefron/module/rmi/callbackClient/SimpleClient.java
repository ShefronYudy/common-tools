package com.shefron.module.rmi.callbackClient;

import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * Created by Administrator on 2014/12/1.
 */
public class SimpleClient {

    public static void main(String [] args){
        try {
            Context namingContext = new InitialContext();

            StockQuoteRegistry registry = (StockQuoteRegistry)namingContext.lookup("rmi://localhost:1099/registerStockQuote");

            StockQuote client = new StockQuoteImpl();

            registry.registerClient(client);

            Thread.sleep(5*1000);

            registry.unregisterClient(client);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
