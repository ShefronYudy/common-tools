package org.shefron.designpattern.structure.proxy.staticProxy;

public class HuaxiaAccount extends Account {

	@Override
	public void queryAccount() {
		System.out.println("查询华夏银行 账号！！！！");
	}

	@Override
	public void updateAccount() {
		System.out.println("修改华夏银行 账号！！！！");
	}

}
