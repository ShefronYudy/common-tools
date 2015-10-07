package org.shefron.utils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * ¼ÆÊýÆ÷
 * 
 * @author a
 * 
 */
public class Counter {

	AtomicLong count = new AtomicLong(0);

	public synchronized long getAndIncrement() {
		return count.getAndIncrement();
	}

	public synchronized long getAndDecrement() {
		if (count.get() < 0L) {
			return 0l;
		}
		return count.getAndDecrement();
	}

	public synchronized void reset() {
		count.set(0L);
	}

	public synchronized long get() {
		return count.get();
	}

}
