package org.shefron.designpattern.behaviour.memento._memento;

import java.util.ArrayList;
import java.util.List;

public class Memento {

	private List<String> states = null;// 内容
	private int index;// 第几步

	Memento(List<String> states, int index) {
		this.states = new ArrayList<String>(states);
		this.index = index;
	}

	public List<String> getStates() {
		return states;
	}

	public int getIndex() {
		return index;
	}

}
