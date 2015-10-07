package org.shefron.designpattern.behaviour.visitor;

public class AppleFruits implements Fruits {

	public void acceptVisitor(Visitor visitor) {
		visitor.doWhat(this);
	}

}
