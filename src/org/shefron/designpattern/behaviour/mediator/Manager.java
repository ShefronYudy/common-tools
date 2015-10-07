package org.shefron.designpattern.behaviour.mediator;

public class Manager extends Colleague {

	public Manager(Mediator mediator) {
		super(mediator);
	}

	public void findZhuguan() {
		this._mediator.findZhuguan().doSth();
	}

	public void findZhuanyuan() {
		this._mediator.findZhuanyuan().doSth();
	}

	@Override
	protected void doSth() {
		System.out.println("æ≠¿Ì…Û≈˙!");
	}

}
