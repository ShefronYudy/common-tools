package com.shefron.module.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionHandler {
	final Lock lock = new ReentrantLock();
	final Condition notFull = lock.newCondition();
	final Condition notEmpty = lock.newCondition();

	final Object[] items = new Object[100];
	int putptr, takeptr, count;

	public void put(Object x) throws InterruptedException {
		lock.lock();// 第一步实现互斥
		try {
			while (count == items.length)// 如果已满，阻塞等待
				notFull.await();
			items[putptr] = x;
			if (++putptr == items.length)
				putptr = 0;// 如果putptr已经是数组的最后一个,那么putptr置为0,从第一个开始放
			++count;// 放完后,把总数加一
			notEmpty.signal();// 通知其他线程可以取了
		} finally {
			lock.unlock();
		}
	}

	public Object take() throws InterruptedException {
		lock.lock();
		try {
			while (count == 0)
				notEmpty.await();
			Object x = items[takeptr];
			if (++takeptr == items.length)
				takeptr = 0;
			--count;
			notFull.signal();
			return x;
		} finally {
			lock.unlock();
		}
	}
}
