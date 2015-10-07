package org.shefron.fc.thread.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TestBlockingQueue {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 阻塞队列
		BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
		// 添加元素
		new Thread(new SingletonThread(queue)).start();

		while (true) {
			// if (queue.isEmpty()){
			// continue;
			// }
			// 消费元素
			String item;
			try {
				item = queue.take();
				System.out.println("get----" + item);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

	private static class SingletonThread implements Runnable {

		BlockingQueue<String> queue = null;

		public SingletonThread(BlockingQueue<String> queue) {
			this.queue = queue;
		}

		public void run() {
			for (int i = 0; i < Integer.MAX_VALUE; i++) {
				System.out.println("put----seq:" + i + ",hehe");
				queue.add("seq:" + i + ",hehe");
				try {
					Thread.currentThread().sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
