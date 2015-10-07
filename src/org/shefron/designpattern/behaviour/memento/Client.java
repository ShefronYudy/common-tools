package org.shefron.designpattern.behaviour.memento;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Originator o = new Originator();
		o.setState("����");

		Caretaker c = new Caretaker();
		c.saveMemento(o.createMemento());

		System.out.println(o.getState());
		o.setState("���޸�");
		System.out.println(o.getState());
		o.restoreMemento(c.retrieveMemento());
		System.out.println(o.getState());

	}

}
