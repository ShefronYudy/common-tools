package org.shefron.designpattern.create.abstractfactory;

public class RoomWithABomb extends Room {
	private boolean isDisapper = false;

	public void bombing() {
		this.isDisapper = true;
	}

	public boolean isDisapper() {
		return isDisapper;
	}
}
