package org.shefron.designpattern.structure.facade;

public class Flight {

	public void flight() {
		preOrder();

		System.out.println("����Ŀ�ĵأ�");
	}

	public void preOrder() {
		System.out.println("����Ʊ��");
	}

}
