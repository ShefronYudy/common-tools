package com.shefron.module.nio;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Administrator on 2014/11/23.
 */
public class PingClient {

    private Selector selector;
    private LinkedList targets = new LinkedList();
    private LinkedList finishTargets = new LinkedList();
    private  boolean shutdown = false;

    public PingClient() throws IOException{
        selector = Selector.open();
        Connector connector = new Connector();
        Printer printer = new Printer();
        connector.start();
        printer.start();
        receiveTarget();

    }

    /**
     * Main
     * @param args
     */
    public static void main(String[] args) throws IOException{
        new PingClient();
    }

    public void addTarget(Target target){
        SocketChannel socketChannel = null;

        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(target.address);

            target.channel = socketChannel;
            target.connectionStart = System.currentTimeMillis();

            synchronized (targets){
                targets.add(target);
            }
            selector.wakeup();

        }catch (Exception e){
            if(socketChannel !=null){
                try {
                    socketChannel.close();
                }catch (Exception e1){
                    e1.printStackTrace();
                }
                target.failure = e;
                addFinishTarget(target);
            }
        }
    }

    public void addFinishTarget(Target target){
        synchronized (finishTargets){
            finishTargets.notify();
            finishTargets.add(target);
        }
    }

    public void printFinishTargets(){
        try {
            for(;;){
                Target target = null;
                synchronized (finishTargets){
                    while (finishTargets.size()==0)
                        finishTargets.wait();
                    target = (Target)finishTargets.removeFirst();
                }
                target.show();
            }

        }catch (Exception e){
            e.printStackTrace();
            return;
        }
    }

    public void registerTargets(){
        synchronized (targets){
            while (targets.size()>0){
                Target target = (Target)targets.removeFirst();

                try {
                    target.channel.register(selector, SelectionKey.OP_CONNECT,target);
                }catch (Exception e){
                    try {
                        target.channel.close();
                    }catch (Exception e1){
                        target.failure =e1;
                        addFinishTarget(target);
                    }
                }
            }
        }
    }

    public void processSelectedKeys() throws IOException{
        for (Iterator it=selector.selectedKeys().iterator();it.hasNext();){
            SelectionKey selectionKey = (SelectionKey)it.next();
            it.remove();

            Target target = (Target)selectionKey.attachment();
            SocketChannel socketChannel = (SocketChannel)selectionKey.channel();

            try {
                if(socketChannel.finishConnect()){
                    selectionKey.cancel();
                    target.connectionFinish = System.currentTimeMillis();
                    socketChannel.close();

                    addFinishTarget(target);

                }
            }catch (Exception e){
                socketChannel.close();
                target.failure=e;
                addFinishTarget(target);
            }
        }
    }

    public void receiveTarget(){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String msg = null;
            while ((msg=reader.readLine()) != null){
                if(!msg.equals("bye")){
                    Target target = new Target(msg);
                    addTarget(target);
                }else{
                    shutdown = true;
                    selector.wakeup();
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    class Printer extends Thread{
        public Printer(){
            setDaemon(true);
        }
        public void run(){
            printFinishTargets();
        }
    }

    class Connector extends Thread{
        public void run(){
            while (!shutdown){
                try {
                    registerTargets();
                    if(selector.select()>0){
                        processSelectedKeys();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                    try {
                        selector.close();
                    }catch (Exception e1){
                        e1.printStackTrace();
                    }
                }

            }
        }
    }


}



class Target{
    InetSocketAddress address;
    SocketChannel channel;
    Exception failure;
    long connectionStart;
    long connectionFinish = 0;
    boolean shown = false;

    Target(String host){
        try {
            address = new InetSocketAddress(InetAddress.getByName(host),80);
        }catch (Exception e){
            failure = e;
        }
    }

    void show(){
        String result;
        if(connectionFinish!=0)
            result = Long.toString(connectionFinish-connectionStart);
        else if (failure != null)
            result = failure.toString();
        else
            result = "Time out";
        System.out.println(address+":"+result);
        shown = true;
    }

}
