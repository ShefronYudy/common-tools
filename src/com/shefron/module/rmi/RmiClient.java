package com.shefron.module.rmi;

import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import java.rmi.Naming;

/**
 * Created by Administrator on 2014/11/30.
 */
public class RmiClient {

    public Object getRmiObject() throws Exception{
        return  Naming.lookup("rmi://10.12.1.171:1099/adService");
    }

    public Object getRmiObject2() throws Exception{
        return new InitialContext().lookup("rmi://10.12.1.171:1099/adService");
    }

    public void showRemoteObjects() throws Exception{
        NamingEnumeration<NameClassPair> enumeration = new InitialContext().list("rmi:");
        while (enumeration.hasMore()){
            System.out.println(enumeration.next().getName());
        }

    }
}
