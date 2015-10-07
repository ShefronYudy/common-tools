package org.shefron.designpattern.behaviour.mediator;

public class Zhuanyuan extends Colleague {

	public Zhuanyuan(Mediator mediator) {
		super(mediator);
	}

	public void findWenyuan() {
		this._mediator.findWenyuan().doSth();
	}

	public void findZhuguan() {
		this._mediator.findZhuguan().doSth();
	}

	@Override
	protected void doSth() {
		System.out.println("专员统计!");
	}

}
