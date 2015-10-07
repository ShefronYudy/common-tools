package org.shefron.designpattern.structure.flightWeight;

import java.awt.Color;

/**
 * ƤЬ
 * 
 * @author a
 * 
 */
public class LeatherShoe extends Shoe {

	private String position = "";

	public LeatherShoe(Color color, int size, String producePlace,
			String position) {
		this.color = color;
		this.size = size;
		this.producePlace = producePlace;
		this.position = position;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getPosition() {
		return position;
	}

	public void setProducePlace(String producePlace) {
		this.producePlace = producePlace;
	}

	public String getProducePlace() {
		return producePlace;
	}

	public void setPosition(String position) {
		this.position = position;
	}

}
