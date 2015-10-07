package org.shefron.fc.thread.threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultThreadFactory implements ThreadFactory {

	final ThreadGroup group;
	final AtomicInteger count = new AtomicInteger(0);
	final String namePrefix;

	public DefaultThreadFactory(final ThreadGroup group, final String namePrefix) {
		super();
		this.group = group;
		this.namePrefix = namePrefix;
	}

	public Thread newThread(Runnable r) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(this.namePrefix);
		buffer.append("-");
		buffer.append(this.count.getAndIncrement());

		Thread thread = new Thread(group, r, buffer.toString());
		thread.setDaemon(false);
		thread.setPriority(Thread.NORM_PRIORITY);

		return thread;
	}

}
