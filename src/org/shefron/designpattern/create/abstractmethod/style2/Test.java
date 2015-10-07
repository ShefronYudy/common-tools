package org.shefron.designpattern.create.abstractmethod.style2;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		IProduct product = CommonFactory.newIns().createProduct(ProductA.class);

		IProduct product2 = CommonFactory.newIns()
				.createProduct(new ProductA());
		System.out.println("#" + ProductA.class + ","
				+ ProductA.class.getName());
	}

}
