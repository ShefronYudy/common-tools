package com.shefron.module.udp;

import org.junit.Test;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * Created by Administrator on 2014/11/30.
 */
public class DatagramChannelTest {

    /**
     * 用DatagramChannel的send方法发送数据包
     */
    @Test
    public void testSend() throws Exception{
        DatagramChannel channel = DatagramChannel.open();
        DatagramSocket socket = channel.socket();

        SocketAddress socketAddress = new InetSocketAddress(7000);
        SocketAddress remoteAddress = new InetSocketAddress(InetAddress.getByName("remoteHost"), 8000);

        while (true){
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.clear();

            System.out.println("缓冲区剩余字节数为" + buffer.remaining() );
            int n =     channel.send(buffer, remoteAddress);
            System.out.println("发送的字节数为"+n);

            wait(500);
        }
    }

    @Test
    public void receive() throws Exception{
        final int ENOUGH_SIZE = 1024;
        final boolean isBlock = true;

        DatagramChannel channel = DatagramChannel.open();
        channel.configureBlocking(isBlock);

        ByteBuffer buffer = ByteBuffer.allocate(ENOUGH_SIZE);

        DatagramSocket datagramSocket = channel.socket();
        SocketAddress localBind = new InetSocketAddress(8000);

//        channel.bind(localBind);

        while (true){
            SocketAddress remoteAddress = channel.receive(buffer);

            if(remoteAddress == null){
                System.out.println("没有要接收的数据包");
            }else{
                buffer.flip();
                System.out.println("接收的数据包大小为："+buffer.remaining());
            }
            wait(500);

        }


    }


}
