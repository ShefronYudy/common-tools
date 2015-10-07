package com.shefron.module.nio;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * Created by Administrator on 2014/11/23.
 */
public class BlockEchoClient {

    private SocketChannel socketChannel = null;

    public BlockEchoClient() throws IOException{
        socketChannel = SocketChannel.open();
        InetAddress iaddr = InetAddress.getLocalHost();

        InetSocketAddress isa = new InetSocketAddress(iaddr,8000);
        socketChannel.connect(isa);

        System.out.println("与服务器建立连接完成");

    }

    public void talk(){
        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(socketChannel.socket().getInputStream()));
            PrintWriter pWriter = new PrintWriter(new OutputStreamWriter(socketChannel.socket().getOutputStream()));

            BufferedReader localReader = new BufferedReader(new InputStreamReader(System.in));
            String msg = null;
            while ((msg=localReader.readLine()) != null){
                pWriter.println(msg);
                System.out.println("server> "+bReader.readLine());

                if(msg.equals("bye")){
                    break;
                }
            }

        }catch (Exception  e){
            e.printStackTrace();
        }finally {
            try {
                socketChannel.close();
            }catch (Exception e){e.printStackTrace();}
        }
    }



}
