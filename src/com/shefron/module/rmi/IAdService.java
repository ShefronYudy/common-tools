package com.shefron.module.rmi;


import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Administrator on 2014/11/30.
 */
public interface IAdService extends INewsService,Remote {

    /**
     * 为广告付费
     * @param position
     * @throws Exception
     */
    public void pay(String position) throws RemoteException;

    /**
     * 获取广告点击率
     * @param id
     * @return
     * @throws Exception
     */
    public long click(String id) throws RemoteException;

}
