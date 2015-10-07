package org.shefron.designpattern.structure.bridge;

public class CPPBasicProgramer extends Programer {

	public CPPBasicProgramer(WebProgramer web) {
		this.web = web;
	}

	public void readBooks() {
		web.acceptTechs();
		System.out.println("c++程序设计和设计模式");
	}

	public void oop() {
		web.acceptTechs();
		System.out.println("面向对象开发！");
	}
}
