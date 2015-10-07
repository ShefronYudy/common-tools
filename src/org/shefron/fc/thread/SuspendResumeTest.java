package org.shefron.fc.thread;

/**
 * 注意 sleep()，suspend()以及resume()都能在不 同步的方法内调用，因为它们不需要对锁定进行操作。
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
				System.out.println("子线程处理中...");
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			thread.resume();
			System.out.println("子线程处理完毕，主线程恢复吧");
		}
	}

	@SuppressWarnings("deprecation")
	public static void main(String[] agrs) {
		SuspendResumeTest test = new SuspendResumeTest();
		Thread t = Thread.currentThread();// 主线程
		ThreadSon son = test.new ThreadSon(t);
		son.start();
		System.out.println("主线程进入等待状态");
		System.out.println("主线程开始等待...");
		t.suspend();
		System.out.println("主线程接收到恢复命令，开始执行后续工作");
		try {
			Thread.sleep(5000);
			System.out.println("处理完毕");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("结束主线程");
	}

}
