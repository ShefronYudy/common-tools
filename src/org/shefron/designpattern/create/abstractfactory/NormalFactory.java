package org.shefron.designpattern.create.abstractfactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class NormalFactory extends AbstractFactory {

	private static final NormalFactory factory = new NormalFactory();

	static NormalFactory newInstance() {
		return factory;
	}

	@Override
	Wall createWall() {
		return new Wall();
	}

	@Override
	Room createRoom() {
		return new Room();
	}

	@Override
	Door createDoor() {
		return new Door();
	}

	@SuppressWarnings("rawtypes")
	@Override
	AbstractProduct createProduct(String className) {
		AbstractProduct product = null;
		try {
			Constructor constructor = Class.forName(className).getConstructor();
			product = (AbstractProduct) constructor.newInstance();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return product;
	}

}
