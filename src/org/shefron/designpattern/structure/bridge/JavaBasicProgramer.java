package org.shefron.designpattern.structure.bridge;

/**
 * java�ײ㿪��
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
		System.out.println("java���˼������ģʽ");
	}

	public void oop() {
		web.acceptTechs();
		System.out.println("������󿪷���");
	}
}
