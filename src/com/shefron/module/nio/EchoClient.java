package com.shefron.module.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Administrator on 2014/11/23.
 */
public class EchoClient {
    private SocketChannel socketChannel = null;
    private ByteBuffer sendBuff = ByteBuffer.allocate(1024);
    private ByteBuffer receiveBuff = ByteBuffer.allocate(1024);

    private Charset charset = Charset.forName("GBK");
    private Selector selector;

    public EchoClient() throws IOException{
        socketChannel=SocketChannel.open();
        InetAddress ia = InetAddress.getLocalHost();
        InetSocketAddress isa = new InetSocketAddress(ia,8000);

        socketChannel.connect(isa);
        socketChannel.configureBlocking(false);

        System.out.println("与服务器连接成功");

        selector = Selector.open();
    }

    /**
     * runs on a thread
     */
    public void receiveFromUser() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String msg = null;
            while((msg = reader.readLine())!= null){
                synchronized (sendBuff){
                    sendBuff.put(encode(msg+"\r\n"));
                }
                if(msg.equals("bye")){
                    break;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void talk() throws IOException{
        socketChannel.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE);

        while (selector.select()>0){
            Set readyKeys = selector.selectedKeys();
            Iterator it = readyKeys.iterator();
            while(it.hasNext()){
                SelectionKey key = null;

                try {
                    key = (SelectionKey)it.next();
                    it.remove();

                    if(key.isReadable()){
                        receive(key);
                    }
                    if(key.isWritable()){
                        send(key);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    try {
                        if(key != null){
                            key.cancel();
                            key.channel().close();
                        }
                    }catch (Exception e1){e1.printStackTrace();}
                }
            }
        }
    }

    public void send(SelectionKey key) throws IOException{
        SocketChannel socketChannel1 = (SocketChannel)key.channel();
        synchronized (sendBuff){
            sendBuff.flip();
            socketChannel1.write(sendBuff);
            sendBuff.compact();
        }

    }

    public void receive(SelectionKey key) throws IOException{
        SocketChannel socketChannel1 =(SocketChannel)key.channel();
        socketChannel1.read(receiveBuff);
        receiveBuff.flip();
        String receiveData = decode(receiveBuff);
        if(receiveData.indexOf("\n") == -1)return;

        String outputData = receiveData.substring(0,receiveData.indexOf("\n")+1);

        System.out.println(outputData);
        if(outputData.equals("echo:bye\r\n")){
            key.cancel();
            socketChannel1.close();
            System.out.println("关闭与服务器的连接");

            selector.close();
            System.exit(0);
        }

        ByteBuffer temp = encode(outputData);
        receiveBuff.position(temp.limit());
        receiveBuff.compact();
    }

    public ByteBuffer encode(String msg){
        return charset.encode(msg);
    }

    public String decode(ByteBuffer msg){
        return charset.decode(msg).toString();
    }

}
