package org.shefron.designpattern.structure.proxy.staticProxy;

public class ProxyAccount extends Account {

	private Account account = null;

	public ProxyAccount(Account account) {
		this.account = account;
	}

	@Override
	public void queryAccount() {
		this.account.queryAccount();
	}

	@Override
	public void updateAccount() {
		this.account.updateAccount();
	}

}
