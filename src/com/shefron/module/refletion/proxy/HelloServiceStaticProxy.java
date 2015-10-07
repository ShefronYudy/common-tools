package com.shefron.module.refletion.proxy;

import com.shefron.module.refletion.remote.Call;
import com.shefron.module.refletion.remote.HelloService;

import java.util.Date;

/**
 * Created by Administrator on 2014/11/29.
 */
public class HelloServiceStaticProxy implements HelloService{
    private String host;
    private int port;

    public HelloServiceStaticProxy(String host,int port){
        this.host = host;
        this.port = port;
    }


    @Override
    public String echo(String msg) throws RemoteException{
        SocketConnector connector = null;
        try{
            connector = new SocketConnector(host, port);
            Call call = new Call(HelloService.class.getName(),"echo",new Class []{String.class},new Object[]{msg});

            connector.send(call);

            call = (Call)connector.receive();

            Object result = call.getResult();
            if(result instanceof Throwable){
                throw new RemoteException((Throwable)result);
            }else {
                return (String)result;
            }

        }catch (Throwable e){
            throw new RemoteException(e);
        }finally {
            if(connector != null) connector.close();
        }
    }

    @Override
    public Date getTime() throws RemoteException{
        SocketConnector connector = null;
        try{
            connector = new SocketConnector(host, port);
            Call call = new Call(HelloService.class.getName(),"getTime",new Class []{},new Object[]{});

            connector.send(call);

            call = (Call)connector.receive();

            Object result = call.getResult();
            if(result instanceof Throwable){
                throw new RemoteException((Throwable)result);
            }else {
                return (Date)result;
            }

        }catch (Throwable e){
            throw new RemoteException(e);
        }finally {
            if(connector != null) connector.close();
        }
    }
}
