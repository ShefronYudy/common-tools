package org.shefron.designpattern.structure.bridge;

public class CPPBasicProgramer extends Programer {

	public CPPBasicProgramer(WebProgramer web) {
		this.web = web;
	}

	public void readBooks() {
		web.acceptTechs();
		System.out.println("c++������ƺ����ģʽ");
	}

	public void oop() {
		web.acceptTechs();
		System.out.println("������󿪷���");
	}
}
