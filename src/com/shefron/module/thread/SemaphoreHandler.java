package com.shefron.module.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 并发信号量
 */
public class SemaphoreHandler {

	
	public static void main(String[] args) {
		final Semaphore semaphore = new Semaphore(5);
		ExecutorService exec = Executors.newCachedThreadPool();
		for(int i=0; i<20; i++){
			final int NO = i;
			
			Runnable runnable = new Runnable() {
				
				@Override
				public void run() {
					try {
						semaphore.acquire();
						System.out.println("NO="+NO+" acquire");
						TimeUnit.MICROSECONDS.sleep((long)Math.random()*10000);
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						semaphore.release();
						System.out.println("NO="+NO+"----------availablePermits size="+semaphore.availablePermits());
					}
					
				}
			};
			exec.execute(runnable);
		}
		exec.shutdown();
		
		try {
			exec.awaitTermination(200, TimeUnit.SECONDS);
			System.out.println("---------------done.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
