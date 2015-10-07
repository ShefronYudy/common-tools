package org.shefron.designpattern.behaviour.state;

public class Context {
	private State state = null;

	public void setState(State state) {
		this.state = state;
	}

	public void request(String content) {
		this.state.handle(content);
	}

}
