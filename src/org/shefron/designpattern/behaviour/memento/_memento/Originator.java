package org.shefron.designpattern.behaviour.memento._memento;

import java.util.ArrayList;
import java.util.List;

/*
 * ������
 */
public class Originator {

	private List<String> states = null;// ����n������
	private int checkPoint;// ����

	public Originator() {
		this.states = new ArrayList<String>();
		this.checkPoint = 0;
	}

	// ��������¼
	public Memento createMemento() {
		return new Memento(this.states, this.checkPoint);
	}

	// �ָ�����¼
	public void restoreMemento(Memento memento) {
		this.states = memento.getStates();
		this.checkPoint = memento.getIndex();
	}

	public List<String> getStates() {
		return states;
	}

	public void setState(String state) {
		this.states.add(state);
		this.checkPoint++;
	}

}
