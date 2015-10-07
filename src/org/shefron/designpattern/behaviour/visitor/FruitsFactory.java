package org.shefron.designpattern.behaviour.visitor;

import java.util.Random;

public class FruitsFactory {

	private static final FruitsFactory instance = new FruitsFactory();

	public FruitsFactory() {
	}

	public static FruitsFactory newInstance() {
		return instance;
	}

	public static Fruits createFruits() {
		Random ran = new Random();

		switch (ran.nextInt(3)) {
		case 0:
			return new AppleFruits();
		case 1:
			return new BananaFruits();
		case 2:
			return new OrangeFruits();
		}
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Random ran = new Random();
		for (int i = 0; i < 20; i++)
			System.out.println(ran.nextInt(3));
	}

}
