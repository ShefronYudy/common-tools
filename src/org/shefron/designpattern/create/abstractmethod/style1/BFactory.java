package org.shefron.designpattern.create.abstractmethod.style1;

public class BFactory extends AbstractFactory {

	@Override
	public IProduct createProduct() {
		return new ProductB();
	}

}
