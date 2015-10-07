package com.shefron.module.udp;

import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;

/**
 * Created by Administrator on 2014/11/30.
 */
public class EchoServer {
    private final int BuffSize = 1024;
    private DatagramChannel channel;
    private final int port = 8000;

    public EchoServer() throws Exception{
        channel = DatagramChannel.open();
        DatagramSocket socket = channel.socket();
        InetSocketAddress socketAddress = new InetSocketAddress(port);

        socket.bind(socketAddress);
        System.out.println("服务启动");
    }

    public String echo(String msg){
        return "echo:"+msg;
    }


    public void service() throws Exception{
        ByteBuffer buffer = ByteBuffer.allocate(BuffSize);

        while (true){

            try {
                buffer.clear();

                InetSocketAddress socketAddress = (InetSocketAddress)channel.receive(buffer);
                buffer.flip();
                String msg = Charset.forName("GBK").decode(buffer).toString();
                System.out.println("来自客户端["+socketAddress.getAddress()+":"+port+"]消息："+msg);

                channel.send(ByteBuffer.wrap(echo(msg).getBytes()),socketAddress);

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }






}
