package com.shefron.module.rmi.concurrency;

/**
 * Created by Administrator on 2014/12/1.
 */
public class SimpleClient {

    class Producer extends Thread{
        private Stack theStack = null;

        public Producer(Stack stack, String name){
            super(name);
            theStack = stack;
        }

        public void run(){
            String goods ;
            try {
                for (;;){
                    synchronized (theStack){
                        goods = "goods "+ (theStack.getPoint()+1);
                        theStack.push(goods);
                    }
                    System.out.println(getName()+":push "+goods+" to "+ theStack.getName());
                    try {
                        Thread.sleep(500);
                    }catch (Exception e){e.printStackTrace();}
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    class Consumer extends Thread{
        private Stack theStack;

        public Consumer(Stack stack,String name){
            super(name);
            this.theStack = stack;
            start();
        }

        public void run(){
            String goods;
            try {
                for (;;) {
                    goods = theStack.pop();
                    System.out.println(getName()+":pop "+goods+" from "+theStack.getName());

                    try {
                        Thread.sleep(400);
                    }catch (Exception e){e.printStackTrace();}
                }



            }catch (Exception e){
                e.printStackTrace();
            }



        }


    }
}
