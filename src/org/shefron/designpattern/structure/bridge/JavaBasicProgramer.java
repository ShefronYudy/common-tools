package org.shefron.designpattern.structure.bridge;

/**
 * java底层开发
 * 
 * @author a
 * 
 */
public class JavaBasicProgramer extends Programer {

	public JavaBasicProgramer(WebProgramer web) {
		this.web = web;
	}

	//
	public void readBooks() {
		web.acceptTechs();
		System.out.println("java编程思想和设计模式");
	}

	public void oop() {
		web.acceptTechs();
		System.out.println("面向对象开发！");
	}
}
