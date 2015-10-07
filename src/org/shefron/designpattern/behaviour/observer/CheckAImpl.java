package org.shefron.designpattern.behaviour.observer;

import java.util.Observable;
import java.util.Observer;

//π€≤Ï’ﬂA
public class CheckAImpl implements ICheck, Observer {

	public CheckAImpl(Observable o) {
		o.addObserver(this);
	}

	public CheckAImpl() {
	}

	public void check() {
		System.out.println("CheckAImpl start check!");
	}

	public void update(Observable o, Object arg) {
		System.out.println("notify is " + o.toString());
		this.check();
	}

}
