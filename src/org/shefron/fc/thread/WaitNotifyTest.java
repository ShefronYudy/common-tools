package org.shefron.fc.thread;

/**
 * 只能为自己的锁定调用 wait()和 notify()其他：yield()暂停执行当前线程，去执行其他行程
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
				System.out.println("子线程处理中...");
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			synchronized (thread) {
				thread.notify();
			}
			System.out.println("子线程处理完毕，主线程恢复吧");
		}
	}

	public static void main(String[] agrs) {
		WaitNotifyTest test = new WaitNotifyTest();
		Thread t = Thread.currentThread();// 主线程
		ThreadSon son = test.new ThreadSon(t);
		son.start();
		System.out.println("主线程进入等待状态");
		System.out.println("主线程开始等待...");
		try {
			synchronized (t) {
				t.wait();
			}
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println("主线程接收到恢复命令，开始执行后续工作");
		try {
			Thread.sleep(5000);
			System.out.println("主线程处理完毕");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("结束主线程");
	}

}
