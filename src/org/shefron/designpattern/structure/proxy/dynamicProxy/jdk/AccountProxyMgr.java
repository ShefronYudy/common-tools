package org.shefron.designpattern.structure.proxy.dynamicProxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class AccountProxyMgr implements InvocationHandler {

	private Object target = null;

	public Object getProxyByBind(Object obj) {
		this.target = obj;
		return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj
				.getClass().getInterfaces(), this);
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println("代理事物开始！");
		Object obj = method.invoke(target, args);
		System.out.println("代理事物结束！");
		return obj;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AccountProxyMgr mgr = new AccountProxyMgr();
		HuaxiaAccount account = new HuaxiaAccount();
		// 动态代理交由mgr生成
		Account proxy = (Account) mgr.getProxyByBind(account);
		proxy.queryAccount();
		proxy.updateAccount();
	}

}
