package com.shefron.module.rmi.callbackClient;


import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Administrator on 2014/12/1.
 */
    public interface StockQuote extends Remote {

    public void quote(String stockSymbol, double price) throws RemoteException;

}
