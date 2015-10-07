package org.shefron.designpattern.behaviour.strategy;

public class Context {

	private IStrategy strategy = null;

	public Context(IStrategy strategy) {
		this.strategy = strategy;
	}

	public void execute(SameCallback callback) {
		this.strategy.execArith(callback);
	}

}
