package org.shefron.designpattern.create.abstractmethod.style2;

public abstract class AbstractFactory {

	public abstract <T extends IProduct> T createProduct(Class<T> c);

}
