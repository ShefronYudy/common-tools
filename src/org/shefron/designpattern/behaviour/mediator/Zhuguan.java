package org.shefron.designpattern.behaviour.mediator;

public class Zhuguan extends Colleague {

	public Zhuguan(Mediator mediator) {
		super(mediator);
	}

	public void findManager() {
		this._mediator.findManager().doSth();
	}

	public void findZhuanyuan() {
		this._mediator.findZhuanyuan().doSth();
	}

	@Override
	protected void doSth() {
		System.out.println("Ö÷¹ÜÉóÔÄ!");
	}

}
