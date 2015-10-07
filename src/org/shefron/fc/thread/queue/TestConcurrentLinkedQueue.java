package org.shefron.fc.thread.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

public class TestConcurrentLinkedQueue {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// ��������
		ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();
		queue.add("Test TEST");

		ConcurrentQueueHandle handle = new ConcurrentQueueHandle(queue, true);
		// ConcurrentQueueHandle handleWithoutSafe = new
		// ConcurrentQueueHandle(queue,false);
		// �̰߳�ȫ��
		new Thread(handle).start();
		// �̲߳���ȫ
		// new Thread(handleWithoutSafe).start();
		// ��Ϣ1s
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// ��ն���
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
					System.out.println("�ж϶��в�Ϊ��......");
					if (timeoutFlag) {
						try {
							Thread.currentThread().sleep(5 * 1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					return queue.poll();
				}
				return "����Ϊ��";
			}
		}

		public String getElementWithoutThreadSafe(boolean timeoutFlag) {
			try {
				if (!queue.isEmpty()) {
					System.out.println("�ж϶��в�Ϊ��......");
					if (timeoutFlag) {
						try {
							Thread.currentThread().sleep(5 * 1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					return queue.poll();
				}
				return "����Ϊ��";
			} catch (Throwable t) {
				t.printStackTrace();
				return "�����쳣��" + t.getMessage();
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
