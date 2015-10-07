package org.shefron.designpattern.structure.facade;

public class Flight {

	public void flight() {
		preOrder();

		System.out.println("飞往目的地！");
	}

	public void preOrder() {
		System.out.println("订机票！");
	}

}
