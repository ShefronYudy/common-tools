package com.shefron.module.rmi.dgc;

import com.shefron.module.rmi.RmiClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Administrator on 2014/12/2.
 */
public interface HelloService extends Remote{

    public boolean isAccessed() throws RemoteException;

    public void access() throws RemoteException;

    public void bye() throws RemoteException;
}
