package org.shefron.designpattern.behaviour.visitor;

public class AnimalVisitor implements Visitor {

	public void doWhat(Fruits fruit) {
		System.out.println("The animal eat " + fruit.getClass().getName()
				+ "with nothing!");
	}

}
