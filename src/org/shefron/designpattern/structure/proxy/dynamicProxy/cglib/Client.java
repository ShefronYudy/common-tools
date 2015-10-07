package org.shefron.designpattern.structure.proxy.dynamicProxy.cglib;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ProxyMgr proxy = new ProxyMgr();
		HuaxiaAccount account = (HuaxiaAccount) proxy
				.instance(new HuaxiaAccount());
		account.queryAccount();
		account.updateAccount();
	}

}
