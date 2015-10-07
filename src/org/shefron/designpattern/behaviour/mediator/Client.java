package org.shefron.designpattern.behaviour.mediator;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Mediator mediator = new ConcreteMediator();

		Manager manager = new Manager(mediator);
		manager.findZhuanyuan();
		manager.findZhuguan();

		Wenyuan wy = new Wenyuan(mediator);
		wy.findZhuanyuan();
		wy.findZhuguan();
	}

}
