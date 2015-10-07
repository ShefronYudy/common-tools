package com.shefron.module.thread;

import sun.awt.windows.ThemeReader;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2014/12/12.
 */
public class CruiseThread extends Thread{

    private List<String> list = new LinkedList<String>();

    private List<String> resultList = new ArrayList<String>();

    private boolean run = true;

    private static byte[] lock = new byte[1];

    public CruiseThread(List<String> list,List<String> resultList){
        this.list = list;
        this.resultList = resultList;

    }

    public void run(){
        String task = null;
        while (run){

            synchronized (CruiseThread.lock){
                if(list.size()<1){
                    run = false;
                    continue;
                }
                task = list.remove(0);
                if (task == null)
                    continue;

                this.resultList.add("hehe#"+task);
            }

            try{
                Thread.sleep(100);
            }catch (Exception e){e.printStackTrace();}

            synchronized (CruiseThread.lock){
                this.resultList.add("hehe#"+task);
            }

        }

    }

    public static void main(String[] args){
        long start = System.currentTimeMillis();
        List<String> list = new LinkedList<String>();
        List<String> resultList = new ArrayList<String>();

        for(int i=0;i<1000;i++){
            list.add("hello"+i);
        }

        List<CruiseThread> threadList = new ArrayList<CruiseThread>();
        for(int i=0;i<15;i++){
            CruiseThread thread = new CruiseThread(list,resultList);
            thread.start();

            threadList.add(thread);
        }


        for(int waitNum = 0;true;){
            for(int i=0;i<threadList.size();i++){
                if(threadList.get(i).getState() != State.TERMINATED){
                    waitNum = 0;
                    break;
                }
            }
            if((++waitNum)>2)
                break;
        }
        System.out.println("耗时:"+(System.currentTimeMillis()-start)/1000);
        System.out.println("#"+resultList);
    }
}
