package org.shefron.designpattern.create.abstractfactory;

public class EnchantedFactory extends NormalFactory {

	private static final EnchantedFactory factory = new EnchantedFactory();

	static EnchantedFactory newInstance() {
		return factory;
	}

	@Override
	Wall createWall() {
		return new EnchantedWall();
	}

	@Override
	Room createRoom() {
		return new EnchantedRoom();
	}

	@Override
	Door createDoor() {
		return new EnchantedDoor();
	}

}
