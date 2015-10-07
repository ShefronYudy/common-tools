package org.shefron.designpattern.create.abstractfactory;

public class BombedFactory extends NormalFactory {

	private static final BombedFactory factory = new BombedFactory();

	static BombedFactory newInstance() {
		return factory;
	}

	@Override
	Wall createWall() {
		return new BombedWall();
	}

	@Override
	Room createRoom() {
		return new RoomWithABomb();
	}

}
