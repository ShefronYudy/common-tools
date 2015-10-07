package org.shefron.designpattern.behaviour.chainofresponsibility;

public abstract class Handler {
	// 当前类持有下一个类的引用
	private Handler handler = null;

	public abstract void handlerRequest();

	protected void setHandler(Handler handler) {
		this.handler = handler;
	}

	protected Handler getHandler() {
		return this.handler;
	}
}
