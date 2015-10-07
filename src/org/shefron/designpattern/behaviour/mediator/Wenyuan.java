package org.shefron.designpattern.behaviour.mediator;

public class Wenyuan extends Colleague {

	public Wenyuan(Mediator mediator) {
		super(mediator);
	}

	public void findZhuanyuan() {
		this._mediator.findZhuanyuan().doSth();
	}

	public void findZhuguan() {
		this._mediator.findZhuguan().doSth();
	}

	@Override
	protected void doSth() {
		System.out.println("文员上报!");
	}

}
