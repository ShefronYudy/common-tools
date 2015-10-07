package org.shefron.designpattern.structure.decorator;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Decorator decorator = new SoundDecorator(new Button());
		decorator.operation();
	}

}
