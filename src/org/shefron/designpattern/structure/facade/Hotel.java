package org.shefron.designpattern.structure.facade;

public class Hotel {

	public void sleep() {
		preShower();

		System.out.println("˯����");
	}

	public void preShower() {
		System.out.println("ϴ�裡");
	}

}
