package org.shefron.fc.thread.timeout;

public class ThreadTimeout {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ThreadDemo demo = new ThreadDemo();
		demo.setDaemon(true);//�ػ��߳�,�����߳̽���������
		demo.start();
		long preTime = System.currentTimeMillis();


		while(true){
			if (System.currentTimeMillis()-preTime > 60*1000){
				System.out.println("��ʱ");
				break;
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
		}


	}



}
