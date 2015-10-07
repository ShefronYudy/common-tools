package org.shefron.designpattern.behaviour.mediator;

public class ConcreteMediator extends Mediator {

	@Override
	protected Colleague findZhuguan() {
		System.out.println("������");
		return new Zhuguan(this);
	}

	@Override
	protected Colleague findZhuanyuan() {
		System.out.println("��רԱ");
		return new Zhuanyuan(this);
	}

	@Override
	protected Colleague findWenyuan() {
		System.out.println("����Ա");
		return new Wenyuan(this);
	}

	@Override
	protected Colleague findManager() {
		System.out.println("�Ҿ���");
		return new Manager(this);
	}

}
