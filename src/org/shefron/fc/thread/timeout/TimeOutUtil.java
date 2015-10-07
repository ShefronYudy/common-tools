package org.shefron.fc.thread.timeout;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TimeOutUtil {


	public Object callMethodWithTimeout(final Object obj,final String methodName,final Object[] paramObjects,int second) throws Exception{
		/*
		 * ����˵����
		 * second:���ó�ʱ��ʱ�䣬��λ��
		 * obj:����ʵ��
		 * paramObjects:��������
		 * */
		ExecutorService executor = Executors.newSingleThreadExecutor();
		FutureTask<Object> future = new FutureTask<Object>(new Callable<Object>() {
					@SuppressWarnings("unchecked")
					public Object call() throws Exception {
						Class clz = obj.getClass();
						Class[] paramTypes = new Class[paramObjects.length];
						for(int i=0;i<paramObjects.length;i++){
							Object obj = paramObjects[i];
							if(obj!=null){
								paramTypes[i]=obj.getClass();
							}else{
								paramTypes[i]=null;
							}
						}
						Method method = clz.getMethod(methodName, paramTypes);
						Object returnobj = method.invoke(obj, paramObjects);
						return returnobj;
					}
				});
		executor.execute(future);
		try {
			return future.get(second*1000, TimeUnit.MILLISECONDS);
		} catch (TimeoutException e) {
			future.cancel(true);
			throw new TimeoutException("���÷�����ʱ��������ֵ"+second+"��");
		} catch (Exception e) {
			future.cancel(true);
			throw new Exception(e);
		} finally {
		    executor.shutdown();
		}
	}
}
