package com.shefron.module.refletion.remote;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2014/11/29.
 */
public class SimpleServer {

    private Map remoteObjects = new HashMap();

    public void register(String className,Object remoteObj){
        remoteObjects.put(className, remoteObj);
    }

    public void service() throws Exception{
        ServerSocket serverSocket = new ServerSocket(8000);
        System.out.println("服务启动");

        while (true){
            Socket socket = serverSocket.accept();

            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            ObjectInputStream ois = new ObjectInputStream(is);
            ObjectOutputStream oos = new ObjectOutputStream(os);

            Call call = (Call)ois.readObject();
            System.out.println(call);

            call=invoke(call);
            oos.writeObject(call);

            ois.close();
            oos.close();
            socket.close();
        }

    }

    public Call invoke(Call call){
        Object result = null;
        try{
            String className = call.getClassName();
            String methodName = call.getMethodName();
            Object[] params = call.getParams();
            Class classType = Class.forName(className);

            Class[] paramTypes = call.getParamTypes();
            Method method = classType.getMethod(methodName,paramTypes);
            Object remoteObj = remoteObjects.get(className);
            if(remoteObj == null){
                throw new Exception("The remote obj not found");
            }else{
                result = method.invoke(remoteObj, params);
            }

        }catch (Exception e){
            e.printStackTrace();
            result = e;
        }

        call.setResult(result);

        return call;
    }

    public static void main(String[] args){
        try {
            SimpleServer server = new SimpleServer();
            server.register(HelloService.class.getName(),new HelloServiceImpl());

            server.service();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
