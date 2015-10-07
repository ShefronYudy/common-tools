package org.shefron.designpattern.structure.proxy.staticProxy;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HuaxiaAccount account = new HuaxiaAccount();
		ProxyAccount proxy = new ProxyAccount(account);
		proxy.queryAccount();
		proxy.updateAccount();
	}

}
