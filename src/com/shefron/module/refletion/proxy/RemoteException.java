package com.shefron.module.refletion.proxy;

/**
 * Created by Administrator on 2014/11/29.
 */
public class RemoteException extends Exception{
    public RemoteException(Throwable t){
        super(t);
    }

    public RemoteException(String msg,Throwable t){
        super(msg,t);
    }
}
