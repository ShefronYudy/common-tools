package com.shefron.module.refletion.proxy;

import com.shefron.module.refletion.remote.Call;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2014/11/29.
 */
public class ProxyFactory {

    public static Object getProxy(final Class classType,final String host,final int port){
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                SocketConnector connector = null;
                try {
                    connector = new SocketConnector(host, port);

                    Call call = new Call(classType.getName(),method.getName(),method.getParameterTypes(),args);

                    connector.send(call);

                    call = (Call)connector.receive();

                    Object result = call.getResult();
                    if(result instanceof Throwable){
                        throw new RemoteException((Throwable)result);
                    }else {
                        return result;
                    }

                }finally {
                    connector.close();
                }
            }
        };

        return Proxy.newProxyInstance(classType.getClassLoader(),new Class[]{classType},handler);
    }
}
