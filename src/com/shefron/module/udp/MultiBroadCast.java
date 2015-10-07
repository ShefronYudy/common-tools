package com.shefron.module.udp;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Date;

/**
 * Created by Administrator on 2014/11/30.
 */
public class MultiBroadCast {

    /**
     * 发送组播的数据包
     * @throws Exception
     */
    public void multicastSender() throws Exception{
        InetAddress groupAddress = InetAddress.getByName("224.0.0.1");
        int port = 4000;

        MulticastSocket ms = null;

        try {
            ms = new MulticastSocket(port);
//            ms.joinGroup(groupAddress);
            while (true){
                String msg = "hello "+new Date();
                byte[] buffer = msg.getBytes();
                DatagramPacket datagramPacket = new DatagramPacket(buffer,buffer.length,groupAddress,port);

                ms.send(datagramPacket);

                Thread.sleep(1000);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(ms != null){
                try {
                    ms.leaveGroup(groupAddress);
                    ms.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 接收组播的数据包
     * @throws Exception
     */
    public void multicastReceive() throws Exception{
        InetAddress groupAddress = InetAddress.getByName("224.0.0.1");
        int port = 4000;

        MulticastSocket ms = null;

        try {
            ms = new MulticastSocket(port);
            ms.joinGroup(groupAddress);

            byte[] buffer = new byte[8192];
            while (true){
                DatagramPacket dPacket = new DatagramPacket(buffer,buffer.length);

                ms.receive(dPacket);

                String s = new String(dPacket.getData(),0,dPacket.getLength());

                System.out.println(s);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(ms != null){
                try {
                    ms.leaveGroup(groupAddress);
                    ms.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }




}
