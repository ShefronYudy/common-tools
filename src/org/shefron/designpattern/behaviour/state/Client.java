package org.shefron.designpattern.behaviour.state;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Context context = new Context();
		State state = new ConcreteAState();
		context.setState(state);
		context.request("test");

	}

}
