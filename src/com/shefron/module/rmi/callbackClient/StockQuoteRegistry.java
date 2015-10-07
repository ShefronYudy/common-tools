package com.shefron.module.rmi.callbackClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Administrator on 2014/12/1.
 */
public interface StockQuoteRegistry extends Remote {

    public void registerClient(StockQuote stockQuote) throws RemoteException;

    public void unregisterClient(StockQuote stockQuote) throws RemoteException;

}
