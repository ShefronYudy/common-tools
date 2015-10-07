package org.shefron.designpattern.behaviour.mediator;

public class ConcreteMediator extends Mediator {

	@Override
	protected Colleague findZhuguan() {
		System.out.println("找主管");
		return new Zhuguan(this);
	}

	@Override
	protected Colleague findZhuanyuan() {
		System.out.println("找专员");
		return new Zhuanyuan(this);
	}

	@Override
	protected Colleague findWenyuan() {
		System.out.println("找文员");
		return new Wenyuan(this);
	}

	@Override
	protected Colleague findManager() {
		System.out.println("找经理");
		return new Manager(this);
	}

}
