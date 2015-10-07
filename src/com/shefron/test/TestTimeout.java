package com.shefron.test;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * 超时方法判断
 */
public class TestTimeout {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final java.util.concurrent.ExecutorService exec = Executors.newFixedThreadPool(1);
		
		Callable<String> call = new Callable<String>(){
			public String call() throws Exception {
				testMethod();
				return "超时处理完成";
			}
		};
		
		Future<String> future = null;
		try{
			future = exec.submit(call);
			String obj = future.get(1000 * 3, TimeUnit.MILLISECONDS);
			System.out.println(obj);
		}catch(TimeoutException e){
			System.out.println("超时后中断 -> ");
			System.out.println("interrupt flag:"+future.cancel(true) );
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			exec.shutdown();
		}
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		

	}
	
	public static void testMethod(){
		while(true){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				System.out.println("外部强制中断线程!");
				break;
			}
			System.out.println("...");
		}
	}

}
