package org.shefron.fc.thread.timeout;

public class ThreadDemo extends Thread {


	public void run() {
		while(true){
			System.out.println("11111111111111111");
			try {
				Thread.sleep(1000*1);
			} catch (InterruptedException e) {
			}
		}
	}

}
