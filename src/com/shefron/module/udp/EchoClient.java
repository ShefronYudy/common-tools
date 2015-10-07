package com.shefron.module.udp;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Administrator on 2014/11/30.
 */
public class EchoClient {
    private DatagramChannel datagramChannel = null;

    private ByteBuffer sendBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer receiveBuffer = ByteBuffer.allocate(1024);

    private Charset charset = Charset.forName("GBK");

    private Selector selector;

    public EchoClient() throws Exception{
        this(7000);
    }

    public EchoClient(int port) throws Exception{
        datagramChannel = DatagramChannel.open();

        InetAddress ia = InetAddress.getLocalHost();
        InetSocketAddress isa = new InetSocketAddress(ia,port);

        datagramChannel.configureBlocking(false);

        datagramChannel.socket().bind(isa);

        isa = new InetSocketAddress(ia, 8000);
        datagramChannel.connect(isa);
        selector = Selector.open();
    }

    public void receiveFromUser(){
        try {
            BufferedReader localReader = new BufferedReader(new InputStreamReader(System.in));
            String msg = null;
            while ((msg = localReader.readLine()) != null){
                synchronized (sendBuffer){
                    sendBuffer.put(charset.encode(msg+"\r\n"));
                }
                if(msg.equals("bye")){
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void talk() throws Exception{
        datagramChannel.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE);

        while (selector.select()>0){
            Set readyKeys = selector.selectedKeys();
            Iterator it = readyKeys.iterator();

            while (it.hasNext()){
                SelectionKey key = null;

                try{
                    key  = (SelectionKey)it.next();
                    it.remove();

                    if(key.isReadable()){
                        receive(key);
                    }

                    if(key.isWritable()){
                        send(key);
                    }


                }catch (Exception e){
                    e.printStackTrace();
                    try{
                        if(key != null){
                            key.cancel();
                            key.channel().close();
                        }
                    }catch (Exception e1){e1.printStackTrace();}
                }

            }
        }

    }

    public void send(SelectionKey key) throws Exception{
        DatagramChannel datagramChannel1 = (DatagramChannel)key.channel();

        synchronized (sendBuffer){
            sendBuffer.flip();
            datagramChannel1.write(sendBuffer);
            sendBuffer.compact();
        }
    }

    public void receive(SelectionKey key) throws Exception{
        DatagramChannel channel = (DatagramChannel)key.channel();
        datagramChannel.read(receiveBuffer);

        receiveBuffer.flip();
        String receiveData = charset.decode(receiveBuffer).toString();

        if(receiveData.indexOf("\n") == -1) return;

        String outputData = receiveData.substring(0,receiveData.lastIndexOf("\n")+1);
        System.out.println(outputData);


        if(outputData.equals("echo:byte\n\n")){
            key.cancel();
            datagramChannel.close();
            System.out.println("关闭与服务器的连接");
            selector.close();
            System.exit(0);
        }

        ByteBuffer temp = charset.encode(outputData);
        receiveBuffer.position(temp.limit());
        receiveBuffer.compact();

    }

}
