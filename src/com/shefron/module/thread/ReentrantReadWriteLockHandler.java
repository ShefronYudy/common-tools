package com.shefron.module.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**
 * 读写锁：
 * <p>适合读多写少的并发环境</p> 
 */
public class ReentrantReadWriteLockHandler {
	private static Random random = new Random();
	
	private static ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
	private static ReadLock rLock = rwLock.readLock();
	private static WriteLock wLock = rwLock.writeLock();
	
	private static Map<String,String> dataMap = new HashMap<String,String>();
	
	private static CountDownLatch countDownLatch = new CountDownLatch(102);
	private static CyclicBarrier barrier = new CyclicBarrier(102);
	
	public static void main(String[] args){
		long beginTime = System.currentTimeMillis();
		for(int i=0; i<100; i++){
			new Thread(new WriteThread()).start();
		}
		
		for(int i=0; i<2; i++){
			new Thread(new ReadThread()).start();
		}
		
		try {
			countDownLatch.await();//wait all thread to end
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		long endTime = System.currentTimeMillis();
		
		System.out.println("cost : "+(endTime - beginTime) + " ms");
	}
	
	static class WriteThread implements Runnable{

		@Override
		public void run() {
			try {
				barrier.await();//wait until call all await
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
			
			try{
				wLock.lock();
				
				dataMap.put("1", String.valueOf(random.nextDouble())+"#"+String.valueOf(random.nextLong()));
				Thread.sleep(100);
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				wLock.unlock();
			}
			countDownLatch.countDown();
		}
		
	}
	
	static class ReadThread implements Runnable{

		@Override
		public void run() {
			try {
				barrier.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
			
			try{
				rLock.lock();
				System.out.println(dataMap.get("1"));
				Thread.sleep(100);
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				rLock.unlock();
			}
			countDownLatch.countDown();
		}
		
	}
}
