package org.shefron.designpattern.behaviour.memento;

/**
 * ������
 * 
 * @author a
 * 
 */

public class Caretaker {

	private MementoI memento = null;

	public void saveMemento(MementoI memento) {
		this.memento = memento;
	}

	public MementoI retrieveMemento() {
		return this.memento;
	}

}
