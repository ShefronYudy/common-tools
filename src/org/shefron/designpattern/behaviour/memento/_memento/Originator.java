package org.shefron.designpattern.behaviour.memento._memento;

import java.util.ArrayList;
import java.util.List;

/*
 * 发起者
 */
public class Originator {

	private List<String> states = null;// 表是n多属性
	private int checkPoint;// 检查点

	public Originator() {
		this.states = new ArrayList<String>();
		this.checkPoint = 0;
	}

	// 创建备忘录
	public Memento createMemento() {
		return new Memento(this.states, this.checkPoint);
	}

	// 恢复备忘录
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
