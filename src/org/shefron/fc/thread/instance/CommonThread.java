package org.shefron.fc.thread.instance;

import java.util.Date;

public class CommonThread extends Thread {

	@Override
	public void run() {
		try{
			Thread.currentThread().sleep(1*1000);
		}catch(Exception e){}
		System.out.print(new Date()+"sub thread:");
		DBDaoInstance.getInstance().operate();
	}

}
