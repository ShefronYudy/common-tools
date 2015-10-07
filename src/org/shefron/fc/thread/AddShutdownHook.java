package org.shefron.fc.thread;

public class AddShutdownHook implements Runnable {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Test test!!!");
		// 添加程序关闭钩子
		Runtime.getRuntime().addShutdownHook(new Thread(new AddShutdownHook()));
	}

	public void run() {
		// 程序关闭（异常）：释放所持有的资源
		// handleSth();
		// release();
		System.out.println("释放资源");

	}

}
