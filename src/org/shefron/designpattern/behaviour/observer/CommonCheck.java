package org.shefron.designpattern.behaviour.observer;

import java.util.Observable;

/**
 * �۲���ģʽ
 * 
 * @author a
 * 
 */

public class CommonCheck extends Observable {

	/**
	 * @param args
	 */
	@Override
	public void notifyObservers(Object arg) {
		this.setChanged();
		super.notifyObservers(arg);
	}

	public static void main(String[] args) {
		CommonCheck common = new CommonCheck();
		common.addObserver(new CheckAImpl());
		common.addObserver(new CheckBImpl());
		common.addObserver(new CheckCImpl());
		common.notifyObservers();
	}

}
