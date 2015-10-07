package org.shefron.designpattern.behaviour.memento._memento;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Originator o = new Originator();
		o.setState("1");
		o.setState("2");
		o.setState("3");

		Caretaker c = new Caretaker(o);
		c.saveMemento(o.createMemento());
		for (String state : o.getStates())
			System.out.println("state : " + state);

		System.out.println("=====================");
		o.setState("4");
		o.setState("5");
		c.saveMemento(o.createMemento());

		for (String state : o.getStates())
			System.out.println("state : " + state);

		System.out.println("=====================");
		c.retrieveMemento(0);// 回复到第一步

		for (String state : o.getStates())
			System.out.println("state : " + state);

	}

}
