package com.shefron.module.refletion.remote;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Administrator on 2014/11/29.
 */
public class SimpleClient {
    public void invoke() throws Exception{
        Socket socket = new Socket("localhost", 8000);

        OutputStream out = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(out);

        InputStream in = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(in);

        Call call = new Call(HelloService.class.getName(),"echo",new Class[]{String.class},new Object[]{"Hello"});

        oos.writeObject(call);

        call=(Call)ois.readObject();
        System.out.println(call.getResult());

        ois.close();
        oos.close();
        socket.close();

    }

    public static void main(String[] args){
        try {
            new SimpleClient().invoke();
        }catch (Exception e){
            e.printStackTrace();
        }

    }



}
