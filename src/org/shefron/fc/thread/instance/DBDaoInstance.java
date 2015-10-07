package org.shefron.fc.thread.instance;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

public class DBDaoInstance {

	private static DBDaoInstance instance = null;

	private final Logger logger = Logger.getLogger(DBDaoInstance.class);

	private AtomicInteger seq = new AtomicInteger(0);

	private DBDaoInstance(){
	}

	public static synchronized DBDaoInstance getInstance(){
		if (instance == null){
			instance = new DBDaoInstance();
		}
		return instance;
	}

	/**
	 * 模拟河南运维月报插入两条记录
	 */
	public synchronized void operate(){
		int i = seq.getAndIncrement();
		logger.debug("start = "+i);

		try{
			Thread.sleep(5*1000);
		}catch(Exception e){
			e.printStackTrace();
		}

		logger.debug("seq="+i+",插入一条999999");

	}

	public static void main(String[] args){
		DBDaoInstance.getInstance().operate();

		CommonThread thread = new CommonThread();
		thread.start();

		try{
			Thread.sleep(3*1000);
		}catch(Exception e){
		}
		System.out.println("wait");
		DBDaoInstance.getInstance().operate();
	}






}
