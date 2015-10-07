package com.shefron.module.rmi;


import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

/**
 * Created by Administrator on 2014/11/30.
 */
public class AdServiceImpl implements IAdService {

    private String name;

    public AdServiceImpl(String name) throws RemoteException{
        this.name = name;
        //将远程类导出为远程对象
        UnicastRemoteObject.exportObject(this,0);
    }

    @Override
    public void pay(String position) throws RemoteException {
        System.out.println("为【"+position+"】付费");
    }

    @Override
    public long click(String id) throws RemoteException {
        System.out.println("id="+id+"点击率 100");
        return 100L;
    }

    @Override
    public void publish(String msg) throws IOException {
        System.out.println(new Date()+"发布新闻"+msg);
    }

    @Override
    public void order(String id) throws Exception {
        System.out.println("用户ID="+id+"订阅了新闻");
    }
}
