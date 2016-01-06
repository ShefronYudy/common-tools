package com.shefron.module.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchHandler {
	


	public static void main(String[] args) {
		int PlayerCount = 20;
		CountDownLatch begin = new CountDownLatch(1);
		CountDownLatch end = new CountDownLatch(PlayerCount);
		
		ExecutorService executorService = Executors.newFixedThreadPool(PlayerCount);
		
		Player player = null;
		for(int i=1; i<=20; i++){
			player = new Player(i, begin, end);
			executorService.execute(player);
		}
		begin.countDown();//比赛开始
		executorService.shutdown();
		
		try{
//			executorService.awaitTermination(200, TimeUnit.SECONDS);
			end.await();
			System.out.println(executorService.isShutdown() );
		}catch(Exception e){
			e.printStackTrace();
		}
		

	}

}

class Player implements Runnable{
	private int id;     
	private CountDownLatch begin;     
	private CountDownLatch end;
		
	public Player(int i, CountDownLatch begin, CountDownLatch end){
		this.id = i;
		this.begin = begin;
		this.end = end;
	}

	@Override
	public void run() {
		try{
			begin.await();
			Thread.sleep((long)Math.random()*10000);
			System.out.println("Player "+ this.id+ " done.");
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			end.countDown();
		}
		
	}
	
}
