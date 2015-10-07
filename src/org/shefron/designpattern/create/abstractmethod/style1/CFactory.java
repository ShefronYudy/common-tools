package org.shefron.designpattern.create.abstractmethod.style1;

public class CFactory extends AbstractFactory {

	@Override
	public IProduct createProduct() {
		return new ProductC();
	}

}
