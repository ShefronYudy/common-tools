package org.shefron.designpattern.behaviour.memento._memento;

import java.util.ArrayList;
import java.util.List;

/**
 * ¸ºÔðÈË
 * 
 * @author a
 * 
 */

public class Caretaker {

	private Originator o = null;
	private List<Memento> memos = new ArrayList<Memento>();
	private int current;

	public Caretaker(Originator o) {
		this.o = o;
		this.current = 0;
	}

	public void saveMemento(Memento memento) {
		this.memos.add(memento);
		this.current++;
	}

	public void retrieveMemento(int index) {
		o.restoreMemento(this.memos.get(index));
	}

	// public void removeMemento(int index){
	// this.memos.remove(index);
	// }

}
