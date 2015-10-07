package com.shefron.test;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * ��ʱ�����ж�
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
				return "��ʱ�������";
			}
		};
		
		Future<String> future = null;
		try{
			future = exec.submit(call);
			String obj = future.get(1000 * 3, TimeUnit.MILLISECONDS);
			System.out.println(obj);
		}catch(TimeoutException e){
			System.out.println("��ʱ���ж� -> ");
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
				System.out.println("�ⲿǿ���ж��߳�!");
				break;
			}
			System.out.println("...");
		}
	}

}
