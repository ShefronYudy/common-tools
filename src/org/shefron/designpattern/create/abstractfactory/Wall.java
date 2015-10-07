package org.shefron.designpattern.create.abstractfactory;

public class Wall extends AbstractProduct {

	@SuppressWarnings("unused")
	private long height = 0l;
	@SuppressWarnings("unused")
	private long width = 0l;

	public Wall() {
	}

	public Wall(long height, long width) {
		this.height = height;
		this.width = width;
	}

	public void paintColor() {
	}

}
