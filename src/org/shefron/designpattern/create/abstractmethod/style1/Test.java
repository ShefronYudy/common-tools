package org.shefron.designpattern.create.abstractmethod.style1;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		IProduct product = AFactory.newIns().createProduct();
	}

}
