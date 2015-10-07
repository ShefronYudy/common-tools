package org.shefron.designpattern.behaviour.memento;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Originator o = new Originator();
		o.setState("正常");

		Caretaker c = new Caretaker();
		c.saveMemento(o.createMemento());

		System.out.println(o.getState());
		o.setState("已修改");
		System.out.println(o.getState());
		o.restoreMemento(c.retrieveMemento());
		System.out.println(o.getState());

	}

}
