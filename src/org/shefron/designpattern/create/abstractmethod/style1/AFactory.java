package org.shefron.designpattern.create.abstractmethod.style1;

public class AFactory extends AbstractFactory {

	private static final AFactory instance = new AFactory();

	public static AFactory newIns() {
		return instance;
	}

	@Override
	public IProduct createProduct() {
		return new ProductA();
	}

}
