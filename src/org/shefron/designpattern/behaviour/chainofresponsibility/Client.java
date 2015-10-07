package org.shefron.designpattern.behaviour.chainofresponsibility;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 组装责任链
		ConcreteHandler handlerA = new ConcreteHandler();
		HelpHandler handlerB = new HelpHandler();
		handlerA.setHandler(handlerB);

		handlerA.handlerRequest();
	}

}
