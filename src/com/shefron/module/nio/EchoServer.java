package com.shefron.module.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Administrator on 2014/11/23.
 */
public class EchoServer {

    private Selector selector = null;
    private ServerSocketChannel serverSocketChannel= null;
    private int port = 8000;

    private Charset charset = Charset.forName("GBK");

    public EchoServer() throws IOException{
        selector = Selector.open();

        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().setReuseAddress(true);
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(port));

        System.out.println("服务器启动");
    }

    public void service() throws IOException{
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while(selector.select()>0){
            Set readyKeys = selector.selectedKeys();
            Iterator it = readyKeys.iterator();

            while (it.hasNext()){
                SelectionKey key = null;
                try{
                    key = (SelectionKey)it.next();
                    it.remove();

                    if(key.isAcceptable()){
                        ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
                        SocketChannel socketChannel = (SocketChannel)ssc.accept();
                        System.out.println("接收客户端："+socketChannel.socket().getInetAddress()+":"+socketChannel.socket().getPort());

                        socketChannel.configureBlocking(false);

                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        socketChannel.register(selector,SelectionKey.OP_READ|SelectionKey.OP_WRITE,buffer);
                    }

                    if(key.isReadable()){
                        receive(key);
                    }
                    if(key.isWritable()){
                        send(key);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }
    }

    public void send(SelectionKey key) throws IOException{
        ByteBuffer buffer = (ByteBuffer)key.attachment();

        SocketChannel socketChannel=(SocketChannel)key.channel();
        buffer.flip();

        String data = charset.decode(buffer).toString();

        if(data.indexOf("\r\n") == -1)return;

        String outputData = data.substring(0,data.indexOf("\n")+1);
        System.out.println(outputData);

        ByteBuffer outputBuffer = charset.encode("echo:"+outputData);
        while (outputBuffer.hasRemaining()){
            socketChannel.write(outputBuffer);
        }

        ByteBuffer temp = charset.encode(outputData);
        buffer.position(temp.limit());
        buffer.compact();

        if(outputData.equals("bye\r\n")){
            key.cancel();
            socketChannel.close();
            System.out.println("关闭与客户端连接");
        }

    }

    public void receive(SelectionKey key) throws IOException{
        ByteBuffer buffer = (ByteBuffer)key.attachment();

        SocketChannel socketChannel = (SocketChannel)key.channel();
        ByteBuffer readBuf = ByteBuffer.allocate(32);
        socketChannel.read(readBuf);
        readBuf.flip();

        buffer.limit(readBuf.capacity());
        buffer.put(readBuf);
    }



}
