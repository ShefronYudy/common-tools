package com.shefron.module.thread;

import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by Administrator on 2014/11/22.
 */
public class ThreadPool extends ThreadGroup {
    private LinkedList<Runnable> taskQueue = null;
    private boolean isClosed = false;
    private static int threadPoolID;
    private int threadID;

    public ThreadPool(int poolSize){
        super("The threadpool ID:"+threadPoolID++);
        setDaemon(true);

        taskQueue = new LinkedList<Runnable>();
        for(int i=0;i<poolSize;i++){
            new WorkThread().start();
        }

    }

    protected synchronized Runnable getTask() throws InterruptedException{
        while(taskQueue.size()==0){
            if(isClosed) return null;
            wait();
        }
        return taskQueue.removeFirst();
    }

    protected synchronized void execute(Runnable task){
        if(isClosed){
            throw new IllegalStateException();
        }
        if(task != null){
            taskQueue.add(task);
            notify();
        }
    }

    protected synchronized void close(){
        if(!isClosed){
            isClosed = true;
            taskQueue.clear();
            interrupt();
        }
    }

    public void join(){
        synchronized (this){
            isClosed = true;
            notifyAll();
        }

        Thread[] threads = new Thread[activeCount()];
        int count = enumerate(threads);
        for(int i=0;i<threads.length;i++){
            try{
                threads[i].join();
            }catch(Exception e){}
        }
    }

    private class WorkThread extends Thread{

        public WorkThread(){
            super(ThreadPool.this,"WorkThread-"+threadID++);
        }

        @Override
        public void run() {
            while(!isInterrupted()){
                Runnable task = null;

                try{
                    task = getTask();
                }catch(Exception e){
                    e.printStackTrace();
                }

                if(task == null) return;

                task.run();

            }
        }
    }





}
