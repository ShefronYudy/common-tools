package org.shefron.fc.thread;

public class AddShutdownHook implements Runnable {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Test test!!!");
		// ��ӳ���رչ���
		Runtime.getRuntime().addShutdownHook(new Thread(new AddShutdownHook()));
	}

	public void run() {
		// ����رգ��쳣�����ͷ������е���Դ
		// handleSth();
		// release();
		System.out.println("�ͷ���Դ");

	}

}
