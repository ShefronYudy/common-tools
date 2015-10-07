package org.shefron.fc.thread.threadpool;

import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool implements ThreadFactory {

	private int corePoolSize = 10;
	private int maxPoolSize = Integer.MAX_VALUE;

	private static boolean shutDown;
	protected ThreadPoolExecutor executor;

	public ThreadPool() {
		setExecutor(createThreadPoolExecutor("Custom Task", true,
				Thread.NORM_PRIORITY));
	}

	public ThreadPool(int corePoolSize, int maxPoolSize) {
		this.corePoolSize = corePoolSize;
		this.maxPoolSize = maxPoolSize;
		setExecutor(createThreadPoolExecutor("Custom Task", true,
				Thread.NORM_PRIORITY));

	}

	public void execute(Runnable runnable) {
		if (!shutDown) {
			this.executor.execute(runnable);
		}

	}

	public void forceShutdown() {
		this.executor.shutdown();
	}

	public void safeShutdown() {
		synchronized (this) {
			shutDown = true;
		}
		this.executor.shutdown();
	}

	public void setExecutor(ThreadPoolExecutor executor) {
		this.executor = executor;
	}

	public ThreadPoolExecutor getExecutor() {
		return executor;
	}

	protected ThreadPoolExecutor createThreadPoolExecutor(final String name,
			final boolean daemon, final int priority) {

		ThreadPoolExecutor Te = null;

		if (maxPoolSize == Integer.MAX_VALUE) {
			Te = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 10,
					TimeUnit.SECONDS, new SynchronousQueue(),
					new DefaultThreadFactory(name, daemon, priority));
		} else {
			Te = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 10,
					TimeUnit.SECONDS, new LinkedBlockingQueue(),
					new DefaultThreadFactory(name, daemon, priority));
		}
		return Te;

	}

	private static class DefaultThreadFactory implements
			java.util.concurrent.ThreadFactory {

		private final String name;
		private final boolean daemon;
		private final int priority;

		public DefaultThreadFactory(String name, boolean daemon, int priority) {
			this.name = name;
			this.daemon = daemon;
			this.priority = priority;
		}

		public Thread newThread(final Runnable r) {

			Thread newThread = null;
			try {
				newThread = (Thread) AccessController
						.doPrivileged(new PrivilegedExceptionAction<Thread>() {
							public Thread run() throws Exception {
								Thread thread = new Thread(r, name);

								thread.setDaemon(daemon);
								thread.setPriority(priority);

								return thread;
							}

						});
			} catch (PrivilegedActionException e) {
				e.printStackTrace();
			}

			return newThread;
		}

	}

}
