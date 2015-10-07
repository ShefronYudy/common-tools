package org.shefron.fc.thread.readwritelock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ģ�⻺����
 */
public class CacheMonitor {

	/**
	 * ����
	 */
	private Map<String, Object> cacheMap = new HashMap<String, Object>();
	/**
	 * ��д��
	 */
	private ReadWriteLock lock = new ReentrantReadWriteLock();

	public Object grab(String key) {
		lock.readLock().lock();// �Ӷ���,�����߳̿ɶ�
		Object value = null;
		try {
			value = cacheMap.get(key);
			if (value == null) {
				lock.readLock().unlock();// �ͷŶ���
				lock.writeLock().lock();// ��д��,�����̲߳��ɶ�
				try {
					value = "new Data";
					cacheMap.put(key, value);
				} finally {
					lock.writeLock().unlock();// �ͷ�д��
				}
				lock.readLock().lock();// �Ӷ���
			}
		} catch (Exception e) {
		} finally {
			lock.readLock().unlock();// �ͷŶ���
		}

		return value;

	}

}
