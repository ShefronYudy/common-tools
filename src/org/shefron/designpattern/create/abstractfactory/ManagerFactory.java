package org.shefron.designpattern.create.abstractfactory;

public class ManagerFactory {

	@SuppressWarnings("rawtypes")
	public static AbstractFactory createFactory(Class cla) {
		if (cla.equals(NormalFactory.class)) {
			return NormalFactory.newInstance();
		}
		if (cla.equals(BombedFactory.class)) {
			return BombedFactory.newInstance();
		}
		if (cla.equals(EnchantedFactory.class)) {
			return EnchantedFactory.newInstance();
		}
		return NormalFactory.newInstance();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		NormalFactory normal = (BombedFactory) createFactory(BombedFactory.class);
		RoomWithABomb room = (RoomWithABomb) normal
				.createProduct(RoomWithABomb.class.getName());
		room.bombing();
		System.out.println("# RoomWithABomb is open " + room.isDisapper());

	}

}
