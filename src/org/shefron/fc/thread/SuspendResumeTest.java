package org.shefron.fc.thread;

/**
 * ע�� sleep()��suspend()�Լ�resume()�����ڲ� ͬ���ķ����ڵ��ã���Ϊ���ǲ���Ҫ���������в�����
 * 
 * @author a
 * 
 */
public class SuspendResumeTest {
	class ThreadSon extends Thread {
		private Thread thread = null;

		public ThreadSon(Thread t) {
			this.thread = t;
		}

		@SuppressWarnings("deprecation")
		public void run() {
			try {
				System.out.println("���̴߳�����...");
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			thread.resume();
			System.out.println("���̴߳�����ϣ����ָ̻߳���");
		}
	}

	@SuppressWarnings("deprecation")
	public static void main(String[] agrs) {
		SuspendResumeTest test = new SuspendResumeTest();
		Thread t = Thread.currentThread();// ���߳�
		ThreadSon son = test.new ThreadSon(t);
		son.start();
		System.out.println("���߳̽���ȴ�״̬");
		System.out.println("���߳̿�ʼ�ȴ�...");
		t.suspend();
		System.out.println("���߳̽��յ��ָ������ʼִ�к�������");
		try {
			Thread.sleep(5000);
			System.out.println("�������");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("�������߳�");
	}

}
