package org.shefron.designpattern.behaviour.visitor;

public class OrangeFruits implements Fruits {

	public void acceptVisitor(Visitor visitor) {
		visitor.doWhat(this);
	}

}
