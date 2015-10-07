package org.shefron.designpattern.behaviour.visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试类未使用泛型适合jdk1.4
 * 
 * @author a
 * 
 */
public class Test {

	@SuppressWarnings( { "rawtypes", "unchecked" })
	private List<Fruits> list = new ArrayList();
	private Visitor visitor = null;

	public Test(Visitor visitor) {
		this.visitor = visitor;
	}

	public void addFruits() {
		for (int i = 0; i < 10; i++) {
			list.add(FruitsFactory.createFruits());
		}
	}

	@SuppressWarnings("rawtypes")
	public List getFruits() {
		return list;
	}

	public Visitor getVisitor() {
		return visitor;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Test test = new Test(new PersonVisitor());
		test.addFruits();
		for (Object fruit : test.getFruits()) {
			Fruits _fruit = (Fruits) fruit;
			_fruit.acceptVisitor(test.getVisitor());

		}
	}

}
