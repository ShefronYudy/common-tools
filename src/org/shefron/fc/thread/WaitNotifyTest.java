package org.shefron.fc.thread;

/**
 * ֻ��Ϊ�Լ����������� wait()�� notify()������yield()��ִͣ�е�ǰ�̣߳�ȥִ�������г�
 * 
 * @author a
 * 
 */
public class WaitNotifyTest {
	class ThreadSon extends Thread {
		private Thread thread = null;

		public ThreadSon(Thread t) {
			this.thread = t;
		}

		public void run() {
			try {
				System.out.println("���̴߳�����...");
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			synchronized (thread) {
				thread.notify();
			}
			System.out.println("���̴߳�����ϣ����ָ̻߳���");
		}
	}

	public static void main(String[] agrs) {
		WaitNotifyTest test = new WaitNotifyTest();
		Thread t = Thread.currentThread();// ���߳�
		ThreadSon son = test.new ThreadSon(t);
		son.start();
		System.out.println("���߳̽���ȴ�״̬");
		System.out.println("���߳̿�ʼ�ȴ�...");
		try {
			synchronized (t) {
				t.wait();
			}
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println("���߳̽��յ��ָ������ʼִ�к�������");
		try {
			Thread.sleep(5000);
			System.out.println("���̴߳������");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("�������߳�");
	}

}
