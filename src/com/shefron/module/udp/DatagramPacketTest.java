package com.shefron.module.udp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Administrator on 2014/11/30.
 */
public class DatagramPacketTest {

    private int port = 8000;
    private DatagramSocket sendSocket;
    private DatagramSocket receiveSocket;
    private static final int MAX_LENGTH = 1024*3;

    public DatagramPacketTest() throws Exception{
        sendSocket = new DatagramSocket();
        sendSocket.setTrafficClass(IPServiceType.HightCount);
        //只向指定连接的主机收发数据包
//        sendSocket.connect(InetAddress.getByName("localhost"),port);
        receiveSocket = new DatagramSocket(port);

        sender.start();
        receive.start();
    }

    public byte[] long2Byte(long[] data) throws Exception{
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        for(long l : data){
            dataOutputStream.writeLong(l);
        }
        dataOutputStream.flush();
        dataOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    public long[] byte2Long(byte[] data) throws Exception{
        long[] longs = new long[data.length/8];

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);

        for(int i=0;i<data.length/8;i++){
            longs[i] = dataInputStream.readLong();
        }
        return longs;
    }

    public void send(byte[] bigData) throws Exception{
        DatagramPacket packet = new DatagramPacket(bigData,0,512, InetAddress.getByName("localhost"),port);

        int bytesSent = 0;
        int count = 0;
        while(bytesSent < bigData.length){
            sendSocket.send(packet);
            System.out.println("SendSocket>第"+(count++)+"次发送了"+packet.getLength()+"个字节" );

            bytesSent +=packet.getLength();

            int remain = bigData.length-bytesSent;
            int length = (remain>512)?512:remain;

            packet.setData(bigData,bytesSent,length);
        }
    }

    public byte[] receive() throws Exception{
        byte[] bigData = new byte[MAX_LENGTH];
        DatagramPacket packet = new DatagramPacket(bigData,0,MAX_LENGTH);

        int bytesReceived = 0;
        int count = 0;

        long beginTime = System.currentTimeMillis();
        while (bytesReceived < bigData.length && (System.currentTimeMillis()-beginTime< 5* 60000) ){
            receiveSocket.receive(packet);
            System.out.println("ReceiveSocket>第"+(count++)+"次接收到"+packet.getLength()+"个字节");

            bytesReceived += packet.getLength();
            packet.setData(bigData,bytesReceived,MAX_LENGTH-bytesReceived);
        }

        return packet.getData();
    }

    public  Thread sender = new Thread(){
        public void run(){
            long[] longArray = new long[MAX_LENGTH/8];
            for(int i=0;i<longArray.length;i++){
                longArray[i] = ++i;
            }
            try{
                send(long2Byte(longArray));
            }catch (Exception e){e.printStackTrace();}

        }

    };

    public Thread receive = new Thread(){
        public void run(){
            try{
                long[] longArray = byte2Long(receive());

                for(int i=0;i<longArray.length;i++){
                    if(i%100 == 0) System.out.println();

                    System.out.print(longArray[i]);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    };


}
