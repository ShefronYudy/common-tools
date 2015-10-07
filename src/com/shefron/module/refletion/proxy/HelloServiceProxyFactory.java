package com.shefron.module.refletion.proxy;

import com.shefron.module.refletion.remote.HelloService;
import com.shefron.module.refletion.remote.HelloServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2014/11/29.
 */
public class HelloServiceProxyFactory {

    public static HelloService getHelloServiceProxy(final HelloService helloService){
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object result = null;

                System.out.println("before calling:"+method);
                result = method.invoke(helloService,args);
                System.out.println("after calling:"+method);

                return result;
            }
        };

        return (HelloService)Proxy.newProxyInstance(HelloService.class.getClassLoader(),new Class[]{HelloService.class},handler);
    }

    public static void main(String[] args) throws Exception{
        HelloService helloService = new HelloServiceImpl();
        HelloService helloServiceProxy = HelloServiceProxyFactory.getHelloServiceProxy(helloService);

        System.out.println("动态代理类："+helloServiceProxy.getClass().getName());
        System.out.println(helloServiceProxy.echo("hehe") );


    }

}
