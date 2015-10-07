package org.shefron.designpattern.behaviour.visitor;

public class BananaFruits implements Fruits {

	public void acceptVisitor(Visitor visitor) {
		visitor.doWhat(this);
	}

}
