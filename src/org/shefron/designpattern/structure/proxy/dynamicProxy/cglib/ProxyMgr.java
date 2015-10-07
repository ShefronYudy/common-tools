package org.shefron.designpattern.structure.proxy.dynamicProxy.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ProxyMgr implements MethodInterceptor {
	// ��Ҫ�����Ŀ��
	private Object target = null;

	public Object instance(Object target) {
		this.target = target;
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(this.target.getClass());

		enhancer.setCallback(this);

		return enhancer.create();
	}

	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		System.out.println("cglib �������ʼ��");
		proxy.invokeSuper(obj, args);
		System.out.println("cglib ������������");
		return null;
	}

}
