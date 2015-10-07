package org.shefron.fc.thread;

public class SyncTest {
	class Thread2 extends Thread {
		public boolean flag = false;

		public Thread2(boolean flag) {
			this.flag = flag;
		}

		private int count = 0;

		public synchronized void run() {
			System.out.println("run() method" + flag);
			count++;
			if (flag) {
				add();
			}
			try {
				Thread.currentThread();
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("run 3 seconds has done" + count + flag);
		}

		public synchronized void add() {
			System.out.println("add() method" + flag);
			count++;
			try {
				Thread.currentThread();
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("add 3 seconds has done " + count + flag);
		}

		public synchronized void doAdd(Thread2 t) {
			t.add();
		}
	}

	public static void main(String args[]) {
		SyncTest sync = new SyncTest();
		Thread2 thread1 = sync.new Thread2(false);
		Thread2 thread2 = sync.new Thread2(true);
		thread1.start();
		thread2.doAdd(thread1);

	}

}
