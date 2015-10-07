package org.shefron.fc.thread.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

public class TestConcurrentLinkedQueue {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 并发队列
		ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();
		queue.add("Test TEST");

		ConcurrentQueueHandle handle = new ConcurrentQueueHandle(queue, true);
		// ConcurrentQueueHandle handleWithoutSafe = new
		// ConcurrentQueueHandle(queue,false);
		// 线程安全的
		new Thread(handle).start();
		// 线程不安全
		// new Thread(handleWithoutSafe).start();
		// 休息1s
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 清空队列
		handle.getElement(false);
		// handleWithoutSafe.getElementWithoutThreadSafe(false);
	}

	private static class ConcurrentQueueHandle implements Runnable {
		ConcurrentLinkedQueue<String> queue = null;
		boolean isThreadSafe = true;

		public ConcurrentQueueHandle(ConcurrentLinkedQueue<String> queue,
				boolean isThreadSafe) {
			this.queue = queue;
			this.isThreadSafe = isThreadSafe;
		}

		public void addElement(String element) {
			synchronized (this) {
				this.queue.add(element);
			}
		}

		public void addElementWithoutThreadsafe(String element) {
			this.queue.add(element);
		}

		public String getElement(boolean timeoutFlag) {
			synchronized (this) {
				if (!queue.isEmpty()) {
					System.out.println("判断队列不为空......");
					if (timeoutFlag) {
						try {
							Thread.currentThread().sleep(5 * 1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					return queue.poll();
				}
				return "队列为空";
			}
		}

		public String getElementWithoutThreadSafe(boolean timeoutFlag) {
			try {
				if (!queue.isEmpty()) {
					System.out.println("判断队列不为空......");
					if (timeoutFlag) {
						try {
							Thread.currentThread().sleep(5 * 1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					return queue.poll();
				}
				return "队列为空";
			} catch (Throwable t) {
				t.printStackTrace();
				return "出现异常：" + t.getMessage();
			}
		}

		public void run() {
			if (this.isThreadSafe) {
				System.out.println(getElement(true));
			} else {
				System.out.println(getElementWithoutThreadSafe(true));
			}

		}

	}

}
