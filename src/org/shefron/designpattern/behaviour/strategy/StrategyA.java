package org.shefron.designpattern.behaviour.strategy;

public class StrategyA implements IStrategy {

	public void execArith(SameCallback callback) {
		// TODO Auto-generated method stub
		callback.doSame();

		System.out.println("��1000��100");

	}

}
