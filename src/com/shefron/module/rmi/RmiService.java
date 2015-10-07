package com.shefron.module.rmi;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * registor url<br/>
 * rmi://服务器名:端口/对象注册的名字
 *
 * Created by Administrator on 2014/11/30.
 */
public class RmiService {

    /**
     * 该方式需要在JDK1.4版本以上
     * @throws Exception
     */
    public void rmiRegistory() throws Exception{
        IAdService adService = new AdServiceImpl("服装广告");

        Context namingContext = new InitialContext();


        //完整名字：rmi:localhost:1099/adService
        namingContext.rebind("rmi:adService",adService);

        System.out.println("服务器注册了adService");

    }

    /**
     * JDK1.3版本及以下
     * @throws Exception
     */
    public void rmiRegistory2() throws Exception{

        String rmiIP = "10.12.1.171";
        int rmiPort = 1099;

        IAdService adService = new AdServiceImpl("服装广告");

        //服务器多个IP时，需要绑定其中的一个
        System.setProperty("java.rmi.server.hostname", rmiIP);
        //创建一个注册实例，在此端口监听请求
        LocateRegistry.createRegistry(rmiPort);
        //响应远程调用或其继承UnicastRemoteObject
        //UnicastRemoteObject.exportObject(schedule, RMI_PORT);
        Naming.rebind("rmi://" + rmiIP + ":" +rmiPort + "/adService", adService);


    }


}
