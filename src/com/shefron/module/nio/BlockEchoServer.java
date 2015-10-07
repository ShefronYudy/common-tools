package com.shefron.module.nio;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Handler;

/**
 * Created by Administrator on 2014/11/23.
 */
public class BlockEchoServer {

    private int port = 8000;
    private ServerSocketChannel serverSocketChannel = null;
    private ExecutorService executorService;
    private static final int POOL_SIZE = 4;

    public BlockEchoServer() throws IOException{
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*POOL_SIZE);
        //
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().setReuseAddress(true);
        serverSocketChannel.socket().bind(new InetSocketAddress(port));

        System.out.println("服务器启动");
    }


    public void service(){
        while(true){
            SocketChannel socketChannel = null;
            try{
                socketChannel = serverSocketChannel.accept();
                executorService.execute(new Handler(socketChannel));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class Handler implements Runnable{
        private SocketChannel socketChannel;

        public Handler(SocketChannel socketChannel){
            this.socketChannel = socketChannel;
        }

        public void run(){
            hanler(socketChannel);
        }

    }

    public void hanler(SocketChannel socketChannel){
        try{
            Socket socket = socketChannel.socket();

            System.out.println("接收客户端连接：来自："+socket.getInetAddress()+":"+socket.getPort());

            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            String msg = null;
            while((msg = br.readLine()) != null){
                System.out.println(msg);

                pw.println(echo(msg));
                if(msg.equals("bye")){
                    break;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(socketChannel!=null)socketChannel.close();
            }catch (Exception e){}
        }
    }

    public String echo(String msg){
        return "echo:"+msg;
    }

    public String readLine(SocketChannel socketChannel) throws IOException{
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        ByteBuffer tempBuffer = ByteBuffer.allocate(1);
        boolean isLine = false;
        boolean isEnd = false;
        String data = null;
        while (!isLine&&!isEnd){
            tempBuffer.clear();
            int n = socketChannel.read(tempBuffer);
            if(n==-1){
                isEnd = true;
                break;
            }
            if(n == 0){
                continue;
            }
            tempBuffer.flip();
            buffer.put(tempBuffer);
            buffer.flip();

            Charset charset = Charset.forName("GBK");
            CharBuffer charBuffer = charset.decode(buffer);
            data=charBuffer.toString();
            if(data.indexOf("\r\n") != -1){
                isLine = true;
                data = data.substring(0,data.indexOf("\r\n"));
                break;
            }
            buffer.position(buffer.limit());
            buffer.limit(buffer.capacity());
        }

        return data;


    }


}
