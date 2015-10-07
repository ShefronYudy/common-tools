package org.shefron.fc.thread.readwritelock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 模拟缓存器
 */
public class CacheMonitor {

	/**
	 * 缓存
	 */
	private Map<String, Object> cacheMap = new HashMap<String, Object>();
	/**
	 * 读写锁
	 */
	private ReadWriteLock lock = new ReentrantReadWriteLock();

	public Object grab(String key) {
		lock.readLock().lock();// 加读锁,其他线程可读
		Object value = null;
		try {
			value = cacheMap.get(key);
			if (value == null) {
				lock.readLock().unlock();// 释放读锁
				lock.writeLock().lock();// 加写锁,其他线程不可读
				try {
					value = "new Data";
					cacheMap.put(key, value);
				} finally {
					lock.writeLock().unlock();// 释放写锁
				}
				lock.readLock().lock();// 加读锁
			}
		} catch (Exception e) {
		} finally {
			lock.readLock().unlock();// 释放读锁
		}

		return value;

	}

}
