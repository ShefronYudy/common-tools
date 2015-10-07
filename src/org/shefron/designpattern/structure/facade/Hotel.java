package org.shefron.designpattern.structure.facade;

public class Hotel {

	public void sleep() {
		preShower();

		System.out.println("Ë¯¾õ£¡");
	}

	public void preShower() {
		System.out.println("Ï´Ôè£¡");
	}

}
