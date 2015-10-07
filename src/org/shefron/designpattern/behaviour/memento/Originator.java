package org.shefron.designpattern.behaviour.memento;

/*
 * 发起者
 */
public class Originator {

	private String state = "";

	// 创建备忘录
	public MementoI createMemento() {
		return new Memento(this.state);
	}

	// 恢复备忘录
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
