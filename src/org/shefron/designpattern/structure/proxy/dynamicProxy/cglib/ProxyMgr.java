package org.shefron.designpattern.structure.proxy.dynamicProxy.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ProxyMgr implements MethodInterceptor {
	// 需要代理的目标
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
		System.out.println("cglib 事物代理开始！");
		proxy.invokeSuper(obj, args);
		System.out.println("cglib 事物代理结束！");
		return null;
	}

}
