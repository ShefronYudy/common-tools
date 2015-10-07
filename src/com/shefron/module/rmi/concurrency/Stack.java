package com.shefron.module.rmi.concurrency;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Administrator on 2014/12/1.
 */
public interface Stack extends Remote {

    public String getName() throws RemoteException;

    public int getPoint() throws RemoteException;

    public String pop() throws RemoteException;

    public void push(String goods) throws RemoteException;
}
