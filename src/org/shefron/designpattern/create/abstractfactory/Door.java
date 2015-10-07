package org.shefron.designpattern.create.abstractfactory;

public class Door extends AbstractProduct {

	private boolean isOpen = false;

	public boolean isOpen() {
		return isOpen;
	}

	public void handleDoor(boolean flag) {
		this.isOpen = flag;
	}
}
