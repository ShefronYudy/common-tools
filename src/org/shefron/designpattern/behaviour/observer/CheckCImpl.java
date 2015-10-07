package org.shefron.designpattern.behaviour.observer;

import java.util.Observable;
import java.util.Observer;

public class CheckCImpl implements ICheck, Observer {

	public CheckCImpl(Observable o) {
		o.addObserver(this);
	}

	public CheckCImpl() {
	}

	public void check() {
		System.out.println("CheckCImpl start check!");
	}

	public void update(Observable o, Object arg) {
		System.out.println("notify is " + o.toString());
		this.check();
	}

}
