package org.shefron.designpattern.structure.proxy.staticProxy;

public class HuaxiaAccount extends Account {

	@Override
	public void queryAccount() {
		System.out.println("��ѯ�������� �˺ţ�������");
	}

	@Override
	public void updateAccount() {
		System.out.println("�޸Ļ������� �˺ţ�������");
	}

}
