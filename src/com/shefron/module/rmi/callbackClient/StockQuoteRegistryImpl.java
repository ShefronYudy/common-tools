package com.shefron.module.rmi.callbackClient;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * Created by Administrator on 2014/12/1.
 */
public class StockQuoteRegistryImpl extends UnicastRemoteObject implements StockQuoteRegistry,Runnable {

    Set<StockQuote> clients = null;

    public StockQuoteRegistryImpl() throws RemoteException{
        clients = new HashSet<StockQuote>();
    }

    @Override
    public void registerClient(StockQuote stockQuote) throws RemoteException {
        System.out.println("加入一个客户端");
        clients.add(stockQuote);
    }

    @Override
    public void unregisterClient(StockQuote stockQuote) throws RemoteException {
        System.out.println("删除一个客户");
        clients.remove(stockQuote);
    }

    @Override
    public void run() {
        String[] sysbols = new String[]{"ONE","TWO","THREE","FOUR","FIVE","SIX","SEVEN"};

        Random random = new Random();

        double values[] = new double[sysbols.length];
        for (int i=0;i<values.length;i++){
            values[i] = 25.0 + random.nextInt(100);
        }

        for(;;){
            int sym = random.nextInt(sysbols.length);

            int change = 100-random.nextInt(201);
            values[sym] = values[sym] +((double)change)/100.0;

            if (values[sym] < 0) values[sym] = 0.01;

            Iterator<StockQuote> iterator = clients.iterator();
            while (iterator.hasNext()){
                StockQuote stockQuote = iterator.next();
                try {
                    stockQuote.quote(sysbols[sym],values[sym]);
                }catch (Exception e){
                    System.out.println("删除一个无效客户端");
                    e.printStackTrace();
                    iterator.remove();
                }
            }

            try {
                Thread.sleep(1000);
            }catch (Exception e){e.printStackTrace();}
        }

    }
}
