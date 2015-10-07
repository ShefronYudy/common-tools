package org.shefron.designpattern.create.abstractfactory;

public class BombedWall extends Wall {
	@SuppressWarnings("unused")
	private boolean isBombed = false;

	public void bombWall() {
		isBombed = true;
	}

}
