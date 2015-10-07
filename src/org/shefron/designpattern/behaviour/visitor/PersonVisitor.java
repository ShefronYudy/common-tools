package org.shefron.designpattern.behaviour.visitor;

public class PersonVisitor implements Visitor {

	public void doWhat(Fruits fruit) {
		System.out.println("The person only eat " + fruit.getClass().getName()
				+ "'s content!");
	}

}
