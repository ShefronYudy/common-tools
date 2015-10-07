package com.shefron.module.rmi.callbackClient;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Administrator on 2014/12/1.
 */
public class StockQuoteImpl extends UnicastRemoteObject implements StockQuote {


    public StockQuoteImpl() throws RemoteException {
    }

    @Override
    public void quote(String stockSymbol, double price) throws RemoteException {
        System.out.println(stockSymbol+":"+price);
    }
}
