package org.shefron.designpattern.behaviour.memento;

/*
 * ������
 */
public class Originator {

	private String state = "";

	// ��������¼
	public MementoI createMemento() {
		return new Memento(this.state);
	}

	// �ָ�����¼
	public void restoreMemento(MementoI memento) {
		this.setState(((Memento) memento).getState());
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	private class Memento implements MementoI {

		private String state = "";

		private Memento(String state) {
			this.state = state;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}
	}
}
