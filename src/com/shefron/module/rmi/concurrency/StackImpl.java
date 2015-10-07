package com.shefron.module.rmi.concurrency;

import com.shefron.module.thread.ThreadPool;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Administrator on 2014/12/1.
 */
public class StackImpl extends UnicastRemoteObject implements Stack {

    private String name;

    private String[] buffer = new String[100];
    int point = -1;

    public StackImpl(String name) throws RemoteException{
        this.name = name;
    }

    @Override
    public String getName() throws RemoteException {
        return this.name;
    }

    @Override
    public synchronized int getPoint() throws RemoteException {
        return point;
    }

    @Override
    public synchronized String pop() throws RemoteException {
        this.notifyAll();

        while(point == -1){
            System.out.println(Thread.currentThread().getName()+":wait");
            try {
                this.wait();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        String goods = buffer[point];
        buffer[point] = null;
        Thread.yield();

        point--;

        return goods;
    }

    @Override
    public synchronized void push(String goods) throws RemoteException {
        this.notifyAll();

        while (point == buffer.length-1){
            System.out.println(Thread.currentThread().getName()+":wait");
            try {
                this.wait();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        point++;
        Thread.yield();
        buffer[point]=goods;

    }
}
