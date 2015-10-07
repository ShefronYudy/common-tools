package org.shefron.designpattern.create.abstractmethod.style2;

public class CommonFactory extends AbstractFactory {

	private static final CommonFactory factory = new CommonFactory();

	public static CommonFactory newIns() {
		return factory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IProduct> T createProduct(Class<T> c) {
		T product = null;
		try {
			product = (T) Class.forName(c.getName()).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return product;
	}

	public IProduct createProduct(IProduct ipro) {
		IProduct ip = ipro;
		return ip;

	}

}
