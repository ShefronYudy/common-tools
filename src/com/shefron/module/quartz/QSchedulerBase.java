package com.shefron.module.quartz;

import java.io.Serializable;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * version quartz 2.2.2
 * scheduler manager
 */
public abstract class QSchedulerBase<K, V> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected static final Logger log = LoggerFactory.getLogger(QSchedulerBase.class);
	/** 正在运行的调度任务 */
	private final ConcurrentHashMap<K, V> runningJob = new ConcurrentHashMap<K, V>();
	/** 调度工厂 */
	private static final StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
	static {
		Properties props = new Properties();
		//设置最大线程数
		props.put("org.quartz.threadPool.threadCount", "50");
		props.put("org.quartz.threadPool.threadPriority", "5");
		props.put("org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread", "true");
		props.put("org.quartz.threadPool.class","org.quartz.simpl.SimpleThreadPool");
		props.put("org.quartz.jobStore.misfireThreshold", "60000");
		props.put("org.quartz.jobStore.class", "org.quartz.simpl.RAMJobStore");

		props.put("org.quartz.scheduler.instanceName","BackupVMServiceQuartzScheduler");
		props.put("org.quartz.scheduler.rmi.export", "false");
		props.put("org.quartz.scheduler.rmi.proxy", "false");
		props.put("org.quartz.scheduler.wrapJobExecutionInUserTransaction","false");

		try {
			schedulerFactory.initialize(props);
		} catch (SchedulerException e) {
			log.error("初始化Quartz参数错误!", e);
		}
	}
	
	protected final String DEFAULT_JOB_GROUP_NAME = "BACKUPVMSERVICE_JOB_GROUPNAME";
	protected final String DEFAULT_TRIGGER_GROUP_NAME = "BACKUPVMSERVICE_TRIGGER_GROUPNAME";
	
	protected final String DEFAULT_JOBKEY_SUFFIX = "-job";
	protected final String DEFAULT_TRIGGERKEY_SUFFIX = "-trigger";
	
	/**
	 * 获取正在运行的JOB任务
	 * @return
	 */
	public final synchronized ConcurrentHashMap<K, V> getRunningJobMap(){
		return runningJob;
	}
	
	/**
	 * 调度器工厂类
	 * @return
	 */
	public final synchronized StdSchedulerFactory getStdSchedulerFactory(){
		return this.schedulerFactory;
	}
	
	/**
	 * 删除JOB任务
	 * @param key
	 */
	protected abstract void deleteQuartzJob(K key);
	
	/**
	 * 添加JOB任务
	 * @param key
	 * @param value
	 */
	protected abstract void addQuartzJob(K key, V value);
	
	/**
	 * 更新JOB任务
	 * @param key
	 * @param value
	 */
	protected abstract void updateQuartzJob(K key, V value);
	
}

