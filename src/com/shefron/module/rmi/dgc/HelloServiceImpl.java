package com.shefron.module.rmi.dgc;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.server.Unreferenced;

/**
 * Created by Administrator on 2014/12/2.
 */
public class HelloServiceImpl extends UnicastRemoteObject implements HelloService,Unreferenced {

    private boolean isAccessed = false;

    @Override
    public boolean isAccessed() throws RemoteException {
        return isAccessed;
    }

    @Override
    public void access() throws RemoteException {
        System.out.println("HelloServiceImpl:被一个客户端远程引用");
        isAccessed = true;

    }

    @Override
    public void bye() throws RemoteException {
        System.out.println("HelloServiceImpl:不再使用了");
    }

    @Override
    public void unreferenced() {
        System.out.println("HelloServiceImpl:永不再使用了");

    }

    public HelloServiceImpl() throws RemoteException{
    }
}
