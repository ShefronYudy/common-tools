package org.shefron.designpattern.behaviour.observer;

import java.util.Observable;
import java.util.Observer;

public class CheckBImpl implements ICheck, Observer {

	public CheckBImpl(Observable o) {
		o.addObserver(this);
	}

	public CheckBImpl() {
	}

	public void check() {
		System.out.println("CheckBImpl start check!");
	}

	public void update(Observable o, Object arg) {
		System.out.println("notify is " + o.toString());
		this.check();
	}

}
