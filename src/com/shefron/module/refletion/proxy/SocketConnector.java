package com.shefron.module.refletion.proxy;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Administrator on 2014/11/29.
 */
public class SocketConnector {
    private String host;
    private int port;
    private Socket socket;
    private InputStream input;
    private OutputStream output;
    private ObjectOutputStream oOutput;
    private ObjectInputStream oInput;

    public SocketConnector(String host,int port) throws Exception{
        this.host = host;
        this.port = port;
        connection(host, port);
    }

    public void connection(String host,int port) throws Exception{
        socket = new Socket(host,port);

        this.input = socket.getInputStream();
        this.oInput = new ObjectInputStream(input);
        this.output = socket.getOutputStream();
        this.oOutput = new ObjectOutputStream(oOutput);
    }

    public void send(Object msg) throws Exception{
        this.oOutput.writeObject(msg);
    }

    public Object receive() throws Exception{
        return this.oInput.readObject();
    }

    public void close(){
        try {
        }finally {
            try {
                this.oInput.close();
                this.oOutput.close();
                this.socket.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
